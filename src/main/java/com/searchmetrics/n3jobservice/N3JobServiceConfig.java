package com.searchmetrics.n3jobservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;

import javax.validation.constraints.NotNull;

/**
 * Created by arobinson on 3/25/17.
 */
@ComponentScan("com.searchmetrics.n3jobservice.dao")
//@EnableCassandraRepositories(basePackages = "com.searchmetrics.audit.job.config.dao")
public class N3JobServiceConfig extends Configuration {
    private static Logger LOGGER = LoggerFactory.getLogger(N3JobServiceConfig.class);

    @JsonProperty("swagger")
    private SwaggerBundleConfiguration swaggerBundleConfiguration;

    @NotNull
    private CassandraYamlConfig cassandraYamlConfig;

    public SwaggerBundleConfiguration getSwaggerBundleConfiguration() {
        return swaggerBundleConfiguration;
    }

    @JsonProperty("cassandra")
    public CassandraYamlConfig getCassandraYamlConfig() {
        return cassandraYamlConfig;
    }

}
