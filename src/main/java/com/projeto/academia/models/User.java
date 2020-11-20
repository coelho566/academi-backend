package com.projeto.academia.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name = "aluno")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matricula")
    private Integer id;

    @Column(name = "nome")
    private String name;

    @Column(name = "email")
    private String email;

    @DateTimeFormat(pattern="dd-mm-yyyy")
    @Column(name = "nascimento")
    private String dateOfBirth;

    @Column(name = "senha")
    private String password;

    @Column(name = "permicao")
    private String roles;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Schedule> schedules;

    @JsonIgnore
    public List<String> getRolesList() {

        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
