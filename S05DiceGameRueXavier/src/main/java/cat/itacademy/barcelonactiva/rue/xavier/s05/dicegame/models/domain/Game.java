package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table (name = "dice_game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long game_id;
    @Column
    private int result;
    @Column
    private int dice1;
    @Column
    private int dice2;

    @Lob
    List<Integer> history = new ArrayList<>();
    @Column
    private float winrate;
    @OneToOne(mappedBy = "game")
    @JsonIgnore
    private Player player;



    public int rollTheDices() {
        Random random = new Random();
        this.dice1 = random.nextInt(6) + 1;
        this.dice2 = random.nextInt(6) + 1;
        this.result = dice1 + dice2;
        this.history.add(result);
        this.winrate = updatedWinrate();
        return result;
    }

    public float updatedWinrate() {
        if(!history.isEmpty()){
            float victories = history.stream().filter(f -> f.equals(7)).count();
            float ratio =  victories /(float) history.size();
            winrate = ratio*100;
        } else {
            winrate = 0.0f;
        }

        return winrate;
    }

    public void resetHistory() {
        this.history.clear();
        this.winrate = updatedWinrate();
    }
}