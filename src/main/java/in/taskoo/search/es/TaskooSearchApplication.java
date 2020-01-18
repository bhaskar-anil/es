package in.taskoo.search.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "in.taskoo.search.es" })
public class TaskooSearchApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaskooSearchApplication.class, args);
  }

}
