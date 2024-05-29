package com.example.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.backend.DTO.PagedResponse;
import com.example.backend.DTO.Todo;
import com.example.backend.service.TodoService;

import jakarta.validation.Valid;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

;



@RestController
@RequestMapping("/api/v1/todos")
@CrossOrigin("http://localhost:8080")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public ResponseEntity<PagedResponse<Todo>> getTodos(
            @RequestParam(required = false) Boolean done,
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Todo.Priority priority,
            @RequestParam(defaultValue = "") String[] sortBy,
            @RequestParam(defaultValue = "0") int page) {

      
        return ResponseEntity.ok(todoService.getFilteredTodosAndSorted(done, text, priority, sortBy, page)); 
    }


    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo RequestTodo) {

        Todo todo = new Todo();
        todo.setText(RequestTodo.getText());
        todo.setPriority(RequestTodo.getPriority());
        todo.setDueDate(RequestTodo.getDueDate());
        todo.setDone(false);
        todo.setCreatedAt(LocalDateTime.now());

        Todo newTodo = todoService.createOrUpdateTodo(todo);

        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }

    @PostMapping("/{id}/done")
    public ResponseEntity<Void> markTodoAsDone (@PathVariable Long id) {
        todoService.markTodo(id, true);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/undone")
    public ResponseEntity<Void> markTodoAsUnone (@PathVariable Long id) {
        todoService.markTodo(id, false);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @Valid @RequestBody Todo RequestTodo) {

        Todo todo = todoService.getTodoById(id);

        todo.setDueDate(RequestTodo.getDueDate());
        todo.setPriority(RequestTodo.getPriority());
        todo.setText(RequestTodo.getText());

        Todo updatedTodo = todoService.createOrUpdateTodo(todo);

        return ResponseEntity.ok(updatedTodo);

    } 

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodoById(id);

        return ResponseEntity.ok().build();
    }
}
