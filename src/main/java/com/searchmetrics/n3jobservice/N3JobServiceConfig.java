package com.searchmetrics.n3jobservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.validation.constraints.NotNull;

/**
 * Created by arobinson on 3/25/17.
 */
@EnableTransactionManagement
@ComponentScan("com.searchmetrics.n3jobservice.dao")
@EnableCassandraRepositories(basePackages = "com.searchmetrics.audit.job.config.dao")
@PropertySource(value = "classpath:application.properties")
public class N3JobServiceConfig extends Configuration {
    private static Logger LOGGER = LoggerFactory.getLogger(N3JobServiceConfig.class);

    @JsonProperty("swagger")
    private SwaggerBundleConfiguration swaggerBundleConfiguration;

    @NotNull
    private CassandraYamlConfig cassandraYamlConfig;


    public SwaggerBundleConfiguration getSwaggerBundleConfiguration() {
        return swaggerBundleConfiguration;
    }

    public CassandraYamlConfig getCassandraYamlConfig() {
        return cassandraYamlConfig;
    }

    public class CassandraConf extends AbstractCassandraConfiguration {

        @Override
        protected String getKeyspaceName() {
            return cassandraYamlConfig.getKeySpace();
        }

        @Override
        @Bean
        public CassandraClusterFactoryBean cluster() {
            final CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
            cluster.setContactPoints(cassandraYamlConfig.getContactPoints());
            cluster.setPort(cassandraYamlConfig.getPort());
            LOGGER.info("Cluster created with contact points [{}] & port [{}].",
                    cassandraYamlConfig.getContactPoints(),
                    cassandraYamlConfig.getPort());
            return cluster;
        }

        @Override
        public String[] getEntityBasePackages() {
            return new String[]{"com.searchmetrics.n3jobservice.dao"};
        }

        @Override
        public SchemaAction getSchemaAction() {
            return SchemaAction.CREATE_IF_NOT_EXISTS;
        }

        @Override
        @Bean
        public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {
            return new BasicCassandraMappingContext();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class CassandraYamlConfig {
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
}
