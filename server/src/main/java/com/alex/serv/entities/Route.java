package com.alex.serv.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "route")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    private String name;
    private int number;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id")
    private Driver driver;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bus_id")
    private Bus bus;
    @OneToOne(mappedBy = "route", fetch = FetchType.EAGER)
    private Trip trip;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "route_stops",
            joinColumns = @JoinColumn(name = "id_route"),
            inverseJoinColumns = @JoinColumn(name = "id_stops"))
    private List<Stops> stops;

    public Route() {
    }

    public Route(String name, int number, Driver driver_id, Bus bus_id) {
        this.name = name;
        this.number = number;
        this.driver = driver_id;
        this.bus = bus_id;
    }

    public String getInfo() {
        return number + " " + name;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public List<Stops> getStops() {
        return stops;
    }

    public void setStops(List<Stops> stops) {
        this.stops = stops;
    }
}
