package br.com.sg.campeonato.service;

import br.com.sg.campeonato.domain.Game;
import br.com.sg.campeonato.domain.TeamPosition;
import br.com.sg.campeonato.error.ResourceNotFoundException;
import br.com.sg.campeonato.repository.GameRepository;
import br.com.sg.campeonato.repository.TeamPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static br.com.sg.campeonato.contants.ValidationMessages.*;

@Service
public class GameService {

    private GameRepository repository;
    private TeamPositionRepository teamPositionRepository;

    @Autowired
    public GameService(GameRepository repository, TeamPositionRepository teamPositionRepository) {
        this.repository = repository;
        this.teamPositionRepository = teamPositionRepository;
    }

    public List<Game> listAll() {
        List<Game> all = this.repository.findAll();
        if (all.isEmpty()) {
            throw new ResourceNotFoundException(NENHUM_JOGO_CADASTRADO);
        }
        return all;
    }

    public Game findOne(Long id) {
        return this.repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(NENHUM_JOGO_ENCONTRADO_PARA_O_ID, id))
                );
    }

    @Transactional
    public Game saveOne(Game game) {
        return this.repository.save(game);
    }


    @Transactional
    protected void updateTeamScore(Long teamId, Long championshipId, BigDecimal additionalScore) {
        TeamPosition teamPosition = this.teamPositionRepository.findByIdAndChampionshipId(teamId, championshipId);
        teamPosition.setScore(teamPosition.getScore().add(additionalScore));
        this.teamPositionRepository.saveAndFlush(teamPosition);
    }

    @Transactional
    public Game updateOne(Long id, Game game) {
        if (!id.equals(game.getId())) {
            throw new ResourceNotFoundException(MENSAGEM_DE_PARAMETROS_DIVERGENTES);
        }
        Game gameStored = this.repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(NENHUM_JOGO_ENCONTRADO_PARA_O_ID, id))
                );
        if (gameStored.getFinished()) {
            throw new ResourceNotFoundException(ESSE_JOGO_JA_FOI_ENCERRADO);
        }
        game = resolveWinnerAndUpdateTeamScore(game);
        if (Objects.nonNull(game) && (gameStored.getTeamAGoals() != game.getTeamAGoals())) {
            gameStored.setTeamAGoals(game.getTeamAGoals());
        }
        if (Objects.nonNull(game) && (gameStored.getTeamBGoals() != game.getTeamBGoals())) {
            gameStored.setTeamBGoals(game.getTeamBGoals());
        }
        if (Objects.nonNull(game) && (gameStored.getFinished().equals(game.getFinished()))) {
            gameStored.setFinished(game.getFinished());
        }
        if (Objects.nonNull(game.getWinner()) && (game.getFinished().equals(true))) {
            gameStored.setWinner(game.getWinner());
            gameStored.setFinished(game.getFinished());
        }
        return this.repository.saveAndFlush(gameStored);
    }

    /**
     *
     * Recebe um jogo e verifica se já foi finalizado e os gols que foram feitos para atualizar a tabela
     * de pontos onde o time tem vinculo com o campeonato.
     * Otime vencedor recebe 3 pontos e em caso de empate cadas time recebe um ponto.
     *
     * @author Kelvy Silva
     * @param game
     * @return
     */
    private Game resolveWinnerAndUpdateTeamScore(Game game) {
        if (game.getTeamAGoals() > game.getTeamBGoals() && game.getFinished()) {
            game.setWinner(game.getTeamA());
            Long teamAId = game.getTeamA().getId();
            Long championshipId = game.getChampionship().getId();
            this.updateTeamScore(teamAId, championshipId, BigDecimal.valueOf(3));
        }else if(game.getTeamAGoals() < game.getTeamBGoals() && game.getFinished()){
            game.setWinner(game.getTeamB());
            Long teamBId = game.getTeamB().getId();
            Long championshipId = game.getChampionship().getId();
            this.updateTeamScore(teamBId, championshipId, BigDecimal.valueOf(3));

        }else if(game.getTeamAGoals() == game.getTeamBGoals() && game.getFinished()) {
            Long teamAId = game.getTeamA().getId();
            Long teamBId = game.getTeamB().getId();
            Long championshipId = game.getChampionship().getId();
            this.updateTeamScore(teamAId, championshipId, BigDecimal.valueOf(1));
            this.updateTeamScore(teamBId, championshipId, BigDecimal.valueOf(1));
        }
        return game;
    }


    public void deleteOne(Long id) {
        this.repository.deleteById(id);
    }
}
