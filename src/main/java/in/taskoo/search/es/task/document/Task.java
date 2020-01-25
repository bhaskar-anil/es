package in.taskoo.search.es.task.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import in.taskoo.search.es.common.dto.GeoLocation;
import lombok.Data;

@Document(indexName = "task")
@Data
public class Task {
  @Id
  @Field(type = FieldType.Long)
  private Long id;

  @Field(type = FieldType.Text)
  private String title;

  @Field(type = FieldType.Text)
  private String description;

  @Field(type = FieldType.Date)
  private String taskDueDateTime;

  @Field(type = FieldType.Keyword)
  private String taskType;

  @Field(type = FieldType.Object)
  private GeoLocation location;

  @Field(type = FieldType.Long)
  private Long seekerId;

  @Field(type = FieldType.Integer)
  private Long estimateAmount;

  @Field(type = FieldType.Keyword)
  private String estimateType;

  @Field(type = FieldType.Text)
  private String category;
}
