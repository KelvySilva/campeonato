package br.com.sg.campeonato.service;

import br.com.sg.campeonato.domain.TeamPosition;
import br.com.sg.campeonato.repository.TeamPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamPositionService {

    private TeamPositionRepository repository;

    @Autowired
    public TeamPositionService(TeamPositionRepository repository) {
        this.repository = repository;
    }

    public List<TeamPosition> findByChampIdOrderByScore(Long id) {
        return this.repository.findByIdOrderByTeamPositionDesc(id);
    }
}
