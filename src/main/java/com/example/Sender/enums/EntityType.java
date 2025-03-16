package com.example.Sender.enums;

import lombok.Getter;

@Getter
public enum EntityType {
    NEW("новый"),
    OLD("давний"),
    CONSTANTLY("постоянный");

    private final String parameter;

    EntityType(String parameter) {
        this.parameter=parameter;
    }
    public static EntityType[] getParameters() {
        return values();
    }

    public static EntityType fromParameter(String parameter) {
        for (EntityType entityType : EntityType.values()) {
            if (entityType.getParameter().equalsIgnoreCase(parameter)) {
                return entityType;
            }
        }
        return null;
    }

}
