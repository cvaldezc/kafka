package io.chris.kafka.streamsjoiner.joiner;

import io.chris.kafka.streamsjoiner.model.InputOne;
import io.chris.kafka.streamsjoiner.model.InputTwo;
import io.chris.kafka.streamsjoiner.model.MergedModel;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class ModelOneAndTwo implements ValueJoiner<InputOne, InputTwo, MergedModel> {
  @Override
  public MergedModel apply(InputOne inputOne, InputTwo inputTwo) {
    return MergedModel.builder().one(inputOne).two(inputTwo).build();
  }
}
