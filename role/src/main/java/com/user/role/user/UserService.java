package com.user.role.user;

import com.user.role.user.dto.UserDTO;
import com.user.role.user.dto.UserLoginDTO;
import com.user.role.utils.Password;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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

        final User userFound = findByName(user.name());

        return Password.matches(user.password(), userFound.getPassword())
                ? userFound
                : null;
    }

    public User findByName(final String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public void update(final String name, final UserDTO user) {
        final User userFound = findByName(name);
        update(user, userFound);
    }

    public void update(final Long id, final UserDTO user) {
        final User userFound = findById(id);
        update(user, userFound);
    }

    private void update(UserDTO user, User userFound) {
        final String password = Password.encode(user.password());
        final User userToSave = User.fromDTOWithId(userFound.getId(), user, password);
        repository.save(userToSave);
    }

    public void delete(final Long id) {
        final User userFound = findById(id);
        repository.delete(userFound);
    }

    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
    }
}
