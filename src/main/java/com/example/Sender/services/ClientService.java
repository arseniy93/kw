package com.example.Sender.services;


import com.example.Sender.models.Client;
import com.example.Sender.models.ClientType;
import com.example.Sender.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public List<Client> getAllClientByClientType(ClientType clientType) {
        return clientRepository.getAllByClientType(clientType);
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

    public void deleteClient(Client client){
        clientRepository.delete(client);
    }


}
