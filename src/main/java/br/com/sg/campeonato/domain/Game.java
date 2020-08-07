package br.com.sg.campeonato.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Game extends AbstractEntity {

    @OneToOne
    private Championship championship;

    @OneToOne
    private Team teamA;

    private Integer teamAGoals = 0;

    @OneToOne
    private Team teamB;

    private Integer teamBGoals = 0;

    @OneToOne(optional = true)
    private Team winner;

    @Column(columnDefinition="BOOLEAN DEFAULT FALSE")
    private Boolean finished;

    @Override
    public String toString() {
        return "Game{" +
                "\n championship=" + championship +
                "\n, teamA=" + teamA +
                "\n, teamAGoals=" + teamAGoals +
                "\n, teamB=" + teamB +
                "\n, teamBGoals=" + teamBGoals +
                "\n, winner=" + winner +
                "\n, finished=" + finished +
                "\n"+'}';
    }
}
