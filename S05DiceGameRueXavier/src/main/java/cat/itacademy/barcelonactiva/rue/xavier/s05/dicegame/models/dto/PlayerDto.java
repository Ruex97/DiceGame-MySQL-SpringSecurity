package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.dto;

import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.Game;

import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PlayerDto {

    private long id;
    private String name;
    private LocalDateTime dateOfRegister;
    private Game game;

    public String showWinrate() {
        return  "Player_id: " + id +
                ", name: '" + name + '\'' +
                ", winrate: " + game.getWinrate() + "%";
    }


}

