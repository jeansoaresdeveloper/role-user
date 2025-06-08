package com.user.role.user;

import com.user.role.role.Role;
import com.user.role.user.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
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

    public static User fromDTOWithId(final Long id, final UserDTO userDTO, final String password) {
        return new User(null, userDTO.name(), userDTO.role(), password);
    }

}
