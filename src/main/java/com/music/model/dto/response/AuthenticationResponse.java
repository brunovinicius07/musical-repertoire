package com.music.model.dto.response;

import com.music.role.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private String token;

    private Long idUser;

    private String nameUser;

    private UserRole role;
}
