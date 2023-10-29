package io.chris.kafka.streamsjoiner.joiner;

import io.chris.kafka.streamsjoiner.model.InputThree;
import io.chris.kafka.streamsjoiner.model.MergedModel;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class ModelMergedAndThree implements ValueJoiner<MergedModel, InputThree, MergedModel> {
  @Override
  public MergedModel apply(MergedModel mergedModel, InputThree inputThree) {
    return MergedModel.builder().one(mergedModel.getOne()).two(mergedModel.getTwo()).three(inputThree).build();
  }
}
