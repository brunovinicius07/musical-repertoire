package com.music.controllers;

import com.music.model.dto.request.UpdateUserRequest;
import com.music.model.dto.response.UserResponseDto;
import com.music.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController()
@RequestMapping(value = "v1/music/user")
public class UserController {

    private final UserService userService;

    @PutMapping("put/{idUser}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long idUser, @RequestBody @Valid UpdateUserRequest updateUserRequest){
        var userResponse = userService.updateUser(idUser, updateUserRequest);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("delete/{idUser}")
    public ResponseEntity<String> deleteUser(@PathVariable Long idUser){
        String message = userService.deleteUser(idUser);
        return ResponseEntity.ok(message);
    }

}
