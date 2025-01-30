package com.xandy.spring_rest.services;

import com.xandy.spring_rest.entities.ClientSpot;
import com.xandy.spring_rest.repository.ClientSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClientSpotService {

    private final ClientSpotRepository repository;

    @Transactional
    public ClientSpot create(ClientSpot clientParkingSpot) {
        return repository.save(clientParkingSpot);
    }
}
