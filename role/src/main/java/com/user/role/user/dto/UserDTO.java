package com.user.role.user.dto;

import com.user.role.role.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserDTO(

        @NotEmpty(message = "Name is required")
        String name,

        @NotNull(message = "Role is required")
        Role role,

        @NotEmpty(message = "Password is required")
        String password
) {
}
