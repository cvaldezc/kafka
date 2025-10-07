package com.chris.cloudstreamkafka.aplication.service;

import com.chris.cloudstreamkafka.aplication.port.ProducePort;
import com.chris.cloudstreamkafka.aplication.port.PublishUseCase;

import java.util.Map;

public class PublishService implements PublishUseCase {

    private final ProducePort producePort;

    public PublishService(ProducePort producePort) {
        this.producePort = producePort;
    }

    @Override
    public boolean sent(Map<String, Object> payload) {
        return producePort.sent(payload);
    }
}
