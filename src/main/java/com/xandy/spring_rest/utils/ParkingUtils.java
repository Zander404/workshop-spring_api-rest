package com.xandy.spring_rest.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingUtils {

    private static final double FIRST_15_MINUTES = 5.00;
    private static final double FIRST_60_MINUTES = 9.25;
    private static final double ADICTIONAL_15_MINUTES = 1.75;
    private static final double DISCOUNT_PERCENTUAL = 0.30;

    public static String generateTicket() {
        LocalDateTime date = LocalDateTime.now();
        String ticket = date.toString().substring(0, 19);
        return ticket.replace("-", "")
                .replace(":", "")
                .replace("T", "-");
    }


    public static BigDecimal calculatePrice(LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        long minutes = checkInDate.until(checkOutDate, ChronoUnit.MINUTES);
        double total = 0.0;

        if (minutes <= 15) {
            total = FIRST_15_MINUTES;
        } else if (minutes <= 60) {
            total = FIRST_60_MINUTES;
        } else {
            long addicionalMinutes = minutes - 60;
            Double totalParts = ((double) addicionalMinutes / 15);
            if (totalParts > totalParts.intValue()) { // 4.66 > 4
                total += FIRST_60_MINUTES + (FIRST_15_MINUTES * (totalParts.intValue() + 1));
            } else { // 4.0
                total += FIRST_60_MINUTES + (ADICTIONAL_15_MINUTES * totalParts.intValue());
            }
        }

        return new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal calculateDiscount(BigDecimal cost, long totalTimes) {
        BigDecimal desconto = ((totalTimes > 0) && (totalTimes % 10 == 0))
                ? cost.multiply(new BigDecimal(DISCOUNT_PERCENTUAL))
                : new BigDecimal(0);
        return desconto.setScale(2, RoundingMode.HALF_EVEN);
    }



}
