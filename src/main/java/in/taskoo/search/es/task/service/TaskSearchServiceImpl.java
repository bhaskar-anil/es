package in.taskoo.search.es.task.service;

import static in.taskoo.search.es.common.enums.Operator.NONE;
import static in.taskoo.search.es.common.enums.Operator.OR;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.util.Pair;

import in.taskoo.search.es.common.enums.Operator;
import in.taskoo.search.es.task.document.Task;
import in.taskoo.search.es.task.dto.TaskDto;
import in.taskoo.search.es.task.repository.TaskSearchRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskSearchServiceImpl implements TaskSearchService {
  private static final Logger LOGGER = LoggerFactory.getLogger(TaskSearchServiceImpl.class);
  private final TaskSearchRepository taskSearchRepository;

  @Override
  public void saveATask(TaskDto taskDto) throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public List<Long> searchForIds(String query, Pageable pageable, String filters) throws Exception {
    SearchQuery searchQuery = prepareSearchQuery(query, pageable, filters);
    List<Task> tasks = getTasksBySearchQuery(searchQuery);
    return extractIds(tasks);
  }

  private List<Task> getTasksBySearchQuery(SearchQuery searchQuery) {
    return taskSearchRepository.search(searchQuery).getContent();
  }

  private SearchQuery prepareSearchQuery(String query, Pageable pageable, String filters) {
    QueryBuilder qb = buildQueryBuilder(query, filters);
    return new NativeSearchQueryBuilder()
        .withPageable(pageable)
        .withQuery(qb).build();
  }

  private QueryBuilder buildQueryBuilder(String query, String filters) {
    Map<String, Pair<Operator, List<String>>> filtersMap = buildFiltersMap(filters);
    BoolQueryBuilder qb = QueryBuilders.boolQuery();

    filtersMap.entrySet().forEach(filter -> {
      switch (filter.getValue().getFirst()) {
      case NONE:
        qb.must(termQuery(filter.getKey(), filter.getValue().getSecond().get(0)));
        break;
      case OR:
        filter.getValue().getSecond().forEach(value -> {
          qb.should(termQuery(filter.getKey(), value));
        });
        break;
      case AND:
        filter.getValue().getSecond().forEach(value -> {
          qb.must(termQuery(filter.getKey(), value));
        });
        break;

      default:
        break;
      }
    });
    return qb;
  }

  private List<Long> extractIds(List<Task> tasks) {
    return tasks.stream()
        .map(task -> task.getId())
        .collect(Collectors.toList());
  }

  /**
   * filters=status:open,in_progress;type:remote;locality:mg%20%road;locality:indiranagar
   * 
   * @param filters
   * @return
   */
  private static Map<String, Pair<Operator, List<String>>> buildFiltersMap(String filters) {
    String[] filterQueries = filters.split(";");
    /**
     * status:open,in_progress type:remote locality:mg road locality:indiranagar
     * 
     */
    Map<String, Pair<Operator, List<String>>> filtersMap = new HashMap<>();

    for (int i = 0; i < filterQueries.length; i++) {
      String[] filterQuery = filterQueries[i].split(":");
      String key = filterQuery[0];
      String valueString = filterQuery[1];

      if (Objects.isNull(filtersMap.get(key))) {
        filtersMap.put(key, generateValuePair(valueString, null));
      } else {
        filtersMap.put(key, generateValuePair(valueString, filtersMap.get(key).getSecond()));
      }
    }
    return filtersMap;
  }

  private static Pair<Operator, List<String>> generateValuePair(String valueString, List<String> existingValues) {
    Pair<Operator, List<String>> result = null;
    
    if (existingValues == null) {
      if (hasOR(valueString)) {
        result = Pair.of(OR, Arrays.asList(valueString.split(",")));
      } else {
        result = Pair.of(NONE, Arrays.asList(valueString));
      }
    } else {
      if (hasOR(valueString)) {
        LOGGER.error("mutual exclusive conditions are passed error");
        // throw exception here
      } else {
        result = Pair.of(Operator.AND, new ArrayList<String>() {
          private static final long serialVersionUID = 3541266211115017109L;
          {
            addAll(existingValues);
            add(valueString);
          }
        });
      }
    }
    return result;
  }

  private static boolean hasOR(String input) {
    return input.contains(",");
  }
}
