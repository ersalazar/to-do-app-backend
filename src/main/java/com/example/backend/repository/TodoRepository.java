package com.example.backend.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.backend.DTO.Todo;

@Repository
public class TodoRepository {
    private HashMap<Long, Todo> todos = new HashMap<Long, Todo>();

    private AtomicLong idCounter = new AtomicLong();

    public Todo save(Todo todo) {
        if (todo.getId() == null) {
            todo.setId(idCounter.incrementAndGet());
        }
        todos.put(todo.getId(), todo);
        //System.out.println("New Todo made");
        return todo;
    }
    
    public Collection<Todo> findAll() {
        return todos.values();
    }
    
    public Todo findById(Long id) {
        return todos.get(id);
    }

    public void deleteById(Long id) {
        todos.remove(id);
        return;
    }

    public Collection<Todo> findAndFilter(Boolean done, String text, Todo.Priority priority) {
        //System.out.println("done " + Boolean.toString(done));
        //System.out.println("text " + text);
        //System.out.println("text " + priority.toString());
        //System.out.println(todos.toString());

        return todos.values().stream()
                .filter(todo -> done == null || todo.getDone() == done)
                .filter(todo -> text == null || todo.getText().contains(text))
                .filter(todo -> priority == null || todo.getPriority() == priority)
                .collect(Collectors.toList());
    }

    public void markAs(Long id, Boolean done) {
        if (todos.get(id).getDone() == done) {
            return;
        }
        todos.get(id).setDone(done);
        if (done) {
            todos.get(id).setDoneDate(LocalDateTime.now());
        } else {
            todos.get(id).setDoneDate(null);
        }
        return;
    }

    
}
