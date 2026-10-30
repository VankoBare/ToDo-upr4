package com.example.todo_app.service;

import com.example.todo_app.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StatsService {

    private final TaskService taskService;

    public StatsService(TaskService taskService) {
        this.taskService = taskService;
    }

    public long getTotalCount() {
        return taskService.getAllTasks().size();
    }

    public long getCompletedCount() {
        List<Task> tasks = taskService.getAllTasks();
        return tasks.stream().filter(Task::isCompleted).count();
    }

    public long getActiveCount() {
        return getTotalCount() - getCompletedCount();
    }
}
