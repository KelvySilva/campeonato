package br.com.sg.campeonato.repository;

import br.com.sg.campeonato.domain.TeamPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamPositionRepository extends JpaRepository<TeamPosition, Long> {

    @Query(value = "SELECT tp.* FROM championship c INNER JOIN championship_team_positions ctp ON c.id = ctp.championship_id inner JOIN team_position tp ON ctp.team_positions_id = tp.id WHERE c.id = 11 order by tp.score desc",nativeQuery = true)
    List<TeamPosition> findByIdOrderByTeamPositionDesc(Long id);
}
