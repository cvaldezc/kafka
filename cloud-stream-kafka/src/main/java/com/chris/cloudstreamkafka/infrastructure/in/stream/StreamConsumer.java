package com.chris.cloudstreamkafka.infrastructure.in.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
@Configuration
public class StreamConsumer {


    @Bean
    public Consumer<String> payments() {
        return msg -> log.info("Consumer received : {}", msg);
    }
}
