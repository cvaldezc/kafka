package io.chris.kafka.streamsjoiner.expose;

import io.chris.kafka.streamsjoiner.streams.StreamController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CommandRun implements CommandLineRunner {

  private final StreamController controller;

  @Override
  public void run(String... args) throws Exception {
    log.info("STARTING APP");
    controller.streamsStart();
  }
}
