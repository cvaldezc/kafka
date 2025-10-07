package com.chris.cloudstreamkafka.infrastructure.in.web;

import com.chris.cloudstreamkafka.aplication.port.PublishUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WebController {

    private final PublishUseCase publishUseCase;

    @PostMapping("/publish")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> publish(@RequestBody Map<String, Object> payload) {
        log.info("Start to publish payload {}", payload);
        var ok = publishUseCase.sent(payload);
        return ResponseEntity.ok(ok ? "SENT" : "FAILED");
    }
}
