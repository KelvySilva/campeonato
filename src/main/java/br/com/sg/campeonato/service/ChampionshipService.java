package br.com.sg.campeonato.service;

import br.com.sg.campeonato.domain.Championship;
import br.com.sg.campeonato.domain.TeamPosition;
import br.com.sg.campeonato.error.ResourceNotFoundException;
import br.com.sg.campeonato.repository.ChampionshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ChampionshipService {

    private ChampionshipRepository repository;

    @Autowired
    public ChampionshipService(ChampionshipRepository repository) {
        this.repository = repository;
    }

    public List<Championship> listAll() {
        List<Championship> all = this.repository.findAll();
        if (all.isEmpty()) throw new ResourceNotFoundException("Nenhum Campionato cadastrado");
        return all;
    }

    public Championship findOne(Long id) {
        return this.repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("Nenhum campionato para o ID: %s", id))
                );

    }

    @Transactional
    public Championship saveOne(Championship championship) {

        return this.repository.save(championship);
    }

    @Transactional
    public Championship updateOne(Long id, Championship championship) {
        Championship update = this.repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("Nenhum campionato para o ID: %s", id))
                );
        if (!championship.getId().equals(update.getId())) {
            throw new ResourceNotFoundException("O id do corpo do objeto é diferente do passado na url!");
        }

        if(Objects.nonNull(championship) && !(update.getName().equals(championship.getName()))) {
            update.setName(championship.getName());
        }

        if(Objects.nonNull(championship) && !(update.getInitialDate().isEqual(championship.getInitialDate()))) {
            update.setInitialDate(championship.getInitialDate());
        }
        if(Objects.nonNull(championship) && !(update.getFinalDate().isEqual(championship.getFinalDate()))) {
            update.setFinalDate(championship.getFinalDate());
        }
        if(Objects.nonNull(championship) && !(update.getTeamPositions().containsAll(championship.getTeamPositions()))) {
//            championship.getTeamPositions().stream()
//                    .forEach(teamPosition -> {
//                        if (!(update.getTeamPositions().contains(teamPosition))) {
//                            update.getTeamPositions().add(teamPosition);
//                        }
//                    });
            update.setTeamPositions(championship.getTeamPositions());
        }
        
        return this.repository.saveAndFlush(update);
    }

}
