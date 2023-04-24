package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.services;

import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.auth.AuthenticationRequest;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.auth.AuthenticationResponse;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.auth.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
