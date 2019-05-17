package priv.cy.common.es.client;

import com.alibaba.fastjson.JSON;
import priv.cy.project.sysmgr.demo.domain.DataAccessLog;
import priv.cy.project.sysmgr.demo.domain.MonitorLog;
import priv.cy.project.sysmgr.demo.domain.SystemLog;
import priv.cy.common.es.ESIndexDefinition;
import priv.cy.common.es.ESMappingDefinition;
import priv.cy.common.es.ESMappingBuilder;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
public class ElasticSearchClient {

    private static final String CLUSTER_NAME = "cluster.name";
    private static final String CLIENT_TRANSPORT_SNIFF = "client.transport.sniff";

    /**
     * ES客户端
     *
     * @return
     * @throws UnknownHostException
     */
    public static TransportClient client() throws UnknownHostException {
        // 连接集群的设置
        Settings settings = Settings.builder()
//                .put(CLUSTER_NAME, "trs-elasticsearch")//集群名称
                .put(CLUSTER_NAME, "es")//集群名称
                .put(CLIENT_TRANSPORT_SNIFF, true) //自动嗅探
                .build();
        return new PreBuiltTransportClient(settings)
//                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
//                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.200.3"), 9300));
    }

    /**
     * 创建索引
     *
     * @param client
     * @param indexName
     * @param contentBuilder
     * @return
     */
    public static boolean createIndex(TransportClient client, String indexName, XContentBuilder contentBuilder) {
        CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(indexName);
        if (contentBuilder != null) {
            createIndexRequestBuilder.setSettings(contentBuilder);
        }
        CreateIndexResponse createIndexResponse = createIndexRequestBuilder.get();
        return createIndexResponse.isAcknowledged();
    }

    /**
     * 索引是否存在
     *
     * @param client
     * @param index
     * @return
     */
    public static boolean isExistsIndex(TransportClient client, String index) {
        IndicesExistsRequest indicesExistsRequest = new IndicesExistsRequest(index);
        IndicesExistsResponse response = client.admin().indices().exists(indicesExistsRequest).actionGet();
        return response.isExists();
    }

    /**
     * 类型是否存在
     *
     * @param client
     * @param index
     * @param type
     * @return
     */
    public static boolean isExistsType(TransportClient client, String index, String type) {
        TypesExistsRequest typesExistsRequest = new TypesExistsRequest(new String[]{index}, type);
        TypesExistsResponse response = client.admin().indices().typesExists(typesExistsRequest).actionGet();
        return response.isExists();
    }

    /**
     * 删除索引
     *
     * @param client
     * @param index
     * @return
     */
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
     *
     * @param index           索引
     * @param type            类型
     * @param mappingPropsMap mappingPropsMap举例：
     *                        {
     *                        "name":{
     *                        "type":"text"
     *                        }
     *                        }
     * @return true-成功，false-失败
     */
    public static boolean createMapping(TransportClient client, String index, String type, Map<String, Map<String, Object>> mappingPropsMap) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                .startObject(type)
                .startObject(ESMappingBuilder.FIELD_PROPERTIES); // start, start type, start properties
        if (mappingPropsMap.isEmpty()) {
            return false;
        }
            for (Map.Entry<String, Map<String, Object>> prop : mappingPropsMap.entrySet()) {
                if (prop.getKey() != null) {
                    addAttribute(builder, prop);
                }
            }

