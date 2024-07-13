package com.ideu.demo.server08.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "city")
public class City {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "pid")
    private Long pid;
    @Column(name = "cityname")
    private String cityName;
    @Column(name = "type")
    private Integer type;
    @Transient
    private List<City> children;



}
