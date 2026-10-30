package com.example.todo_app.service;

import com.example.todo_app.model.Task;
import com.example.todo_app.repository.TaskRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TaskService {

    private final TaskRepository taskRepository;

    // Constructor injection - Spring подава TaskRepository автоматично
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll(Sort.by(Sort.Direction.ASC, "completed")
                .and(Sort.by(Sort.Direction.DESC, "createdAt")));
    }

    public Optional<Task> getTask(Long id) {
        return taskRepository.findById(id);
    }

    public Task addTask(String title, String description) {
        return taskRepository.save(new Task(title.trim(), normalize(description)));
    }

    public Optional<Task> updateTask(Long id, String title, String description) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(title.trim());
            task.setDescription(normalize(description));
            return taskRepository.save(task);
        });
    }


    public Optional<Task> toggleCompleted(Long id) {
        return taskRepository.findById(id).map(task -> {
            task.setCompleted(!task.isCompleted());
            return taskRepository.save(task);
        });
    }

    public void deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        }
    }

    private String normalize(String description) {
        return description == null ? "" : description.trim();
    }
}
