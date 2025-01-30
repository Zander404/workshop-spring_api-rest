package com.xandy.spring_rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingResponseDTO {

    private String plate;

    private String brand;

    private String model;
    private String color;
    private String clientCpf;

    private String ticket;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime checkInDate;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime checkOutDate;
    private String id_parking_spot;
    private BigDecimal price;
    private BigDecimal discount;
}
