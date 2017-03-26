package com.searchmetrics.n3jobservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by arobinson on 3/25/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CassandraYamlConfig {
    private final String contactPoints;
    private final String keySpace;
    private final Integer port;

    @JsonCreator
    public CassandraYamlConfig(
            String contactPoints,
            String keySpace,
            Integer port) {
        this.contactPoints = contactPoints;
        this.keySpace = keySpace;
        this.port = port;
    }

    public String getContactPoints() {
        return contactPoints;
    }

    public String getKeySpace() {
        return keySpace;
    }

    public Integer getPort() {
        return port;
    }
}