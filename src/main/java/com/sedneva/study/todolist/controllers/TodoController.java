package com.sedneva.study.todolist.controllers;

import com.sedneva.study.todolist.entities.Todo;
import com.sedneva.study.todolist.exception.ResourceNotFoundException;
import com.sedneva.study.todolist.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    TodoRepository todoRepository;

    //Get all todos
    @GetMapping("/todos")
    public List<Todo> getAllTodos(){
        return todoRepository.findAll();
    }

    //Create new todo
    @PostMapping("/todos")
    public Todo createTodo(@Valid @RequestBody Todo todo){
        return todoRepository.save(todo);
    }

    //Get a single todo
    @GetMapping("/todos/{id}")
    public Todo getTodo(@PathVariable(value = "id") int noteId){
        return todoRepository
                .findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note","id",noteId));

    }

    //Update todo
    @PutMapping("/todos/{id}")
    public Todo updateTodo(@PathVariable(value ="id") int todoId, @Valid @RequestBody Todo newVersion){

        Todo todo= todoRepository.findById(todoId)
                .orElseThrow(()->new ResourceNotFoundException("Todo","id", todoId));
        todo.setName(newVersion.getName());
        todo.setDone(newVersion.getDone());
        Todo updateTodo = todoRepository.save(todo);
        return updateTodo;
    }

    //Delete a todo
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable(value = "id") int todoId){
        Todo todo=todoRepository.findById(todoId)
                .orElseThrow(()->new ResourceNotFoundException("Note","id", todoId));
        todoRepository.delete(todo);
        return ResponseEntity.ok().build();

    }
}
