package com.example.todo_app.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class TaskForm {

    @NotBlank(message = "Заглавието е задължително и не може да съдържа само интервали.")
    @Size(max = 255, message = "Заглавието може да е най-много 255 символа.")
    private String title;

    @Size(max = 2000, message = "Описанието може да е най-много 2000 символа.")
    private String description;

    public TaskForm() {
    }

    public TaskForm(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
