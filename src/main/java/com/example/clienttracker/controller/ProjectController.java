package com.example.clienttracker.controller;

import com.example.clienttracker.service.ClientService;
import com.example.clienttracker.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectController {
    private final ClientService clientService;
    private final ProjectService projectService;

    public ProjectController(ClientService clientService, ProjectService projectService) {
        this.clientService = clientService;
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public String showProjects(Model model) {
        model.addAttribute("clients", clientService.getClients());
        model.addAttribute("projects", projectService.getProjects());
        return "projects";
    }

    @PostMapping("/projects")
    public String addProject(@RequestParam("clientId") long clientId,
            @RequestParam("name") String name,
            Model model) {
        try {
            projectService.addProject(clientId, name);
            return "redirect:/projects";
        } catch (IllegalArgumentException error) {
            model.addAttribute("error", error.getMessage());
            model.addAttribute("selectedClientId", clientId);
            model.addAttribute("enteredName", name);
            return showProjects(model);
        }
    }
}