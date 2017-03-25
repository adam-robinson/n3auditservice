package com.searchmetrics.n3service.dao;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.searchmetrics.n3jobservice.N3JobServiceConfig;
import com.searchmetrics.n3jobservice.dao.JobsRepository;
import com.searchmetrics.n3jobservice.dto.Job;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by arobinson on 3/25/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = N3JobServiceConfig.CassandraConf.class)
public class JobsRepositoryIT {
    public static final Logger LOGGER = LoggerFactory.getLogger(JobsRepositoryIT.class);

    // Added additional time to bring up the server, was failing on work machine...default is 10000 ms
    public static final Long EMBEDDED_SERVER_STARTUP_TIMEOUT = 30000L;

    @Autowired
    private JobsRepository jobsRepository;


    @BeforeClass
    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra(EMBEDDED_SERVER_STARTUP_TIMEOUT);
        final Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").withPort(9142).build();
        LOGGER.info("Server Started at 127.0.0.1:9142... ");
        final Session session = cluster.connect();
        Thread.sleep(10000);
    }

    @Test
    public void test_newJob() {
        Job newJob = new Job(1234567L, "prod.wago.com", 10000, "sso", null,
                null, LocalDateTime.now(), "someURL", 1);
        jobsRepository.save(newJob);
        Assert.assertTrue("", jobsRepository.findById(1234567L).count() == 1);
    }
}
