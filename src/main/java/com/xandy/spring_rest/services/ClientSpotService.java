package com.xandy.spring_rest.services;

import com.xandy.spring_rest.entities.ClientSpot;
import com.xandy.spring_rest.repository.ClientSpotRepository;
import com.xandy.spring_rest.repository.projection.ClientSpotProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClientSpotService {

    private final ClientSpotRepository repository;

    @Transactional
    public ClientSpot save(ClientSpot clientParkingSpot) {
        return repository.save(clientParkingSpot);
    }

    @Transactional
    public ClientSpot searchByTicket(String ticket) {
        return repository.findByTicketAndCheckOutDateIsNull(ticket).orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    @Transactional(readOnly = true)
    public long getTotalTimesCompletParking(String cpf) {
        return repository.countByClientCpfAndCheckOutDateIsNotNull(cpf);

    }

    @Transactional(readOnly = true)
    public Page<ClientSpotProjection> searchAllByClientCPF(String cpf, Pageable pageable) {
        return repository.findAllByClientCPF(cpf, pageable);
    }

    @Transactional(readOnly = true)
    public Page<ClientSpotProjection> searchAllByUserId(Long id, Pageable pageable) {
        return repository.findAllByClientUserId(id, pageable);
    }
}
