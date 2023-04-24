package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.repository;

import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
