package com.example.Sender.services;


import com.example.Sender.enums.EntityType;
import com.example.Sender.models.Client;
import com.example.Sender.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public List<Client> getAllClientByClientType(EntityType entityType) {
        return clientRepository.getAllByClientEntityType(entityType);
    }

    public Client getClientByEmail(String email) {
        return clientRepository.getByEmail(email);
    }

    public List<Client> getClients() {
        return (List<Client>) clientRepository.findAll();
    }

    public void saveClient(Client client) {
        clientRepository.save(client);
    }


    public Optional<Client> findClientByEmail(String email) {
        return clientRepository.findClientByEmail(email);
    }

    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }


}
