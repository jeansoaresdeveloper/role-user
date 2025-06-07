package com.user.role.user.dto;

import com.user.role.role.Role;
import jakarta.validation.constraints.NotEmpty;

public record UserDTO(

        @NotEmpty(message = "Name is required")
        String name,

        @NotEmpty(message = "Role is required")
        Role role,

        @NotEmpty(message = "Password is required")
        String password
) {
}
