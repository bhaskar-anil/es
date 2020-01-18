package in.taskoo.search.es.common.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BudgetType {

  HOURLY(1), TOTAL(2);

  @JsonCreator
  public static BudgetType toEnum(Integer value) {
    return Arrays.stream(BudgetType.values()).filter(type -> type.getId().equals(value)).findFirst().orElseThrow(null);
  }

  private final Integer id;

}
