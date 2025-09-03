package com.example.empleos.presentation.dto.Authentication;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthLoginRequest(@JsonProperty("username") String username,
                               @JsonProperty("password") String password) {}