package com.example.PetFriends_Transporte.model;

import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    protected void addEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    public List<DomainEvent> drainEvents() {
        List<DomainEvent> copy = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return copy;
    }
}
