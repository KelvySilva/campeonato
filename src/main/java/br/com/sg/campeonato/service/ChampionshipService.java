package br.com.sg.campeonato.service;

import br.com.sg.campeonato.domain.Championship;
import br.com.sg.campeonato.error.ResourceNotFoundException;
import br.com.sg.campeonato.repository.ChampionshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static br.com.sg.campeonato.contants.ValidationMessages.*;

@Service
public class ChampionshipService {

    private ChampionshipRepository repository;

    @Autowired
    public ChampionshipService(ChampionshipRepository repository) {
        this.repository = repository;
    }

    public List<Championship> listAll() {
        List<Championship> all = this.repository.findAll();
        if (all.isEmpty()) throw new ResourceNotFoundException(NENHUM_CAMPEONATO_CADASTRADO);
        return all;
    }

    public Championship findOne(Long id) {
        return this.repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(NENHUM_CAMPEONATO_PARA_O_ID, id))
                );

    }

    @Transactional
    public Championship saveOne(Championship championship) {

        return this.repository.save(championship);
    }

    @Transactional
    public Championship updateOne(Long id, Championship championship) {
        Championship existentChampionship = this.repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(NENHUM_CAMPEONATO_PARA_O_ID, id))
                );
        if (!championship.getId().equals(existentChampionship.getId())) {
            throw new ResourceNotFoundException(MENSAGEM_DE_PARAMETROS_DIVERGENTES);
        }
        if(Objects.nonNull(championship) && !(existentChampionship.getName().equals(championship.getName()))) {
            existentChampionship.setName(championship.getName());
        }
        if(Objects.nonNull(championship) && !(existentChampionship.getInitialDate().isEqual(championship.getInitialDate()))) {
            existentChampionship.setInitialDate(championship.getInitialDate());
        }
        if(Objects.nonNull(championship) && !(existentChampionship.getFinalDate().isEqual(championship.getFinalDate()))) {
            existentChampionship.setFinalDate(championship.getFinalDate());
        }
        if(Objects.nonNull(championship) && !(existentChampionship.getTeamPositions().containsAll(championship.getTeamPositions()))) {

            existentChampionship.setTeamPositions(championship.getTeamPositions());
        }
        
        return this.repository.saveAndFlush(existentChampionship);
    }

}
