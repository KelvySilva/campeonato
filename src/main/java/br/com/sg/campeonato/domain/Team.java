package br.com.sg.campeonato.domain;

import br.com.sg.campeonato.enumerator.STATE;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Team extends AbstractEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private STATE state;

}
