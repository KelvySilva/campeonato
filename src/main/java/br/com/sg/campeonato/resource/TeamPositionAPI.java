package br.com.sg.campeonato.resource;

import br.com.sg.campeonato.service.TeamPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class TeamPositionAPI {

    private TeamPositionService service;

    @Autowired
    public TeamPositionAPI(TeamPositionService service) {
        this.service = service;
    }

    @GetMapping(path = "/protected/teams/champ/{id}/ranking")
    public ResponseEntity findRankByChampId(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.findByChampIdOrderByScore(id));
    }

    @GetMapping(path = "/protected/team/{teamId}/champ/{id}")
    public ResponseEntity findTeamScoreOnChamp(@PathVariable("teamId") Long teamId,@PathVariable("id") Long id) {
        System.out.println(teamId);
        System.out.println(id);
        return ResponseEntity.ok(this.service.findTeamScoreOnChamp(teamId, id));
    }
}
