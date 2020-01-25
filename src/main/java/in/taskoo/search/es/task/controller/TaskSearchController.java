package in.taskoo.search.es.task.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import in.taskoo.search.es.task.document.Task;
import in.taskoo.search.es.task.service.TaskSearchService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/es/tasks")
public class TaskSearchController {

  @Value("${document.templates.filetypes:}")
  private String[] feilds;

  private static final Logger LOGGER = LoggerFactory.getLogger(TaskSearchController.class);
  
  private final ElasticsearchOperations elasticsearchOperations;

  private final TaskSearchService taskSearchService;
  
  /**
   * this method saves a task object into elastic search data use this method to
   * save a new task or update an existing task
   * 
   * it requires a task to have id before saving it
   * 
   * @param task
   */
  @PostMapping("/")
  public void saveTask(@RequestBody Task task) {
    if (StringUtils.isEmpty(task.getId())) {
      // through some exception here, no point saving it here
      LOGGER.error("task id is mandatory to save a task into elastic search data" + task.getTitle());
    }
    IndexQuery indexQuery = new IndexQueryBuilder()
        .withId(task.getId().toString())
        .withObject(task)
        .build();
      String documentId = elasticsearchOperations.index(indexQuery);
      LOGGER.debug("task is saved with id: " + documentId);
  }

  /**
   * 
   * this method searches for tasks
   * 
   * @param query
   * @param pageable
   * @param select
   * @return
   * @throws Exception
   */
  @GetMapping("/search")
  public @ResponseBody List<Long> search(@RequestParam String query, @RequestParam(required = false) Pageable pageable,
      @RequestParam(required = false) String filters) throws Exception {
    return taskSearchService.searchForIds(query, pageable, filters);
  }

}
