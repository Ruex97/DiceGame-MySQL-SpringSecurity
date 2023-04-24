package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.services;

import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.dto.GameDto;
import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.dto.PlayerDto;

import java.util.List;

public interface PlayerService {

    GameDto rollTheDices(long id);
    PlayerDto updatePlayer(long id, PlayerDto playerDto);
    String deleteHistory(long id);
    List<PlayerDto> getPlayersWinrate();
    PlayerDto getPlayerById(Long id);
    Float getAverageWinrate();
    PlayerDto getWorstPlayer();
    PlayerDto getBestPlayer();




    /*
    GET /players/: retorna el llistat de tots  els jugadors/es del sistema amb el seu  percentatge mitjà d’èxits.
            GET /players/{id}/games: retorna el llistat de jugades per un jugador/a.
            GET /players/ranking: retorna el ranking mig de tots els jugadors/es del sistema. És a dir, el  percentatge mitjà d’èxits.
            GET /players/ranking/loser: retorna el jugador/a  amb pitjor percentatge d’èxit.
            GET /players/ranking/winner: retorna el  jugador amb pitjor percentatge d’èxit.*/


}
