package com.example.PetFriends_Almoxarifado.service;

import com.example.PetFriends_Almoxarifado.DTO.PedidoCriadoDTO;
import com.example.PetFriends_Almoxarifado.event.EstoqueItem;
import com.example.PetFriends_Almoxarifado.model.OutboxEvent;
import com.example.PetFriends_Almoxarifado.model.DomainEvent;
import com.example.PetFriends_Almoxarifado.repository.EstoqueRepository;
import com.example.PetFriends_Almoxarifado.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

@Service
public class AlmoxarifadoApplicationService {
    private final EstoqueRepository estoqueRepo;
    private final OutboxRepository outboxRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AlmoxarifadoApplicationService(EstoqueRepository estoqueRepo, OutboxRepository outboxRepo) {
        this.estoqueRepo = estoqueRepo;
        this.outboxRepo = outboxRepo;
    }

    @Transactional
    public void reservarEstoque(UUID productId, UUID pedidoId, int quantidade) throws JsonProcessingException {
        EstoqueItem item = estoqueRepo.findByProductId(productId)
                .orElseGet(() -> new EstoqueItem(UUID.randomUUID(), productId, 0));
        item.reservar(pedidoId, quantidade);
        estoqueRepo.save(item);

        List<DomainEvent> events = item.drainEvents();
        for (DomainEvent ev : events) {
            byte[] payload = objectMapper.writeValueAsBytes(ev);
            OutboxEvent out = new OutboxEvent(UUID.randomUUID(), "EstoqueItem", ev.eventType(), payload, ev.occurredOn(), false);
            outboxRepo.save(out);
        }
        // commit aqui
    }

    @KafkaListener(topics = "pedidos-criados", groupId = "almoxarifado-group")
    @Transactional
    public void onPedidoCriado(String messageJson) {
        try {
            PedidoCriadoDTO dto = objectMapper.readValue(messageJson, PedidoCriadoDTO.class);
            reservarEstoque(dto.getProductId(), dto.getPedidoId(), dto.getQuantidade());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar evento de pedido criado", e);
        }
    }
}
