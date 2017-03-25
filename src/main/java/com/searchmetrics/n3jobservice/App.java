package com.searchmetrics.n3jobservice;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by arobinson on 3/25/17.
 */
public class App extends Application<N3JobServiceConfig> {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void run(N3JobServiceConfig configuration, Environment environment) throws Exception {

    }
}
