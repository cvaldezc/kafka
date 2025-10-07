package com.chris.cloudstreamkafka.aplication.port;

import java.util.Map;

public interface PublishUseCase {

    boolean sent(Map<String, Object> payload);
}
