package com.chris.cloudstreamkafka.aplication.port;

import java.util.Map;

public interface ProducePort {

    boolean sent(Map<String, Object> payload);
}
