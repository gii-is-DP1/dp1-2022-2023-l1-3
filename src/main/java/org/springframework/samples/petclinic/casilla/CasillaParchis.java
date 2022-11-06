package org.springframework.samples.petclinic.casilla;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CasillaParchis extends Casilla{

/*
    @NotNull
    @Value("0")
    private Integer nextPosition;
*/
@ManyToOne
@NotNull
@JoinColumn(name="casillaparchistype_id")
private CasillaParchisType casillaParchisType;

}
