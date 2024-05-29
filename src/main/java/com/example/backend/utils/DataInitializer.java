package com.example.backend.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.backend.DTO.Todo;
import com.example.backend.repository.TodoRepository;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public void run(String... args) throws Exception {
        Todo todo1 = new Todo();
        todo1.setText("Buy groceries");
        todo1.setPriority(Todo.Priority.High);
        todo1.setDueDate(LocalDateTime.now().plusDays(1));
        todo1.setDone(false);
        todo1.setCreatedAt(LocalDateTime.now());

        Todo todo2 = new Todo();
        todo2.setText("Complete project report");
        todo2.setPriority(Todo.Priority.Medium);
        todo2.setDueDate(LocalDateTime.now().plusDays(3));
        todo2.setDone(false);
        todo2.setCreatedAt(LocalDateTime.now());

        Todo todo3 = new Todo();
        todo3.setText("Book dentist appointment");
        todo3.setPriority(Todo.Priority.Low);
        todo3.setDueDate(LocalDateTime.now().plusDays(7));
        todo3.setDone(false);
        todo3.setCreatedAt(LocalDateTime.now());

        Todo todo4 = new Todo();
        todo4.setText("Plan weekend trip");
        todo4.setPriority(Todo.Priority.Medium);
        todo4.setDueDate(LocalDateTime.now().plusDays(5));
        todo4.setDone(false);
        todo4.setCreatedAt(LocalDateTime.now());

        Todo todo5 = new Todo();
        todo5.setText("Submit tax returns");
        todo5.setPriority(Todo.Priority.High);
        todo5.setDueDate(LocalDateTime.now().plusDays(10));
        todo5.setDone(false);
        todo5.setCreatedAt(LocalDateTime.now());

        Todo todo6 = new Todo();
        todo6.setText("Renew car insurance");
        todo6.setPriority(Todo.Priority.Medium);
        todo6.setDueDate(LocalDateTime.now().plusDays(14));
        todo6.setDone(false);
        todo6.setCreatedAt(LocalDateTime.now());

        Todo todo7 = new Todo();
        todo7.setText("Pay utility bills");
        todo7.setPriority(Todo.Priority.High);
        todo7.setDueDate(LocalDateTime.now().plusDays(2));
        todo7.setDone(false);
        todo7.setCreatedAt(LocalDateTime.now());

        Todo todo8 = new Todo();
        todo8.setText("Call plumber");
        todo8.setPriority(Todo.Priority.Low);
        todo8.setDueDate(LocalDateTime.now().plusDays(6));
        todo8.setDone(false);
        todo8.setCreatedAt(LocalDateTime.now());

        Todo todo9 = new Todo();
        todo9.setText("Finish reading book");
        todo9.setPriority(Todo.Priority.Low);
        todo9.setDueDate(LocalDateTime.now().plusDays(9));
        todo9.setDone(false);
        todo9.setCreatedAt(LocalDateTime.now());

        Todo todo10 = new Todo();
        todo10.setText("Prepare presentation");
        todo10.setPriority(Todo.Priority.High);
        todo10.setDueDate(LocalDateTime.now().plusDays(4));
        todo10.setDone(false);
        todo10.setCreatedAt(LocalDateTime.now());

        Todo todo11 = new Todo();
        todo11.setText("Update resume");
        todo11.setPriority(Todo.Priority.Medium);
        todo11.setDueDate(LocalDateTime.now().plusDays(8));
        todo11.setDone(false);
        todo11.setCreatedAt(LocalDateTime.now());

        Todo todo12 = new Todo();
        todo12.setText("Clean garage");
        todo12.setPriority(Todo.Priority.Low);
        todo12.setDueDate(LocalDateTime.now().plusDays(11));
        todo12.setDone(false);
        todo12.setCreatedAt(LocalDateTime.now());

        Todo todo13 = new Todo();
        todo13.setText("Buy birthday gift");
        todo13.setPriority(Todo.Priority.High);
        todo13.setDueDate(LocalDateTime.now().plusDays(12));
        todo13.setDone(false);
        todo13.setCreatedAt(LocalDateTime.now());

        Todo todo14 = new Todo();
        todo14.setText("Schedule annual checkup");
        todo14.setPriority(Todo.Priority.Medium);
        todo14.setDueDate(LocalDateTime.now().plusDays(13));
        todo14.setDone(false);
        todo14.setCreatedAt(LocalDateTime.now());

        Todo todo15 = new Todo();
        todo15.setText("Organize files");
        todo15.setPriority(Todo.Priority.Low);
        todo15.setDueDate(LocalDateTime.now().plusDays(15));
        todo15.setDone(false);
        todo15.setCreatedAt(LocalDateTime.now());

        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);
        todoRepository.save(todo4);
        todoRepository.save(todo5);
        todoRepository.save(todo6);
        todoRepository.save(todo7);
        todoRepository.save(todo8);
        todoRepository.save(todo9);
        todoRepository.save(todo10);
        todoRepository.save(todo11);
        todoRepository.save(todo12);
        todoRepository.save(todo13);
        todoRepository.save(todo14);
        todoRepository.save(todo15);
    }
}

