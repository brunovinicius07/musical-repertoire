package com.music.model.dto.response;

import com.music.role.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long cdUser;

    private String nmUser;

    private String email;

    private UserRole role;
}
