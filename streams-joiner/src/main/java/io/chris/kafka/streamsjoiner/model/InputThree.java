package io.chris.kafka.streamsjoiner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputThree {
  private String id;
  private String subId;
  private String status;
}
