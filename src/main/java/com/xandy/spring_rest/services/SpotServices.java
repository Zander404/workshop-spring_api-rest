package com.xandy.spring_rest.services;

import com.xandy.spring_rest.entities.Spot;
import com.xandy.spring_rest.exceptions.CodeUniqueViolation;
import com.xandy.spring_rest.exceptions.EntityNotFoundException;
import com.xandy.spring_rest.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.xandy.spring_rest.entities.enums.ParkingStatus.FREE;


@RequiredArgsConstructor
@Service
public class SpotServices {

    private final SpotRepository repository;


    @Transactional
    public Spot save(Spot spot) {
        try {
            return repository.save(spot);
        } catch (DataIntegrityViolationException e) {
            throw new CodeUniqueViolation(String.format("Code %s already exists", spot.getCode()));
        }
    }

    @Transactional(readOnly = true)
    public Spot searchByCode(String code) {
        return repository.findByCode(code).orElseThrow(() -> new EntityNotFoundException(String.format(" Parking Space with code %s is not find", code)));
    }


    @Transactional(readOnly = true)
    public Spot searchByFreeSpot() {
        return repository.findFirstByStatus(FREE).orElseThrow(
                () -> new EntityNotFoundException("Not has more free spots in the parkinglot")
        );
    }
}
