package com.alex.serv.entities;

import javax.persistence.*;

@Entity
@Table(name = "stops")
public class Stops {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "name")
    private String name;

    public Stops() {
    }

    public Stops(String name) {
        this.name = name;
    }

    public String getInfo() {
        return name + "\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
