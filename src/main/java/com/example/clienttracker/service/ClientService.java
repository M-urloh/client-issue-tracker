package com.example.clienttracker.service;

import com.example.clienttracker.model.Client;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    private final List<Client> clients = new ArrayList<>();
    private long nextId = 1;

public synchronized Client addClient(String name) {
    if (name == null || name.isBlank()) {
        throw new IllegalArgumentException("Enter a client name.");
    }

    String cleanName = name.trim();

    for (Client existingClient : clients) {
        if (existingClient.getName().equalsIgnoreCase(cleanName)) {
            throw new IllegalArgumentException("A client with this name already exists.");
        }
    }

    Client client = new Client(nextId, cleanName);
    clients.add(client);
    nextId++;
    return client;
}   
    public synchronized List<Client> getClients() {
    return List.copyOf(clients);
}
}