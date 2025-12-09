package com.example.PetFriends_Almoxarifado.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="outbox_events")
public class OutboxEvent {
    @Id
    private UUID id;
    private String aggregateType;
    private String eventType;
    @Column(columnDefinition = "BLOB")
    private byte[] payload;
    private Instant occurredOn;
    private boolean published = false;
    private int attempts = 0;
    public void incrementAttempts() { this.attempts++; }

    public OutboxEvent(UUID id, String aggregateType, String eventType, byte[] payload, Instant occurredOn, boolean published) {
        this.id = id;
        this.aggregateType = aggregateType;
        this.eventType = eventType;
        this.payload = payload;
        this.occurredOn = occurredOn;
        this.published = published;
        incrementAttempts();
    }

    public OutboxEvent() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }

    public void setOccurredOn(Instant occurredOn) {
        this.occurredOn = occurredOn;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
}
