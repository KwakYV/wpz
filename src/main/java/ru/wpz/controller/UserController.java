package ru.wpz.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.wpz.config.JwtUtil;
import ru.wpz.entity.User;
import ru.wpz.service.UserService;

import java.util.Objects;

@RestController
@AllArgsConstructor
public class UserController {
    private static final ResponseEntity<Objects> UNAUTHORIZED = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    private final UserService userService;
    private final JwtUtil jwtUtil;


    @PostMapping("/login")
    public Mono<ResponseEntity> login(ServerWebExchange swe){
        return swe.getFormData().flatMap(credentials ->
                userService.findByUsername(credentials.getFirst("username"))
                        .cast(User.class)
                        .map(userDetails ->
                                        Objects.equals(credentials.getFirst("password"),
                                                userDetails.getPassword()
                                        )
                                                ? ResponseEntity.ok(jwtUtil.generateToken(userDetails))
                                                : UNAUTHORIZED
                                )
                        .defaultIfEmpty(UNAUTHORIZED)
        );
    }
}