        builder.endObject().endObject().endObject(); // end start, end type, end properties
        PutMappingRequest mapping = Requests.putMappingRequest(index).type(type).source(builder);
        PutMappingResponse putMappingResponse = client.admin().indices().putMapping(mapping).actionGet();
        return putMappingResponse.isAcknowledged();
    }

    private static void addAttribute(XContentBuilder builder, Map.Entry<String, Map<String, Object>> prop) throws IOException {
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

    /**
     * 索引文档
     *
     * @param index 索引
     * @param type  类型
     * @param json  JSON文档，key-属性名，value-属性类型
     * @return boolean
     */
    public static boolean createDocument(TransportClient client, String index, String type, String id, String json) {

        IndexRequestBuilder ib;
        if (id != null && !id.trim().isEmpty()) {
            ib = client.prepareIndex(index, type, id);
        } else {
            ib = client.prepareIndex(index, type);
        }
//        XContentBuilder builder = XContentFactory.jsonBuilder();
//        builder.startObject(); // start
//        for (Map.Entry<String, Object> field : docProps.entrySet()) {
//            String key = field.getKey();
//            Object value = field.getValue();
//            if (key != null && !key.trim().isEmpty() && value != null) {
//                builder.field(key, value);
//            }
//        }
//        builder.endObject(); // end
//        ib.setSource(builder);
        ib.setSource(json, XContentType.JSON);
        IndexResponse indexResponse = ib.get();
        return indexResponse.forcedRefresh();
    }


    public static <T> boolean createDocument(TransportClient client, String id, T t) {
        ESIndexDefinition indexDefinition = ESMappingBuilder.esIndexDefinition(t.getClass());
        if (indexDefinition == null) {
            return false;
        }
        return createDocument(client, indexDefinition.getIndexName(), indexDefinition.getType(), "", JSON.toJSONString(t));
    }

    /**
     * 创建分词器
     *
     * @return
     */
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

    /**
     * 获取映射关系
     * {
     * "message":{"type":"text","fields":{"keyword":{"type":"keyword","ignore_above":256}}},
     * "postDate":{"type":"date"},
     * "user":{"type":"text","fields":{"keyword":{"type":"keyword","ignore_above":256}}}
     * }
     *
     * @param client
     * @param index
     * @param type
     * @return
     */
    public static Map<String, Object> obtainMapping(TransportClient client, String index, String type) throws IOException {
        GetMappingsResponse response = client.admin().indices().prepareGetMappings(index).get();
        ImmutableOpenMap<String, MappingMetaData> mappings = response.getMappings().get(index);
        MappingMetaData mappingMetaData = mappings.get(type);
        Map<String, Object> propertiesMapping = null;
        if (mappingMetaData != null) {
            propertiesMapping = (Map<String, Object>) mappingMetaData.sourceAsMap().get("properties");
        }
        return propertiesMapping;
    }

    /**
     * 根据ID获取数据
     *
     * @param client
     * @param index
     * @param type
     * @param id
     * @return
     */
    public static Map<String, Object> getById(TransportClient client, String index, String type, String id) {
        GetResponse response = client.prepareGet(index, type, id).get();
        return response.getSourceAsMap();
    }

    /**
     * 分页获取文档列表
     *
     * @param client
     * @param index
     * @param type
     * @param from
     * @param size
     * @return
     */
    public static List<Map<String, Object>> documentForPage(TransportClient client, String index,
                                                            String type, int from, int size) {
        SearchResponse searchResponse = client.prepareSearch(index).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//                .setQuery(QueryBuilders.termQuery("multi", "test"))                 // Query
//                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
                .setFrom(from).setSize(size).setExplain(true)
                .get();

        SearchHits hits = searchResponse.getHits();
        List<Map<String, Object>> responses = new ArrayList<>();
        for (SearchHit hit : hits) {
            responses.add(hit.getSourceAsMap());
        }
        return responses;
    }

    /**
     * 创建映射，自动检测已存在映射，如果新增资字段，则自动更新字段
     *
     * @param client
     * @param clazz
     * @return
     * @throws IOException
     */
    public static boolean createMapping(TransportClient client, Class<?> clazz) throws IOException {
        ESIndexDefinition indexDefinition = ESMappingBuilder.esIndexDefinition(clazz);
        if (indexDefinition == null) {
            return false;
        }
        List<ESMappingDefinition> esFieldDefinitions = ESMappingBuilder.esFieldDefinitions(clazz);
        String index = indexDefinition.getIndexName();
        String type = indexDefinition.getType();
        if (!isExistsIndex(client, index)) {
            log.warn(String.format("索引：%s 不存在", index));
//            createIndex(client, index, null);
            return false;
        } else {
            Map<String, Object> existsMappings = obtainMapping(client, index, type);
            if (existsMappings != null) {
                Set<String> existProps = existsMappings.keySet();
                esFieldDefinitions = esFieldDefinitions.stream()
                        .filter(esFieldDef -> !existProps.contains(esFieldDef.getFieldName())).collect(Collectors.toList());
            }
        }
        if (esFieldDefinitions != null && !esFieldDefinitions.isEmpty()) {
            StringBuilder attributes = new StringBuilder();
            esFieldDefinitions.stream().forEach(field -> {
                attributes.append(field.getFieldName()).append(",").append(field.getType()).append(" ");
            });
            log.info(String.format("索引：%s,类型：%s,映射新增属性:%s", index, type, attributes));
            XContentBuilder builder = XContentFactory.jsonBuilder();
            // start, start type, start properties
            builder.startObject()
                    .startObject(type)
                    .startObject(ESMappingBuilder.FIELD_PROPERTIES);
            for (ESMappingDefinition esFieldDefinition : esFieldDefinitions) {
                builder.startObject(esFieldDefinition.getFieldName());
                builder.field(ESMappingBuilder.FIELD_TYPE, ESMappingBuilder.fieldType(esFieldDefinition).toString().toLowerCase());
                ESMappingBuilder.addFieldMappingParameters(builder, esFieldDefinition);
                builder.endObject();
            }
            builder.endObject()
                    .endObject()
                    .endObject();
            // start, start type, start properties
            PutMappingRequest mapping = Requests.putMappingRequest(index).type(type).source(builder);
            PutMappingResponse putMappingResponse = client.admin().indices().putMapping(mapping).actionGet();
            return putMappingResponse.isAcknowledged();
        }
        return true;
    }


    /**
     * 关闭索引
     *
     * @param index
     * @return
     */
    public static boolean closeIndex(TransportClient client, String index) {
        IndicesAdminClient indicesAdminClient = client.admin()
                .indices();
        CloseIndexResponse response = indicesAdminClient.prepareClose(index)
                .get();
        return response.isAcknowledged();
    }

    /**
     * 打开索引
     *
     * @param index
     * @return
     */
    public static boolean openIndex(TransportClient client, String index) {
        IndicesAdminClient indicesAdminClient = client.admin()
                .indices();
        OpenIndexResponse response = indicesAdminClient.prepareOpen(index)
                .get();
        return response.isAcknowledged();
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

//        初始化客户端
        TransportClient client = client();
//        Map<String, Object> mappingMetaData = null;
//        创建索引
//        boolean text_index = createIndex(client, "text_index",null);
//        判断索引是否存在
//        boolean isExistsIndex = isExistsIndex(client, "text_index");
//        创建文档
//        createDocument(client,"text_index","tweet",null,JSON.toJSONString(new HashMap<String, Object>() {{
//            put("user", "yang.cao"+System.nanoTime());
//            put("postDate", new DATE());
//            put("message", "msg:"+System.nanoTime());
//            put("name", "name:"+UUID.randomUUID()+System.nanoTime());
//            put("nameXX", "nameXX:"+System.nanoTime());
//        }}));

//        更新创建映射
//        Map<String, Map<String, Object>> mappingPropsMap = new HashMap<>();
//        Map<String, Object> type = new HashMap<>();
//        type.put("type", "text");
//        mappingPropsMap.put("keywords", type);
//        createMapping(client, "text_index", "tweet", mappingPropsMap);
//        获取映射
//        mappingMetaData = obtainMapping(client, "text_index", "tweet");
//        log.info(JSON.toJSONString(mappingMetaData));
//        log.info(JSON.toJSONString(documentForPage(client, "text_index", "tweet", 0, 100)));
//        创建映射
        boolean isSuccess = createMapping(client, DataAccessLog.class);
        System.out.println("修改映射：" + isSuccess);

        isSuccess = createMapping(client, MonitorLog.class);
        System.out.println("修改映射：" + isSuccess);

        isSuccess = createMapping(client, SystemLog.class);
        System.out.println("修改映射：" + isSuccess);

//        获取映射
//        mappingMetaData = obtainMapping(client, "doc_index", "article");
//        log.info(JSON.toJSONString(mappingMetaData));
//        创建索引
//        createDocument(client, "", Article.createArticle());
//        log.info(JSON.toJSONString(documentForPage(client, "doc_index", "article", 0, 100)));
//        删除映射
//        deleteIndex(client, "doc_index");

//        获取属性
//        GetSettingsRequest settingsRequest = new GetSettingsRequest();
//        settingsRequest.humanReadable(true);
//        settingsRequest.includeDefaults(true);
//        GetSettingsResponse getSettingsResponse = client.admin().indices()
//                .getSettings(settingsRequest.indices("uirb_log_index")).actionGet();
//        ImmutableOpenMap<String, Settings> indexToSettings = getSettingsResponse.getIndexToSettings();
//        Settings uirb_log_index = indexToSettings.get("uirb_log_index");
//        Map<String, Settings> groups = uirb_log_index.getGroups("index.analysis.analyzer");
//        log.info("index.analysis.analyzer===>"+groups.toString());

        /*{
            "filter": [
            "lowercase",
                    "my_stopwords"
                            ],
            "char_filter": [
            "html_strip",
                    "&_to_and"
                            ],
            "type": "custom",
                "tokenizer": "standard"
        }*/

        /*
        XContentBuilder builder = XContentFactory.jsonBuilder();
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
        */

//        Map<String, Object> analyzersConfig = new HashMap<>();
//        Map<String, Object> myAnalyzerConfig = new HashMap<>();
//        analyzersConfig.put("my_analyzer",myAnalyzerConfig);
//        myAnalyzerConfig.put("tokenizer","my_tokenizer");
//
//        Map<String, Object> tokenizerConfig = new HashMap<>();
//        Map<String, Object> mytokenizerConfig = new HashMap<>();
//        tokenizerConfig.put("my_tokenizer",mytokenizerConfig);
//        mytokenizerConfig.put("type","pattern");
//        mytokenizerConfig.put("pattern",",");
//
//
//        Map<String, Object> analysisConfig = new HashMap<>();
//        analysisConfig.put("analyzer",analyzersConfig);
//        analysisConfig.put("tokenizer",tokenizerConfig);
//
//        Map<String, Object> customAnalysis =  new HashMap<>();
//        customAnalysis.put("analysis", analysisConfig);
//
//          更新属性设置
//        UpdateSettingsRequest settingRequest = new UpdateSettingsRequest("uirb_log_index");
//        settingRequest.settings(customAnalysis);
//        settingRequest.settings(Collections.singletonMap("index.number_of_shards", 1));


//        UpdateSettingsResponse response = client.admin().indices()
//                .updateSettings(settingRequest).get();
        client.close();
    }
}
