package com.example.taskflow.controller;

import com.example.taskflow.entity.User;
import com.example.taskflow.service.TaskService;
import com.example.taskflow.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import org.springframework.ui.Model;



@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String index(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("tasks", taskService.getTasksByUser_Username(username));
        return "task-list";
    }

    @GetMapping("/new")
    public String newTask(Model model) {
        model.addAttribute("task", new Task());
        return "task-form";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute("task") Task task, @AuthenticationPrincipal User user) {
        task.setUser(user);
        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/toggle-status/{id}")
    public String toggleStatus(@PathVariable("id") Long id) {
        Task task = taskService.getTaskById(id);
        task.setCompleted(!task.isCompleted());
        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable("id") Long id, Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        return "task-form";
    }
}
