package org.springframework.samples.petclinic.administrator;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.model.Person;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "administrator")
public class Administrator extends Person{

    @Column(name = "username", unique = true)
    @NotEmpty
    @Size(min = 4, max = 10)
    private String username = "No name";

    @Column(name = "password")
    @NotEmpty
    private String password = "No pass";

    @Column(name = "email", unique = true)
    @Email
    @NotEmpty
    private String email = "";

    @ManyToMany
    private List<Game> games;
}
