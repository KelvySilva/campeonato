package br.com.sg.campeonato.resource;

import br.com.sg.campeonato.domain.Team;
import br.com.sg.campeonato.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
public class TeamAPI {


    private TeamService service;

    @Autowired
    public TeamAPI(TeamService service) {
        this.service = service;
    }

    @GetMapping(path = "/protected/teams")
    public ResponseEntity listAll() {
        return ResponseEntity.ok(this.service.listAll());
    }

    @GetMapping(path = "/protected/team/{id}")
    public ResponseEntity findOne(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.findOne(id));
    }

    @PostMapping(path = "/admin/team")
    public ResponseEntity saveOne(@RequestBody Team team) {
        return ResponseEntity.ok(this.service.saveOne(team));
    }

    @PutMapping(path = "/admin/team/{id}")
    public ResponseEntity updateOne(@PathVariable Long id,@RequestBody Team team) {
        return ResponseEntity.ok(this.service.updateOne(id,team));
    }
}
