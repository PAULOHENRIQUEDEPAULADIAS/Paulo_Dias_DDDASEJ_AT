package com.example.PetFriends_Transporte.model;

import java.time.Instant;

public interface DomainEvent {
    String eventType();
    Instant occurredOn();
}
