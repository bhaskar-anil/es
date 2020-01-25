package in.taskoo.search.es.task.dto;

import java.time.LocalDateTime;

import in.taskoo.search.es.common.dto.GeoLocation;
import lombok.Data;

@Data
public class TaskDto {
  private String id;

  private String title;

  private String description;

  private LocalDateTime taskDueDateTime;

  private String taskType;

  private GeoLocation location;

  private Long seekerId;

  private Long estimateAmount;

  private String estimateType;

  private String category;
}
