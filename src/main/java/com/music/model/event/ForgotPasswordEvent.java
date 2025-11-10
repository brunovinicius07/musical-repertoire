package com.music.model.event;

public record ForgotPasswordEvent(String email, String nameUser, String token) {}
