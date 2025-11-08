package com.music.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    private String nameUser;

    private String email;

    @JsonProperty("isChangePassword")
    private boolean isChangePassword;

    private String currentPassword;

    private String newPassword;

    private String confirmNewPassword;
}
