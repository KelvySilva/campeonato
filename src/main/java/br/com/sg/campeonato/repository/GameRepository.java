package br.com.sg.campeonato.repository;


import br.com.sg.campeonato.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
