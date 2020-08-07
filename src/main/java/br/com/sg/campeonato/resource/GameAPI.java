package br.com.sg.campeonato.resource;

import br.com.sg.campeonato.domain.Game;
import br.com.sg.campeonato.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
public class GameAPI {

    private GameService service;

    @Autowired
    public GameAPI(GameService service) {
        this.service = service;
    }

    @GetMapping(path = "/protected/games")
    public ResponseEntity listAll(){
        return ResponseEntity.ok(this.service.listAll());
    }

    @GetMapping(path = "/protected/game/{id}")
    public ResponseEntity findOne(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.findOne(id));
    }

    @PostMapping(path = "/admin/game")
    public ResponseEntity saveOne(@RequestBody Game game) {
        return ResponseEntity.ok(this.service.saveOne(game));
    }

    @PutMapping(path = "/admin/game/{id}")
    public ResponseEntity updateOne(@PathVariable Long id, @RequestBody Game game) {
        return ResponseEntity.ok(this.service.updateOne(id, game));
    }

    @DeleteMapping(path = "/admin/game/{id}")
    public ResponseEntity deleteOne(@PathVariable Long id) {
        this.service.deleteOne(id);
        return ResponseEntity.accepted().build();
    }
}
