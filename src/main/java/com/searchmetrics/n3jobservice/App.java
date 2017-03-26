package com.searchmetrics.n3jobservice;

import com.searchmetrics.n3jobservice.api.rest.JobResource;
import com.searchmetrics.n3jobservice.dao.JobsRepository;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by arobinson on 3/25/17.
 */
public class App extends Application<N3JobServiceConfig> {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void run(N3JobServiceConfig configuration, Environment environment) throws Exception {
        final AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(
                        N3JobServiceConfig.class
                );

        final JobsRepository jobsRepository = applicationContext.getBean(JobsRepository.class);
        final JobResource jobResource = new JobResource(jobsRepository);
        environment.jersey().register(jobResource);
    }
}
