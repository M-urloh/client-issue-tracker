package com.example.clienttracker.controller;

import com.example.clienttracker.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public String showClients(Model model) {
        model.addAttribute("clients", clientService.getClients());
        return "clients";
    }

    @GetMapping("/clients/{id}/edit")
    public String showEditClient(@PathVariable("id") long id, Model model) {
        try {
            model.addAttribute("client", clientService.findById(id));
            return "edit-client";
        } catch (IllegalArgumentException error) {
            model.addAttribute("error", error.getMessage());
            return showClients(model);
        }
    }

    @PostMapping("/clients/{id}/edit")
    public String saveClientChanges(@PathVariable("id") long id,
            @RequestParam("name") String name,
            Model model) {
        try {
            clientService.renameClient(id, name);
            return "redirect:/clients";
        } catch (IllegalArgumentException error) {
            model.addAttribute("error", error.getMessage());
            model.addAttribute("enteredName", name);
            return showEditClient(id, model);
        }
    }

    @PostMapping("/clients")
    public String addClient(@RequestParam("name") String name, Model model) {
        try {
            clientService.addClient(name);
            return "redirect:/clients";
        } catch (IllegalArgumentException error) {
            model.addAttribute("error", error.getMessage());
            model.addAttribute("enteredName", name);
            return showClients(model);
        }
    }
}