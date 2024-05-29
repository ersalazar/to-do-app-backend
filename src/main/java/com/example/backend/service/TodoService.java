package com.example.backend.service;

import java.time.Duration;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.DTO.PagedResponse;
import com.example.backend.DTO.Todo;
import com.example.backend.repository.TodoRepository;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    public Todo createOrUpdateTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public void deleteTodoById(Long id) {
        todoRepository.deleteById(id);
    }

    public void markTodo(Long id, Boolean done) {
        todoRepository.markAs(id, done);
    }

    public Collection<Todo> getTodos() {
        return todoRepository.findAll();
    }

    public PagedResponse<Todo> getFilteredTodosAndSorted(Boolean done, String text, Todo.Priority priority, String[] sortParams, int page) {

        Collection<Todo> todos = todoRepository.findAndFilter(done, text, priority);
        //System.out.println(todos);

        List<Todo> sortedTodos;
 
        if (sortParams.length > 0) {
            Comparator<Todo> comparator =  parseSortParams(sortParams);

            sortedTodos = todos.stream().sorted(comparator).toList();
        }
        else {
            sortedTodos = todos.stream().toList();
        }

        return paginateTodos(sortedTodos, page, 10);
        
    }
    
    
    private Comparator<Todo> parseSortParams(String[] sortParams) {
        Comparator<Todo> comparator = null;
    
        for (String param : sortParams) {
            String[] split = param.split(":");
            String property = split[0];
            boolean isDescending = split.length > 1 && "desc".equalsIgnoreCase(split[1]);
            Comparator<Todo> newComparator;
    
            switch (property) {
                case "priority":
                    newComparator = Comparator.comparing(Todo::getPriority);
                    break;
                case "dueDate":
                    newComparator = Comparator.comparing(Todo::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()));
                    break;
                default:
                    continue;
            }
    
            if (isDescending) {
                newComparator = newComparator.reversed();
            }
    
            if (comparator == null) {
                comparator = newComparator;
            } else {
                comparator = comparator.thenComparing(newComparator);
            }
        }
    
        return comparator;
    }
    
    
    private PagedResponse<Todo> paginateTodos(List<Todo> todos, int page, int size) {
        int start = page * size;
        int end = Math.min(start + size, todos.size());
        
        int totalTodos = todos.size();
        int totalPages = (int) Math.ceil((double)totalTodos / size );

        List<Todo> paginatedTodos = todos.subList(start, end);

        HashMap<String, Double> averages = getAvgTime(todos);

        PagedResponse<Todo> response = new PagedResponse<Todo>(page, totalPages, totalTodos,
         paginatedTodos, averages.get("All"), averages.getOrDefault("Low", 0.0), averages.getOrDefault("Medium", 0.0), averages.getOrDefault("High", 0.0));
        
        return response;
    }

    private HashMap<String, Double> getAvgTime(List<Todo> todos) {

        List<Todo> completedTodos = todos.stream()
                .filter(todo -> todo.getDone() && todo.getDoneDate() != null)
                .toList();
        
        HashMap<String, Double> averages = new HashMap<>();

        if (completedTodos.isEmpty()) {
            averages.put("All", 0.0);
            averages.put("High", 0.0);
            averages.put("Medium", 0.0);
            averages.put("Low", 0.0);
            return averages;
        }
        
        double overallAverage = completedTodos.stream()
                .mapToDouble(todo -> Duration.between(todo.getCreatedAt(), todo.getDoneDate()).toMinutes())
                .average()
                .orElse(0.0);
            averages.put("All", overallAverage);

        Map<Todo.Priority, Double> priorityAverages = completedTodos.stream()
                .collect(Collectors.groupingBy(
                    Todo::getPriority,
                    Collectors.averagingDouble(todo -> Duration.between(todo.getCreatedAt(), todo.getDoneDate()).toMinutes())
                ));

        for (Todo.Priority priority : Todo.Priority.values()) {
            averages.put(priority.toString(), priorityAverages.getOrDefault(priority, 0.0));
        }

        return averages;
    }
    
    
    
    
    
}
