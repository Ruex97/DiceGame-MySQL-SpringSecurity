package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    private String message;
}
