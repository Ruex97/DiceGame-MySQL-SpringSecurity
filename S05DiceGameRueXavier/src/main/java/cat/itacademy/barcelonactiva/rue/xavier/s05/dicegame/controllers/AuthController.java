package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.controllers;

import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.auth.AuthenticationRequest;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.auth.AuthenticationResponse;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.auth.RegisterRequest;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> playerRegister (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> logIn (@RequestBody AuthenticationRequest request) {
        System.out.println("messi");
        return ResponseEntity.ok(service.authenticate(request));
    }


}
