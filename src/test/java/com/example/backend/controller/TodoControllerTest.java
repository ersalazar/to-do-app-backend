package com.example.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.backend.DTO.PagedResponse;
import com.example.backend.DTO.Todo;
import com.example.backend.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void ShouldCreateTodo() throws Exception {
        
        Todo createdTodo = new Todo();
        createdTodo.setId(1L);
        createdTodo.setText("Test todo");
        createdTodo.setPriority(Todo.Priority.High);
        createdTodo.setDone(false);
        createdTodo.setCreatedAt(LocalDateTime.now());

        when(todoService.createOrUpdateTodo(any(Todo.class))).thenReturn(createdTodo);

        String requestBody = "{\"text\":\"Test todo\",\"priority\":\"High\"}";

        mockMvc.perform(post("/api/v1/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdTodo.getId()))
                .andExpect(jsonPath("$.text").value(createdTodo.getText()))
                .andExpect(jsonPath("$.priority").value(createdTodo.getPriority().toString()))
                .andExpect(jsonPath("$.done").value(createdTodo.getDone()))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    void ShouldDeleteTodo() throws Exception {
        
        doNothing().when(todoService).deleteTodoById(1L);
        mockMvc.perform(delete("/api/v1/todos/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk());
        verify(todoService, times(1)).deleteTodoById(1L);
    }

    @Test
    void testGetTodos() throws Exception {

        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setText("Test todo 1");
        todo1.setPriority(Todo.Priority.High);
        todo1.setDone(false);
        todo1.setCreatedAt(LocalDateTime.now());

        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setText("Test todo 2");
        todo2.setPriority(Todo.Priority.Low);
        todo2.setDone(true);
        todo2.setCreatedAt(LocalDateTime.now());

        List<Todo> todos = List.of(todo1, todo2);
        PagedResponse<Todo> pagedResponse = new PagedResponse<>(
            0, 1, 2, todos,
            10.0, 
            12.0, 
            8.0,  
            6.0   
        );

        when(todoService.getFilteredTodosAndSorted(null, null, null, new String[0], 0)).thenReturn(pagedResponse);

        mockMvc.perform(get("/api/v1/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id").value(todo1.getId()))
                .andExpect(jsonPath("$.items[0].text").value(todo1.getText()))
                .andExpect(jsonPath("$.items[0].priority").value(todo1.getPriority().toString()))
                .andExpect(jsonPath("$.items[0].done").value(todo1.getDone()))
                .andExpect(jsonPath("$.items[0].createdAt").exists())
                .andExpect(jsonPath("$.items[1].id").value(todo2.getId()))
                .andExpect(jsonPath("$.items[1].text").value(todo2.getText()))
                .andExpect(jsonPath("$.items[1].priority").value(todo2.getPriority().toString()))
                .andExpect(jsonPath("$.items[1].done").value(todo2.getDone()))
                .andExpect(jsonPath("$.items[1].createdAt").exists())
                .andExpect(jsonPath("$.currentPage").value(0))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalItems").value(2))
                .andExpect(jsonPath("$.avgTimeToFinishTasks").value(10.0))
                .andExpect(jsonPath("$.avgTimeToFinishTasksPriorityLow").value(12.0))
                .andExpect(jsonPath("$.avgTimeToFinishTaskspriorityMedium").value(8.0))
                .andExpect(jsonPath("$.avgTimeToFinishTaskspriorityHigh").value(6.0));

    }

    @Test
    void testMarkTodoAsDone() throws Exception { 

        Long todoId = 1L;

        doNothing().when(todoService).markTodo(todoId, true);

        mockMvc.perform(post("/api/v1//todos/{id}/done", todoId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(todoService, times(1)).markTodo(todoId, true);
    }

    @Test
    void testMarkTodoAsUnone() throws Exception {
        Long todoId = 1L;

        doNothing().when(todoService).markTodo(todoId, false);

        mockMvc.perform(put("/api/v1//todos/{id}/undone", todoId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(todoService, times(1)).markTodo(todoId, false);


    }

    @Test
    void testUpdateTodo() throws Exception {
        Long todoId = 1L;

        Todo existingTodo = new Todo();
        existingTodo.setId(todoId);
        existingTodo.setText("Existing todo");
        existingTodo.setPriority(Todo.Priority.Medium);
        existingTodo.setDone(false);
        existingTodo.setCreatedAt(LocalDateTime.now());

        Todo updatedTodo = new Todo();
        updatedTodo.setId(todoId);
        updatedTodo.setText("Updated todo");
        updatedTodo.setPriority(Todo.Priority.High);
        updatedTodo.setDueDate(LocalDateTime.now().plusDays(1));
        updatedTodo.setDone(false);
        updatedTodo.setCreatedAt(existingTodo.getCreatedAt());

        when(todoService.getTodoById(todoId)).thenReturn(existingTodo);
        when(todoService.createOrUpdateTodo(any(Todo.class))).thenReturn(updatedTodo);

        String requestBody = objectMapper.writeValueAsString(updatedTodo);

        mockMvc.perform(put("/api/v1/todos/{id}", todoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedTodo.getId()))
                .andExpect(jsonPath("$.text").value(updatedTodo.getText()))
                .andExpect(jsonPath("$.priority").value(updatedTodo.getPriority().toString()))
                .andExpect(jsonPath("$.dueDate").exists())
                .andExpect(jsonPath("$.done").value(updatedTodo.getDone()))
                .andExpect(jsonPath("$.createdAt").exists());


        verify(todoService, times(1)).getTodoById(todoId);
        verify(todoService, times(1)).createOrUpdateTodo(any(Todo.class));

    }
}
