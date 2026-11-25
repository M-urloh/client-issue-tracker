package com.example.clienttracker.service;

import com.example.clienttracker.model.Client;
import com.example.clienttracker.model.Project;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    private final ClientService clientService;
    private final List<Project> projects = new ArrayList<>();
    private long nextId = 1;

    public ProjectService(ClientService clientService) {
        this.clientService = clientService;
    }

    public synchronized Project addProject(long clientId, String name) {
        Client owner = clientService.findById(clientId);

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Enter a project name.");
        }

        String cleanName = name.trim();
        for (Project project : projects) {
            boolean sameOwner = project.getClient().getId() == clientId;
            boolean sameName = project.getName().equalsIgnoreCase(cleanName);

            if (sameOwner && sameName) {
                throw new IllegalArgumentException("This client already has a project with that name.");
            }
        }

        Project project = new Project(nextId, owner, cleanName);
        projects.add(project);
        nextId++;
        return project;
    }

    public synchronized List<Project> getProjects() {
        return List.copyOf(projects);
    }
}