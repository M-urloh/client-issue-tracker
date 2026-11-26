package com.example.clienttracker.model;

public class Client {
    private final long id;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public Client(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}