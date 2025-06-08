package com.user.role.user;

import com.user.role.user.dto.UserDTO;
import com.user.role.user.dto.UserLoginDTO;
import com.user.role.utils.Password;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public User isValid(final UserLoginDTO user) {

        final User userFound = repository.findByName(user.name())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return Password.matches(user.password(), userFound.getPassword())
                ? userFound
                : null;
    }
}
