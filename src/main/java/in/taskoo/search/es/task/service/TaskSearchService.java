package in.taskoo.search.es.task.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import in.taskoo.search.es.task.dto.TaskDto;

public interface TaskSearchService {
  List<Long> searchForIds(String query, Pageable pageable, String filters) throws Exception;
  void saveATask(TaskDto taskDto) throws Exception;
}
