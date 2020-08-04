package br.com.sg.campeonato.repository;


import br.com.sg.campeonato.domain.Championship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
}
