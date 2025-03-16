package com.example.Sender.repository;

import com.example.Sender.enums.EntityType;
import com.example.Sender.models.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {
    List<Client> getAllByClientEntityType(EntityType clientType);
    Client getByEmail(String mail);
    Optional<Client> findClientByEmail(String email);
}
