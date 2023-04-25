package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.services;

import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.Game;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.Player;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.Role;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.auth.AuthenticationRequest;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.auth.AuthenticationResponse;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.auth.RegisterRequest;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.repository.GameRepository;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.repository.PlayerRepository;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final PlayerRepository repository;
    private final GameRepository gameRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
    // Game initialization to assign it to a player
    Game newGame = createGame();
    // Check for duplicates
    if (repository.existsByName(request.getName())) {
        throw new IllegalArgumentException("Name is already taken");
    }
    // Set name to "anonymous" if it is null or empty
    if (request.getName() == null || request.getName().isEmpty()) {
        request.setName("anonymous");
    }
    var user = Player.builder()
            .name(request.getName())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .game(newGame)
            .build();
    repository.save(user);
    var jwtToken = jwtService.generateToken(user);

    // Return appropriate message
    if (request.getName().equals("anonymous")) {
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .message("Name was set to anonymous")
                .build();
    } else {
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .message("Player registered successfully")
                .build();
    }
}


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getName(),
                        request.getPassword()
                )
        );
        var user = repository.findByName(request.getName())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .message("Login succesfully")
                .build();
    }

    public Game createGame() {
        Game game = new Game();
        gameRepository.save(game);
        return game;

    }
}
