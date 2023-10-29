package io.chris.kafka.streamsjoiner.streams;

import io.chris.kafka.streamsjoiner.joiner.ModelMergedAndThree;
import io.chris.kafka.streamsjoiner.joiner.ModelOneAndTwo;
import io.chris.kafka.streamsjoiner.model.InputOne;
import io.chris.kafka.streamsjoiner.model.InputThree;
import io.chris.kafka.streamsjoiner.model.InputTwo;
import io.chris.kafka.streamsjoiner.model.MergedModel;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProcessorStreams implements StreamController{

  @Value("${spring.kafka.input1.topic:input1}")
  private String input1;
  @Value("${spring.kafka.input2.topic:input2}")
  private String input2;
  @Value("${spring.kafka.input3.topic:input3}")
  private String input3;
  @Value("${spring.kafka.output.topic:output}")
  private String output;

  private final StreamsBuilderFactoryBean factoryBean;

  @Override
  public void streamsStart() throws Exception {
    StreamsBuilder builder = new StreamsBuilder();

    KStream<String, InputOne> streamOne = builder.stream(input1, Consumed.with(Serdes.String(),
              new JsonSerde<>(InputOne.class).ignoreTypeHeaders().noTypeInfo().forKeys()))
              .selectKey((s, inputOne) -> inputOne.getId());

    KStream<String, InputTwo> streamTwo = builder.stream(input2, Consumed.with(Serdes.String(),
                        new JsonSerde<>(InputTwo.class).ignoreTypeHeaders().noTypeInfo().forKeys()))
              .selectKey((s, inputTwo) -> inputTwo.getId());

    KStream<String, InputThree> streamThree = builder.stream(input3, Consumed.with(Serdes.String(),
                        new JsonSerde<>(InputThree.class).ignoreTypeHeaders().noTypeInfo().forKeys()))
              .selectKey((s, inputThree) -> inputThree.getId());

    KStream<String, MergedModel> firstJoined = streamOne.join(streamTwo, new ModelOneAndTwo(),
              JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(3L)),
              StreamJoined.with(Serdes.String(), new JsonSerde<InputOne>(InputOne.class),
                        new JsonSerde<InputTwo>(InputTwo.class))
    );

    KStream<String, MergedModel> joined = firstJoined.join(streamThree, new ModelMergedAndThree(),
              JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(3L)),
              StreamJoined.with(Serdes.String(), new JsonSerde<MergedModel>(MergedModel.class),
                        new JsonSerde<>(InputThree.class)));

    joined.to(output, Produced.with(Serdes.String(), new JsonSerde<>(MergedModel.class)));

    Topology topology = builder.build();

    final KafkaStreams streams = new KafkaStreams(topology, factoryBean.getStreamsConfiguration());
    final CountDownLatch latch = new CountDownLatch(1);

    // attach shutdown handler to catch control-c
    Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
      @Override
      public void run() {
        streams.close();
        latch.countDown();
      }
    });

    try {
      streams.start();
      latch.await();
    } catch (Throwable e) {
      System.exit(1);
    }
  }
}
