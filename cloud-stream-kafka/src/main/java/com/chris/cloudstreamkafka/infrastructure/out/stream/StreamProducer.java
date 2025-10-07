package com.chris.cloudstreamkafka.infrastructure.out.stream;

import com.chris.cloudstreamkafka.aplication.port.ProducePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class StreamProducer implements ProducePort {

    private final StreamBridge streamBridge;

    @Override
    public boolean sent(Map<String, Object> payload) {
        payload.put("date", LocalDate.now().toString());
        payload.put("active", Boolean.TRUE);
        final var bindingName = "publish-out-0";
        log.info("Sending binding '{}'", bindingName);
        log.info("Start to send payload {}", payload);
        return streamBridge.send(bindingName, payload);
    }
}
