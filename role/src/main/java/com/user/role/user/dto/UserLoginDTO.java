package com.user.role.user.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginDTO(

        @NotEmpty(message = "Name is required")
        String name,

        @NotEmpty(message = "Password is required")
        String password
) {
}
