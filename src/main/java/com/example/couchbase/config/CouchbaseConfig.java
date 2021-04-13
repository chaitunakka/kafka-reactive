package com.example.couchbase.config;

import com.couchbase.client.core.env.TimeoutConfig;
import com.couchbase.client.java.env.ClusterEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

import java.time.Duration;

@Configuration
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Override
    public String getConnectionString() {
        return "couchbase://172.24.0.4";
    }

    @Override
    public String getUserName() {
        return "root";
    }

    @Override
    public String getPassword() {
        return "docker";
    }

    @Override
    public String getBucketName() {
        return "beer-sample";
    }

    @Override
    @Primary
    @Bean
    public CustomConversions customConversions() {
        return super.customConversions();
    }

    @Override
    protected void configureEnvironment(ClusterEnvironment.Builder builder) {
        builder.timeoutConfig(TimeoutConfig
                .connectTimeout(Duration.ofMillis(15000))
                .queryTimeout(Duration.ofMillis(600 * 1000))
                .viewTimeout(Duration.ofMillis(600 * 1000)))
        .build();
    }
}
