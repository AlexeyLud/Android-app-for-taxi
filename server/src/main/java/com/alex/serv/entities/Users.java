package com.alex.serv.entities;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    private String start_stop;
    private String end_stop;
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Trip trip;

    public Users(){}

    public Users(String start_stop, String end_stop){
        this.start_stop = start_stop;
        this.end_stop = end_stop;
    }

    public String getInfo() {
        return id + " " + start_stop + " " + end_stop + "\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartStop() {
        return start_stop;
    }

    public void setStartStop(String start_stop) {
        this.start_stop = start_stop;
    }

    public String getEndStop() {
        return end_stop;
    }

    public void setEndStop(String end_stop) {
        this.end_stop = end_stop;
    }
}
