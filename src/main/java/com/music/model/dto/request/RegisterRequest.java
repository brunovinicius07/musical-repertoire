package com.music.model.dto.request;

import com.music.role.UserRole;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest{

    private String nameUser;

    private String email;

    private String password;

    private UserRole role;


}
