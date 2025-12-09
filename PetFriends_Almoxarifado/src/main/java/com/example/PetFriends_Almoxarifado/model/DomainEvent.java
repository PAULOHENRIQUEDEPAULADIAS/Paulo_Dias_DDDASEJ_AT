package com.example.PetFriends_Almoxarifado.model;

import java.time.Instant;

public interface DomainEvent {
    String eventType();
    Instant occurredOn();
}
