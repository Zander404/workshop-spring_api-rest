package com.xandy.spring_rest.dto;

import lombok.*;

@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class UserPasswordDTO {
    private String password;
    private String newPassword;
    private String confirmPassword;
}
