package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class GameDto {

    private long id;

    private int result;

    private int dice1;

    private int dice2;

    private float winrate;

}
