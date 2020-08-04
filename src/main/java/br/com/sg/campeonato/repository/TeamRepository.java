package br.com.sg.campeonato.repository;


import br.com.sg.campeonato.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
