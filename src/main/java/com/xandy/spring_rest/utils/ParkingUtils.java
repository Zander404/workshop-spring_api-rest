package com.xandy.spring_rest.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingUtils {

    public static String generateTicket() {
        LocalDateTime date = LocalDateTime.now();
        String ticket = date.toString().substring(0, 19);
        return ticket.replace("-", "")
                .replace(":", "")
                .replace("T", "-");
    }


}
