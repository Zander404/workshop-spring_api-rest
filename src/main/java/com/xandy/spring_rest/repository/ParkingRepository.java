package com.xandy.spring_rest.repository;

import com.xandy.spring_rest.entities.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

    Optional<Parking> findyByCode(String code);
}
