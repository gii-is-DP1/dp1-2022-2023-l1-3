package org.springframework.samples.xtreme.board;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import org.springframework.samples.xtreme.cells.ParchisCell;
import org.springframework.samples.xtreme.model.BaseEntity;
import org.springframework.samples.xtreme.pieces.ParchisPiece;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="parchisBoard")
public class ParchisBoard extends BaseEntity{

    String background;

    @Positive
    Double width;

    @Positive
    Double height;

    public ParchisBoard() {
        this.background="resources/img/boards/parchis/parchisBoard.svg";
        this.width=1151.5;
        this.height=1151.5;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "board", fetch = FetchType.EAGER)
    private List<ParchisPiece> pieces;
    
}
