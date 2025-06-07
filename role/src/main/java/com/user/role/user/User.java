package com.user.role.user;

import com.user.role.role.Role;
import com.user.role.user.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Entity
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @NotNull
    private String password;

    public static User fromDTO(final UserDTO userDTO, final String password) {
        return new User(null, userDTO.name(), userDTO.role(), password);
    }

}
