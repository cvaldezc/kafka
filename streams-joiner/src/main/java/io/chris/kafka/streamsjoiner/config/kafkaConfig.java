package io.chris.kafka.streamsjoiner.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.support.serializer.JsonSerde;

@EnableKafka
@Configuration
public class kafkaConfig {

  @Value("${spring.kafka.bootstrap-server}")
  private String bootstrapAddress;

  @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
  public KafkaStreamsConfiguration kStreamsConfig() {
    Map<String, Object> props = new HashMap<>();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-app");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class.getName());
    props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 4);
//    props.put(StreamsConfig.PROBING_REBALANCE_INTERVAL_MS_CONFIG, 4);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "300000"); // Tiempo máximo entre recepción de mensajes
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "500");

    return new KafkaStreamsConfiguration(props);
  }

  @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_BUILDER_BEAN_NAME)
  public StreamsBuilderFactoryBean streamsBuilderFactoryBean() {
    return new StreamsBuilderFactoryBean(kStreamsConfig());
  }
}
