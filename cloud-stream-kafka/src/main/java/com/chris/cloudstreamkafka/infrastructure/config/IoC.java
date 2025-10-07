package com.chris.cloudstreamkafka.infrastructure.config;

import com.chris.cloudstreamkafka.aplication.port.ProducePort;
import com.chris.cloudstreamkafka.aplication.port.PublishUseCase;
import com.chris.cloudstreamkafka.aplication.service.PublishService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IoC {

    @Bean
    public PublishUseCase publishUseCase(ProducePort producePort) {
        return new PublishService(producePort);
    }
}
