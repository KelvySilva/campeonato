package br.com.sg.campeonato.service;

import br.com.sg.campeonato.domain.Team;
import br.com.sg.campeonato.error.ResourceNotFoundException;
import br.com.sg.campeonato.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static br.com.sg.campeonato.contants.ValidationMessages.NENHUM_TIME_CADASTRADO;
import static br.com.sg.campeonato.contants.ValidationMessages.TIME_COM_ID_S_NÃO_ENCOTRADO;

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
            throw  new ResourceNotFoundException(NENHUM_TIME_CADASTRADO);
        }
        return all;
    }

    public Team findOne(Long id) {
        return this.repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(TIME_COM_ID_S_NÃO_ENCOTRADO, id))
                );
    }

    @Transactional
    public Team saveOne(Team team) {
        return this.repository.save(team);
    }

    @Transactional
    public Team updateOne(Long id, Team team) {
        Team existentTeam = this.repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(TIME_COM_ID_S_NÃO_ENCOTRADO, id))
                );
        if (Objects.nonNull(team) && Objects.nonNull(team.getId()) && !(existentTeam.getName().equals(team.getName())))  {
            existentTeam.setName(team.getName());
        }
        if (Objects.nonNull(team) && Objects.nonNull(team.getId()) && !(existentTeam.getName().equals(team.getState())))  {
            existentTeam.setState(team.getState());
        }

        return this.repository.saveAndFlush(existentTeam);
    }
}
