package br.com.sg.campeonato.service;

import br.com.sg.campeonato.domain.Team;
import br.com.sg.campeonato.error.ResourceNotFoundException;
import br.com.sg.campeonato.repository.TeamRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class TeamService {

    private TeamRepository repository;

    @Autowired
    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public List<Team> listAll() {
        List<Team> all = this.repository.findAll();

        if (all.isEmpty()) {
            throw  new ResourceNotFoundException("Nenhum time cadastrado!");
        }
        return all;
    }

    public Team findOne(Long id) {
        return this.repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("Time com ID: %s não encotrado", id))
                );
    }

    @Transactional
    public Team saveOne(Team team) {
        return this.repository.save(team);
    }

    @Transactional
    public Team updateOne(Long id, Team team) {
        Team update = this.repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("Time não encontrado para o ID: %s", id))
                );
        if (Objects.nonNull(team) && Objects.nonNull(team.getId()) && !(update.getName().equals(team.getName())))  {
            update.setName(team.getName());
        }
        if (Objects.nonNull(team) && Objects.nonNull(team.getId()) && !(update.getName().equals(team.getState())))  {
            update.setState(team.getState());
        }

        return this.repository.saveAndFlush(update);
    }
}
