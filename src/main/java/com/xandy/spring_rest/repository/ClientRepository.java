package com.xandy.spring_rest.repository;

import com.xandy.spring_rest.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
