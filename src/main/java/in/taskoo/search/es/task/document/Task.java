package in.taskoo.search.es.task.document;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;

import in.taskoo.search.es.common.dto.GeoLocation;
import in.taskoo.search.es.common.enums.BudgetType;
import in.taskoo.search.es.common.enums.TaskType;
import lombok.Data;

@Document(indexName = "task")
@Data
public class Task {
  @Id @Field(type = FieldType.Keyword) private String id;
  @Field(type = FieldType.Text) private String title;
  @Field(type = FieldType.Text) private String details;
  @Field(type = FieldType.Text) private String due;
  @Field(type = FieldType.Keyword) private TaskType type;
  @Field(type = FieldType.Keyword) private BudgetType budgetType;
  @Field(type = FieldType.Object) private GeoLocation location;
}
