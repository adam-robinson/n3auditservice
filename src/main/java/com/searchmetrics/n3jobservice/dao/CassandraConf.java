package com.searchmetrics.n3jobservice.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.searchmetrics.n3jobservice.dao")
@PropertySource(value = { "classpath:application.properties" } )
public class CassandraConf extends AbstractCassandraConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraConf.class);

    @Autowired
    Environment env;

    @Override
    protected String getKeyspaceName() {
        return env.getProperty("cassandra.keySpace");
    }

    @Override
    @Bean
    public CassandraClusterFactoryBean cluster() {
        LOGGER.info("Cluster created with contact points [{}] & port [{}].",
                env.getProperty("cassandra.contactPoints"),
                env.getProperty("cassandra.port"));
        final CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(env.getProperty("cassandra.contactPoints"));
        cluster.setPort(Integer.valueOf(env.getProperty("cassandra.port")));
        return cluster;
    }

    @Override
    @Bean
    public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {
        return new BasicCassandraMappingContext();
    }

}

