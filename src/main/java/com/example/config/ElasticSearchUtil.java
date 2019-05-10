package com.example.config;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

@Slf4j
public class ElasticSearchUtil {


    private static TransportClient client;


    public static TransportClient client() throws UnknownHostException {

        if (client == null) {
            //client = new PreBuiltTransportClient(Settings.EMPTY)

            // 连接集群的设置
            Settings settings = Settings.builder()
                    .put("cluster.name", "es") //如果集群的名字不是默认的elasticsearch，需指定
                    .put("client.transport.sniff", true) //自动嗅探
                    .build();
            client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

            //可用连接设置参数说明
			/*
			cluster.name
				指定集群的名字，如果集群的名字不是默认的elasticsearch，需指定。
			client.transport.sniff
				设置为true,将自动嗅探整个集群，自动加入集群的节点到连接列表中。
			client.transport.ignore_cluster_name
				Set to true to ignore cluster name validation of connected nodes. (since 0.19.4)
			client.transport.ping_timeout
				The time to wait for a ping response from a node. Defaults to 5s.
			client.transport.nodes_sampler_interval
				How often to sample / ping the nodes listed and connected. Defaults to 5s.
			*/

        }
        return client;
    }

    public static boolean createIndex(TransportClient client, String indexName, XContentBuilder contentBuilder) {
        CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(indexName);
        if (contentBuilder != null) {
            createIndexRequestBuilder.setSettings(contentBuilder);
        }
        CreateIndexResponse createIndexResponse = createIndexRequestBuilder.get();
        return createIndexResponse.isAcknowledged();
    }

    public static boolean isExistsIndex(TransportClient client, String index) {
        IndicesExistsRequest indicesExistsRequest = new IndicesExistsRequest(index);
        IndicesExistsResponse response = client.admin().indices().exists(indicesExistsRequest).actionGet();
        return response.isExists();
    }

    public static boolean isExistsType(TransportClient client, String index, String type) {
        TypesExistsRequest typesExistsRequest = new TypesExistsRequest(new String[]{index}, type);
        TypesExistsResponse response = client.admin().indices().typesExists(typesExistsRequest).actionGet();
        return response.isExists();
    }

    public static boolean deleteIndex(TransportClient client, String index) {
        if (isExistsIndex(client, index)) {
            DeleteIndexResponse deleteIndexResponse = client.admin().indices().prepareDelete(index).get();
            return deleteIndexResponse.isAcknowledged();
        } else {
            return false;
        }
    }

    /**
     * 创建映射
     * @param index 索引
     * @param type 类型
     * @param mappingPropsMap
     *
     * mappingPropsMap举例：
     * {
     *    "name":{
     *        "type":"text"
     *    }
     * }
     *
     *  @return true-成功，false-失败
     */
    public boolean createMapping(TransportClient client, String index, String type, Map<String, Map<String, Object>> mappingPropsMap) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject().startObject(type).startObject("properties"); // start, start type, start properties
        builder.startObject().startObject(type).startObject("properties"); // start, start type, start properties
        if (!mappingPropsMap.isEmpty()) {
            for (Map.Entry<String, Map<String, Object>> prop : mappingPropsMap.entrySet()) {
                if (prop.getKey() != null) {
                    builder.startObject(prop.getKey()); // start prop.getkey()
                    if (!prop.getValue().isEmpty()) {
                        for (Map.Entry<String, Object> field : prop.getValue().entrySet()) {
                            String key = field.getKey();
                            Object value = field.getValue();
                            if (key != null && !key.trim().isEmpty() && value != null) {
                                builder.field(key, value);
                            }
                        }
                    }
                    builder.endObject(); // end prop.getKey()
                }
            }
        }
        builder.endObject().endObject().endObject(); // end start, end type, end properties
        builder.endObject().endObject().endObject(); // end start, end type, end properties
        PutMappingRequest mapping = Requests.putMappingRequest(index).type(type).source(builder);
        PutMappingResponse putMappingResponse = client.admin().indices().putMapping(mapping).actionGet();
        return putMappingResponse.isAcknowledged();
    }
    /**
     * 索引文档
     * @param index 索引
     * @param type 类型
     * @param docProps 文档属性，key-属性名，value-属性类型
     * @return boolean
     */
    public boolean indexDocument(String index, String type, Map<String, Object> docProps) throws IOException {
        String id = null;
        // 设置默认ID
        if (docProps.get("id") != null) {
            id = String.valueOf(docProps.get("id"));
        }
        IndexRequestBuilder ib;
        if (id != null && !id.trim().isEmpty()) {
            ib = client.prepareIndex(index, type, id);
        }else {
            ib = client.prepareIndex(index, type);
        }
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject(); // start

        for (Map.Entry<String, Object> field : docProps.entrySet()) {
            String key = field.getKey();
            Object value = field.getValue();
            if (key != null && !key.trim().isEmpty() && value != null) {
                builder.field(key, value);
            }
        }

        builder.endObject(); // end
        ib.setSource(builder);
        IndexResponse indexResponse = ib.get();
        return indexResponse.forcedRefresh();
    }

    public static XContentBuilder createAnalyzer() {
        XContentBuilder builder = null;
        try {
            builder = XContentFactory.jsonBuilder();
            builder.startObject();
            builder.startObject("analysis");
            builder.startObject("analyzer");
            builder.startObject("my_analyzer");
            builder.field("tokenizer", "my_tokenizer");
            builder.endObject();
            builder.endObject();
            builder.startObject("tokenizer");
            builder.startObject("my_tokenizer");
            builder.field("type", "pattern");
            builder.field("pattern", ",");
            builder.endObject();
            builder.endObject();
            builder.endObject();
            builder.endObject();
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
        return builder;
    }

    public static void main(String[] args) throws UnknownHostException {
        TransportClient client = client();
//        boolean text_index = createIndex(client, "text_index",null);

        boolean text_index1 = isExistsIndex(client, "text_index");

        Map<String, Object> json = new HashMap<>();
        json.put("user","kimchy");
        json.put("postDate",new Date());
        json.put("message","trying out Elasticsearch");


        IndexResponse response = client.prepareIndex("text_index", "tweet")
                .setSource(json, XContentType.JSON)
                .get();


        System.out.println(text_index1);
        client.close();

    }
}
