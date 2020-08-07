package br.com.sg.campeonato.resource;

import br.com.sg.campeonato.domain.Championship;
import br.com.sg.campeonato.service.ChampionshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
public class ChampionshipAPI {

    private ChampionshipService service;

    @Autowired
    public ChampionshipAPI(ChampionshipService service) {
        this.service = service;
    }

    @GetMapping(path = "/protected/champs")
    public ResponseEntity listAll(){
        return ResponseEntity.ok(this.service.listAll());
    }

    @GetMapping(path = "/protected/champ/{id}")
    public ResponseEntity findOne(@PathVariable Long id){
        return ResponseEntity.ok(this.service.findOne(id));
    }

    @PostMapping(path = "/admin/champ")
    public ResponseEntity saveOne(@RequestBody Championship championship){
        return ResponseEntity.ok(this.service.saveOne(championship));
    }
    
    @PutMapping(path = "/admin/champ/{id}")
    public ResponseEntity updateOne(@PathVariable Long id, @RequestBody Championship championship) {
        return ResponseEntity.ok(this.service.updateOne(id, championship));
    }


}
