package io.chris.kafka.streamsjoiner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MergedModel {

  private InputOne one;
  private InputTwo two;
  private InputThree three;

}
