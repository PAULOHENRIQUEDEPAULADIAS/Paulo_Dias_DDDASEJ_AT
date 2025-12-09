package com.example.PetFriends_Transporte.event;

import com.example.PetFriends_Almoxarifado.model.OutboxEvent;
import com.example.PetFriends_Almoxarifado.repository.OutboxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class OutboxPublisher {

    private final OutboxRepository outboxRepo;
    private final KafkaTemplate<String, String> kafka;
    private static final Logger LOG = LoggerFactory.getLogger(OutboxPublisher.class);

    public OutboxPublisher(OutboxRepository outboxRepo, KafkaTemplate<String, String> kafka) {
        this.outboxRepo = outboxRepo;
        this.kafka = kafka;
    }

    @Scheduled(fixedDelayString = "${outbox.publish.interval:1000}")
    public void publishPending() {

        List<OutboxEvent> pending =
                outboxRepo.findTop100ByPublishedFalseOrderByOccurredOnAsc();

        for (OutboxEvent out : pending) {
            String json = new String(out.getPayload(), StandardCharsets.UTF_8);

            CompletableFuture<SendResult<String, String>> fut =
                    kafka.send(out.getEventType(), json);

            fut.whenComplete((result, ex) -> {

                if (ex == null) {
                    out.setPublished(true);
                    out.setOccurredOn(Instant.now());
                    outboxRepo.save(out);

                } else {
                    LOG.error("Falha ao publicar evento {}: {}", out.getId(), ex.getMessage());
                }
            });
        }
    }
}
