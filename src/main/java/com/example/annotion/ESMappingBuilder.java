package com.example.annotion;

import com.example.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ESMappingBuilder {

    public static final String FIELD_DATA = "fielddata";
    public static final String FIELD_STORE = "store";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_INDEX = "index";
    public static final String FIELD_FORMAT = "format";
    public static final String FIELD_SEARCH_ANALYZER = "search_analyzer";
    public static final String FIELD_INDEX_ANALYZER = "analyzer";
    public static final String FIELD_NORMALIZER = "normalizer";
    public static final  String FIELD_PROPERTIES = "properties";
    public static final String TYPE_VALUE_KEYWORD = "keyword";


    public static List<ESMappingDefinition> esFieldDefinitions(Class<?> clazz) {
        Map<String, Field> fields = new HashMap<>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            java.lang.reflect.Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                String name = declaredField.getName();
                boolean annotationPresent = declaredField.isAnnotationPresent(ESField.class);
                if (!fields.containsKey(name) && annotationPresent) {
                    fields.put(name, declaredField);
                }
            }
        }
        return fields.values().stream()
                .map(field -> {
                    ESField esField = field.getAnnotation(ESField.class);
                    return ESMappingDefinition.builder()
                            .fieldName(field.getName())
                            .defaultFieldType(field.getType().getSimpleName())
                            .type(esField.type())
                            .index(esField.index())
                            .store(esField.store())
                            .pattern(esField.pattern())
                            .searchAnalyzer(esField.searchAnalyzer())
                            .normalizer(esField.normalizer())
                            .ignoreFields(esField.ignoreFields())
                            .dateFormat(esField.dateFormat())
                            .analyzer(esField.analyzer()).build();
                }).collect(Collectors.toList());
    }

    public static ESIndexDefinition esIndexDefinition(Class<?> clazz){
        ESIndexDefinition esIndexDefinition = null;
        boolean annotationPresent = clazz.isAnnotationPresent(ESDocument.class);
        if(annotationPresent){
            ESDocument annotation = clazz.getAnnotation(ESDocument.class);
            esIndexDefinition =ESIndexDefinition.builder().createIndex(annotation.createIndex())
                    .indexName(annotation.indexName())
                    .type(annotation.type())
                    .shards(annotation.shards())
                    .replicas(annotation.replicas())
                    .build();
        }
        return esIndexDefinition;
    }

    /**
     * 添加映射属性
     *
     * @param builder
     * @param esFieldDefinition
     * @throws IOException
     */
    public static void addFieldMappingParameters(XContentBuilder builder, ESMappingDefinition esFieldDefinition) throws IOException {

        boolean index = esFieldDefinition.isIndex();
        boolean store = esFieldDefinition.isStore();
        FieldType type = esFieldDefinition.getType();
        DateFormat dateFormat = esFieldDefinition.getDateFormat();
        String analyzer = esFieldDefinition.getAnalyzer();
        String searchAnalyzer = esFieldDefinition.getSearchAnalyzer();
        String normalizer = esFieldDefinition.getNormalizer();
        if (store) {
            builder.field(ESMappingBuilder.FIELD_STORE, store);
        }

        if (type != FieldType.Auto) {
            builder.field(ESMappingBuilder.FIELD_TYPE, type.name().toLowerCase());
            if (type == FieldType.Date && dateFormat != DateFormat.none) {
                builder.field(ESMappingBuilder.FIELD_FORMAT, dateFormat.toString());
            }
        }
        if (!index) {
            builder.field(ESMappingBuilder.FIELD_INDEX, index);
        }
        if (!ESMappingBuilder.isEmpty(analyzer)) {
            builder.field(ESMappingBuilder.FIELD_INDEX_ANALYZER, analyzer);
        }
        if (!ESMappingBuilder.isEmpty(searchAnalyzer)) {
            builder.field(ESMappingBuilder.FIELD_SEARCH_ANALYZER, searchAnalyzer);
        }
        if (!ESMappingBuilder.isEmpty(normalizer)) {
            builder.field(ESMappingBuilder.FIELD_NORMALIZER, normalizer);
        }
    }

    public static FieldType fieldType(ESMappingDefinition esFieldDefinition){
        FieldType type = esFieldDefinition.getType();
        if (type == null) {
            type = FieldType.of(esFieldDefinition.getDefaultFieldType());
        }
        return type;
    }

    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }
    public static void main(String[] args) {
        List<ESMappingDefinition> esFields = esFieldDefinitions(Article.class);
        for (ESMappingDefinition esField : esFields) {
            log.info(esField.toString());
        }
        ESIndexDefinition indexDefinition = esIndexDefinition(Article.class);
        log.info(indexDefinition.toString());
        log.info(FieldType.of("")+"");
        log.info(FieldType.of("keyword")+"");
    }

}
