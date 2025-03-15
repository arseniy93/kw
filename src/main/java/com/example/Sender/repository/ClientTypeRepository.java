package com.example.Sender.repository;

import com.example.Sender.models.ClientType;
import org.springframework.data.repository.CrudRepository;

public interface ClientTypeRepository extends CrudRepository<ClientType, Integer> {
    ClientType getClientTypeById(Integer id);
}
