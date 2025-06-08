package com.user.role.user;

import com.user.role.token.TokenService;
import com.user.role.user.dto.UserDTO;
import com.user.role.user.dto.UserLoginDTO;
import com.user.role.user.dto.UserTokenDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final TokenService tokenService;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(service.find());
    }

    @PostMapping("/auth")
    public ResponseEntity<UserTokenDTO> auth(@RequestBody @Valid UserLoginDTO user) {
        final User userIfIsValid = service.isValid(user);

        if (Objects.isNull(userIfIsValid)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final UserTokenDTO token = new UserTokenDTO(tokenService.generate(userIfIsValid.getName(), userIfIsValid.getRole()));
        return ResponseEntity.ok(token);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid UserDTO user) {

        final User saved = service.save(user);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

}
