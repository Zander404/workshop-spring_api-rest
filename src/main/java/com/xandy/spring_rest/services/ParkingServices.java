package com.xandy.spring_rest.services;

import com.xandy.spring_rest.entities.Parking;
import com.xandy.spring_rest.exceptions.CodeUniqueViolation;
import com.xandy.spring_rest.exceptions.EntityNotFoundException;
import com.xandy.spring_rest.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class ParkingServices {

    private final ParkingRepository repository;


    @Transactional
    public Parking save(Parking parking) {
        try {
            return repository.save(parking);
        } catch (DataIntegrityViolationException e) {
            throw new CodeUniqueViolation(String.format("Code %s already exists", parking.getCode()));
        }
    }

    @Transactional(readOnly = true)
    public Parking searchByCode(String code) {
        return repository.findByCode(code).orElseThrow(() -> new EntityNotFoundException(String.format(" Parking Space with code %s is not find", code)));
    }
}
