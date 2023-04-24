package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.services;

import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.Game;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.Player;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.dto.GameDto;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.dto.PlayerDto;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.repository.GameRepository;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;


    @Override
    public GameDto rollTheDices(long id) {
        Player player1;
        Game game, updatedGame;
        GameDto gameDto;
        Optional<Player> player = playerRepository.findById(id);
        if(player.isPresent()) {
            player1 = player.get();
            game = player1.getGame();
            game.rollTheDices();
            updatedGame = gameRepository.save(game);
            gameDto = gameToDto(updatedGame);

        } else {
            gameDto = null;
        }
        return gameDto;
    }

    @Override
    public PlayerDto updatePlayer(long id, PlayerDto playerDto) {
        Optional<Player> player = playerRepository.findById(id);
        PlayerDto responsePlayer;
        Player myPlayer, savedPlayer;

        if(player.isPresent()) {
            myPlayer = player.get();
            myPlayer.setName(playerDto.getName());
            savedPlayer = playerRepository.save(myPlayer);
            responsePlayer = playerToDto(savedPlayer);
        } else{
            responsePlayer = null;
        }

        return responsePlayer;
    }

    @Override
    public String deleteHistory(long id) {
        Optional<Player> player = playerRepository.findById(id);
        if(player.isPresent()) {
            Player myPlayer = player.get();
            Game game = myPlayer.getGame();
            game.resetHistory();
            gameRepository.save(game);
            return "history reset";
        }

        return "We don't have any player with id " + id;
    }

    @Override
    public List<PlayerDto> getPlayersWinrate() {
        return playerRepository.findAll().stream().map(this::playerToDto).collect(Collectors.toList());

    }

    @Override
    public PlayerDto getPlayerById(Long id){
        Optional<Player> player = playerRepository.findById(id);
        PlayerDto responsePlayer;

        if(player.isPresent()) {
            Player myPlayer = player.get();
            responsePlayer = playerToDto(myPlayer);
        } else{
            responsePlayer = null;
        }
        return responsePlayer;
    }

    @Override
    public Float getAverageWinrate() {
        List<Player> players = playerRepository.findAll();
        double averageWinrate = players.stream()
                                        .mapToDouble(p -> p.getGame().getWinrate())
                                        .average()
                                        .orElse(0.0);


        return (float) averageWinrate;
    }

    @Override
    public PlayerDto getWorstPlayer() {
        List<PlayerDto> players = playerRepository.findAll().stream().map(this::playerToDto).toList();
        PlayerDto loser = players.stream()
                                 .min(Comparator.comparingDouble(p -> p.getGame().getWinrate()))
                                 .orElse(null);

        return loser;
    }

    @Override
    public PlayerDto getBestPlayer() {
        List<PlayerDto> players = playerRepository.findAll().stream().map(this::playerToDto).toList();
        PlayerDto winner = players.stream()
                .max(Comparator.comparingDouble(p -> p.getGame().getWinrate()))
                .orElse(null);

        return winner;
    }

    public PlayerDto playerToDto(Player player){

        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(player.getPlayer_id());
        playerDto.setName(player.getName());
        playerDto.setDateOfRegister(player.getDateOfRegister());
        playerDto.setGame(player.getGame());

        return playerDto;
    }

    public GameDto gameToDto(Game game){

        GameDto gameDto = new GameDto();
        gameDto.setId(game.getGame_id());
        gameDto.setDice1(game.getDice1());
        gameDto.setDice2(game.getDice2());
        gameDto.setResult(game.getResult());
        gameDto.setWinrate(game.getWinrate());

        return gameDto;
    }


    public boolean checkNames(PlayerDto playerDto) {
        boolean taken = false;
        if (playerDto == null) {
            return taken;
        } else if (playerRepository.findByName(playerDto.getName()) != null) {
            taken = true;
        }
        return taken;
    }

}
