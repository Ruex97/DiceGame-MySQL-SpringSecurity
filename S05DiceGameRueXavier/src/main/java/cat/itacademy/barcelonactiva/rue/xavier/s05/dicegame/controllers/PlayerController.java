package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.controllers;

import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.dto.GameDto;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.dto.PlayerDto;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.services.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;


    @PostMapping("/{id}/play")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GameDto> rollTheDices(@PathVariable(required = true) long id) {
        if(playerService.rollTheDices(id) != null) {
            return new ResponseEntity<>(playerService.rollTheDices(id), HttpStatus.CREATED);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Put Methods
    @PutMapping("/{id}/update")
    public ResponseEntity<PlayerDto> updateName(@PathVariable long id, @RequestBody PlayerDto playerDto) {
        PlayerDto updatedPlayer = playerService.updatePlayer(id, playerDto);

        if (updatedPlayer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
    }

    // Delete Methods
    @DeleteMapping("/{id}/reset")
    public ResponseEntity<String> resetHistory(@PathVariable long id) {
        String message = playerService.deleteHistory(id);
        return new ResponseEntity<>(message,  HttpStatus.OK);
    }

    // Get Methods
    @GetMapping("/getAll")
    public ResponseEntity<String> getPlayersStats() {
        List<PlayerDto> playersDto = playerService.getPlayersWinrate();
        StringBuilder responseBuilder = new StringBuilder();
        playersDto.forEach(p -> responseBuilder.append(p.showWinrate()).append("\n"));
        return new ResponseEntity<>(responseBuilder.toString(), HttpStatus.OK);
    }

    @GetMapping("/getResults/{id}")
    public ResponseEntity<String> getPlayersResultById(@PathVariable long id) {
        PlayerDto playerDto;
        String history;

        if(playerService.getPlayerById(id) != null) {
            playerDto = playerService.getPlayerById(id);
            history = playerDto.getGame().getHistory().toString();
            return new ResponseEntity<>("Player: " + playerDto.getName() + " | Results of each roll: " + history, HttpStatus.OK);
        } else{
            return new ResponseEntity<>("No Player with id: "+ id + " in our database", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ranking")
    public ResponseEntity<String> getAverageWinrate() {
        float average = playerService.getAverageWinrate();
        return new ResponseEntity<>("The average win rate of all our players is: " + average + "%", HttpStatus.OK);
    }

    @GetMapping("/ranking/loser")
    public ResponseEntity<PlayerDto> getLoser() {
        PlayerDto loser = playerService.getWorstPlayer();
        return new ResponseEntity<>(loser, HttpStatus.OK);
    }

    @GetMapping("/ranking/winner")
    public ResponseEntity<PlayerDto> getWinner() {
        PlayerDto winner = playerService.getBestPlayer();
        return new ResponseEntity<>(winner, HttpStatus.OK);
    }


}
