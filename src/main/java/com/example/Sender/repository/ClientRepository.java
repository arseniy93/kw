package com.example.Sender.repository;

import com.example.Sender.models.Client;
import com.example.Sender.models.ClientType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Integer> {
    List<Client> getAllByClientType(ClientType clientType);
    Client getByEmail(String mail);
}
