package com.xandy.spring_rest.repository;

import com.xandy.spring_rest.entities.Spot;
import com.xandy.spring_rest.entities.enums.ParkingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpotRepository extends JpaRepository<Spot, Long> {

    Optional<Spot> findByCode(String code);

    Optional<Spot> findFirstByStatus(ParkingStatus status);
}
