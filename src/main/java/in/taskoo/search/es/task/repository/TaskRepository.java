package in.taskoo.search.es.task.repository;

import org.springframework.data.repository.CrudRepository;

import in.taskoo.search.es.task.document.Task;

public interface TaskRepository extends CrudRepository<Task, String> {

}
