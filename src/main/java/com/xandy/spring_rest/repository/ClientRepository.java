package com.xandy.spring_rest.repository;

import com.xandy.spring_rest.entities.Client;
import com.xandy.spring_rest.repository.projection.ClientProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c")
    Page<ClientProjection> findAllPageable(Pageable pageable);
}
