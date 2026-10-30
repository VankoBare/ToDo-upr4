package com.example.todo_app.controller;

import com.example.todo_app.model.Task;
import com.example.todo_app.model.TaskForm;
import com.example.todo_app.service.StatsService;
import com.example.todo_app.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;


@Controller
public class TaskController {

    private final TaskService taskService;
    private final StatsService statsService;

    public TaskController(TaskService taskService, StatsService statsService) {
        this.taskService = taskService;
        this.statsService = statsService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/tasks";
    }


    @GetMapping("/tasks")
    public String showTasks(Model model) {
        model.addAttribute("taskForm", new TaskForm());
        fillModel(model);
        return "tasks";
    }


    @PostMapping("/tasks")
    public String addTask(@Valid TaskForm taskForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            fillModel(model);
            return "tasks";
        }
        taskService.addTask(taskForm.getTitle(), taskForm.getDescription());
        return "redirect:/tasks";
    }


    @GetMapping("/tasks/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Task> task = taskService.getTask(id);
        if (task.isEmpty()) {
            return "redirect:/tasks";
        }
        model.addAttribute("taskForm", new TaskForm(task.get().getTitle(), task.get().getDescription()));
        model.addAttribute("editingId", id);
        fillModel(model);
        return "tasks";
    }


    @PostMapping("/tasks/{id}/edit")
    public String updateTask(@PathVariable Long id, @Valid TaskForm taskForm,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("editingId", id);
            fillModel(model);
            return "tasks";
        }
        taskService.updateTask(id, taskForm.getTitle(), taskForm.getDescription());
        return "redirect:/tasks";
    }


    @PostMapping("/tasks/{id}/toggle")
    public String toggleTask(@PathVariable Long id) {
        taskService.toggleCompleted(id);
        return "redirect:/tasks";
    }


    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    private void fillModel(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("total", statsService.getTotalCount());
        model.addAttribute("completed", statsService.getCompletedCount());
        model.addAttribute("active", statsService.getActiveCount());
    }
}
