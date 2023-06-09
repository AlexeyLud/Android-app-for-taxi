package com.alex.serv.entities;

import javax.persistence.*;

@Entity
@Table(name = "bus")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    private String info;
    private int count;
    @OneToOne(mappedBy = "bus", fetch = FetchType.EAGER)
    private Route route;

    public Bus() {
    }

    public Bus(String info, int count) {
        this.info = info;
        this.count = count;
    }

    public String getBus() {
        return info + " " + count + "\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}