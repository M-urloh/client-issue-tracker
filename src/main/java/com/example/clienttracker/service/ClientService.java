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
        String cleanName = validateName(name, null);
        Client client = new Client(nextId, cleanName);
        clients.add(client);
        nextId++;
        return client;
    }

    public synchronized void renameClient(long id, String name) {
        Client client = findById(id);
        String cleanName = validateName(name, client);
        client.setName(cleanName);
    }

    private String validateName(String name, Client clientBeingEdited) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Enter a client name.");
        }

        String cleanName = name.trim();
        for (Client existingClient : clients) {
            if (existingClient == clientBeingEdited) {
                continue;
            }
            if (existingClient.getName().equalsIgnoreCase(cleanName)) {
                throw new IllegalArgumentException("A client with this name already exists.");
            }
        }
        return cleanName;
    }

    public synchronized List<Client> getClients() {
        return List.copyOf(clients);
    }

    public synchronized Client findById(long id) {
        for (Client client : clients) {
            if (client.getId() == id) {
                return client;
            }
        }

        throw new IllegalArgumentException("The selected client could not be found.");
    }

}