package com.xandy.spring_rest.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "client_spot")
@EntityListeners(AuditingEntityListener.class)
public class ClientSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_number", nullable = false, length = 15)
    private String ticket;

    @Column(name = "plate", nullable = false, length = 8)
    private String plate;

    @Column(name = "brand", nullable = false, length = 45)
    private String brand;

    @Column(name = "model", nullable = false, length = 45)
    private String model;

    @Column(name = "color", nullable = false, length = 45)
    private String color;

    @Column(name = "checkInDate", nullable = false, length = 45)
    private LocalDateTime checkInDate;

    @Column(name = "checkOutDate")
    private LocalDateTime checkOutDate;

    @Column(name = "price", columnDefinition = "decimal(7,2)")
    private BigDecimal price;

    @Column(name = "discount", columnDefinition = "decimal(7,2)")
    private BigDecimal discount;


    @ManyToOne()
    @JoinColumn(name="id_client", nullable = false)
    private Client client;

    @ManyToOne()
    @JoinColumn(name="id_parking_spot", nullable = false)
    private Spot spot;
    



    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String updatedBy;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClientSpot that = (ClientSpot) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
