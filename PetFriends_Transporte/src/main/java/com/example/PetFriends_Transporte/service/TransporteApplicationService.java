package com.example.PetFriends_Transporte.service;

import com.example.PetFriends_Transporte.DTO.PedidoDespachadoDTO;
import com.example.PetFriends_Transporte.model.CodigoRastreamento;
import com.example.PetFriends_Transporte.model.EnderecoEntrega;
import com.example.PetFriends_Transporte.model.Entrega;
import com.example.PetFriends_Transporte.model.DomainEvent;
import com.example.PetFriends_Transporte.repository.EntregaRepository;
import com.example.PetFriends_Transporte.repository.OutboxRepository;
import com.example.PetFriends_Transporte.model.OutboxEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransporteApplicationService {

    private final EntregaRepository entregaRepository;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TransporteApplicationService(EntregaRepository entregaRepository, OutboxRepository outboxRepository) {
        this.entregaRepository = entregaRepository;
        this.outboxRepository = outboxRepository;
    }

    @KafkaListener(topics = "PedidoDespachado", groupId = "petfriends-transporte")
    @Transactional
    public void onPedidoDespachado(String messageJson) throws JsonProcessingException {
        PedidoDespachadoDTO dto = objectMapper.readValue(messageJson, PedidoDespachadoDTO.class); // ALTERAÇÃO: Usar Jackson.

        if (entregaRepository.findByPedidoId(dto.getPedidoId()).isPresent()) {
            return;
        }

        EnderecoEntrega endereco = new EnderecoEntrega(
                dto.getEnderecoEntrega().getRua(),
                "",
                dto.getEnderecoEntrega().getCidade(),
                ""
        );

        Entrega entrega = new Entrega(UUID.randomUUID(), dto.getPedidoId(), endereco);
        entrega.despachar(new CodigoRastreamento(dto.getRastreamento()));
        entregaRepository.save(entrega);

        List<DomainEvent> events = entrega.drainEvents();
        for (DomainEvent ev : events) {
            byte[] payload = objectMapper.writeValueAsBytes(ev);
            OutboxEvent out = new OutboxEvent(UUID.randomUUID(), "Entrega", ev.eventType(), payload, ev.occurredOn(), false);
            outboxRepository.save(out);
        }
    }
}