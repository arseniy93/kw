package com.example.Sender.services;


import com.example.Sender.models.ClientType;
import com.example.Sender.repository.ClientTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientTypeService {
    private final ClientTypeRepository clientTypeRepository;

    public ClientType getClientTypeByName(String name) {
        return clientTypeRepository.getClientTypeByName(name);
    }


}
