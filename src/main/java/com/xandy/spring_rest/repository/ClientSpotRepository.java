package com.xandy.spring_rest.repository;

import com.xandy.spring_rest.entities.ClientSpot;
import com.xandy.spring_rest.repository.projection.ClientSpotProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientSpotRepository extends JpaRepository<ClientSpot, Long> {

    Optional<ClientSpot> findByTicketAndCheckOutDateIsNull(String ticket);

    long countByClientCpfAndCheckOutDateIsNotNull(String cpf);

    Page<ClientSpotProjection> findAllByClientCPF(String cpf, Pageable pageable);

    Page<ClientSpotProjection> findAllByClientUserId(Long id, Pageable pageable);
}
