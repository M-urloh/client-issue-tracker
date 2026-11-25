package com.example.clienttracker.model;

public class Project {
    private final long id;
    private final Client client;
    private final String name;
    private boolean active = true;

    public Project(long id, Client client, String name) {
        this.id = id;
        this.client = client;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }
}