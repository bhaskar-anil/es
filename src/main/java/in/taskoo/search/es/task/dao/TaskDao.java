package in.taskoo.search.es.task.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import in.taskoo.search.es.task.document.Task;

public interface TaskDao extends ElasticsearchCrudRepository<Task, String> {

}
