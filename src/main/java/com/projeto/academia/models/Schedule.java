package com.projeto.academia.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Data
@Entity
@Table(name = "agenda")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data")
    @DateTimeFormat(pattern="dd-mm-yyyy")
    private String date;

    @Column(name = "entrada")
    @DateTimeFormat(pattern="00:00")
    private String entryTime;

    @Column(name = "saida")
    @DateTimeFormat(pattern="00:00")
    private String departureTime;

    @ManyToOne
    @JoinColumn(name = "id_aluno")
    private User user;

}
