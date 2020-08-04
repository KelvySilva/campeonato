package br.com.sg.campeonato.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Championship extends AbstractEntity {

    private LocalDate initialDate;

    private LocalDate finalDate;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TeamPosition> teamPositions;



}
