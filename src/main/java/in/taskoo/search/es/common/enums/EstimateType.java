package in.taskoo.search.es.common.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EstimateType {
  HOURLY(0), TOTAL(1);

  @JsonCreator
  public static EstimateType toEnum(Integer value) {
    return Arrays.stream(EstimateType.values()).filter(type -> type.getId().equals(value)).findFirst()
        .orElseThrow(null);
  }

  private final Integer id;

  @JsonValue
  public String getTaskType() {
    return this.toString();
  }
}
