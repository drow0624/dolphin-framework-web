package com.lsl.nature.common.es;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ESIndexDefinition {
    private String indexName;
    private String type;
    private short shards;
    private short replicas;
    private boolean createIndex;
}
