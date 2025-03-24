package com.sparta.socialapp.domain.user.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    private String email;
    private String username;
    private String password;
}
