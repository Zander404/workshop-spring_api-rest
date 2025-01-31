package com.xandy.spring_rest.services;

import com.xandy.spring_rest.entities.Client;
import com.xandy.spring_rest.entities.ClientSpot;
import com.xandy.spring_rest.entities.Spot;
import com.xandy.spring_rest.entities.enums.ParkingStatus;
import com.xandy.spring_rest.utils.ParkingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ParkingService {
    private final ClientSpotService clientParkingSpotService;
    private final ClientService clientService;
    private final SpotServices spotService;
    private final ClientSpotService clientSpotService;


    @Transactional
    public ClientSpot checkIn(ClientSpot clientParkingSpot) {
        Client client = clientService.findByCpf(clientParkingSpot.getClient().getCpf());
        clientParkingSpot.setClient(client);

        Spot spot = spotService.searchByFreeSpot();
        spot.setStatus(ParkingStatus.OCCUPIED);
        clientParkingSpot.setSpot(spot);

        clientParkingSpot.setCheckInDate(LocalDateTime.now());
        clientParkingSpot.setTicket(ParkingUtils.generateTicket());

        return clientParkingSpotService.save(clientParkingSpot);
    }


    @Transactional
    public ClientSpot checkOut(String ticket) {
        ClientSpot clientParkingSpot = clientSpotService.searchByTicket(ticket);

        LocalDateTime checkOutDate = LocalDateTime.now();
        BigDecimal price = ParkingUtils.calculatePrice(clientParkingSpot.getCheckInDate(), checkOutDate);
        clientParkingSpot.setPrice(price);

        long totalTimes = clientSpotService.getTotalTimesCompletParking(clientParkingSpot.getClient().getCpf());

        BigDecimal discount = ParkingUtils.calculateDiscount(price, totalTimes);
        clientParkingSpot.setDiscount(discount);

        clientParkingSpot.setCheckOutDate(checkOutDate);
        clientParkingSpot.getSpot().setStatus(ParkingStatus.FREE);
        return clientParkingSpotService.save(clientParkingSpot);
    }
}
