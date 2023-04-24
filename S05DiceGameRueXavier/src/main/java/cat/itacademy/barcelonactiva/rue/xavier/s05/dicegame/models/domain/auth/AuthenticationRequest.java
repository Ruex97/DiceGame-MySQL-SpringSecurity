package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {

    private String name;
    private String password;
}
