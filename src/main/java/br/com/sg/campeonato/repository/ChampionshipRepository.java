package br.com.sg.campeonato.repository;


import br.com.sg.campeonato.domain.Championship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
}
