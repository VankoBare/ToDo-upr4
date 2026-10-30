package com.example.todo_app;

import com.example.todo_app.model.Task;
import com.example.todo_app.service.StatsService;
import com.example.todo_app.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ToDoAppApplicationTests {

    @Autowired
    private TaskService taskService;

    @Autowired
    private StatsService statsService;

    @Test
    void contextLoads() {
    }

    @Test
    void addToggleAndStats() {
        long before = statsService.getTotalCount();
        Task task = taskService.addTask("  Тестова задача ", "описание");
        assertEquals("Тестова задача", task.getTitle());
        assertEquals(before + 1, statsService.getTotalCount());

        long completedBefore = statsService.getCompletedCount();
        taskService.toggleCompleted(task.getId());
        assertEquals(completedBefore + 1, statsService.getCompletedCount());

        taskService.deleteTask(task.getId());
        assertTrue(taskService.getTask(task.getId()).isEmpty());
    }
}
