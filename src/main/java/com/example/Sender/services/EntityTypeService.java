package com.example.Sender.services;

import com.example.Sender.enums.EntityType;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EntityTypeService {
    public String getStringEntityTypeByParameter(String parameter) {
        return Objects
                .requireNonNull(EntityType.fromParameter(parameter))
                .getParameter();
    }

    public EntityType getEntityTypeByParameter(String parameter) {
        return EntityType.fromParameter(parameter);
    }


}
