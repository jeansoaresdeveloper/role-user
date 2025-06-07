package com.user.role.user;

import com.user.role.user.dto.UserDTO;
import com.user.role.user.dto.UserLoginDTO;
import com.user.role.utils.Password;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> find() {
        return repository.findAll();
    }

    public User save(final UserDTO user) {
        final String password = Password.encode(user.password());
        return repository.save(User.fromDTO(user, password));
    }

    public void auth(final UserLoginDTO user) {

    }
}
