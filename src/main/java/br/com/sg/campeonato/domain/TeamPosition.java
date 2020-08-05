package br.com.sg.campeonato.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class TeamPosition extends AbstractEntity {

    @OneToOne(fetch = FetchType.EAGER)
    private Team team;

    private BigDecimal score = BigDecimal.ZERO;
}
