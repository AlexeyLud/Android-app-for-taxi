package com.alex.serv.entities;

import javax.persistence.*;

@Entity
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    private String starting_stop;
    private String ending_stop;
    @OneToOne
    @JoinColumn(name = "id_route")
    private Route route;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private Users user;

    public Trip() {
    }

    public Trip(String starting_stop, String ending_stop, Route route_id, Users user_id) {
        this.starting_stop = starting_stop;
        this.ending_stop = ending_stop;
        this.route = route_id;
        this.user = user_id;
    }

    public String getInfo() {
        return user.getStartStop() + " " + user.getEndStop() + " " + route.getNumber() + " " + user.getId() + "\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartingStop() {
        return starting_stop;
    }

    public void setStartingStop(String starting_stop) {
        this.starting_stop = starting_stop;
    }

    public String getEndingStop() {
        return ending_stop;
    }

    public void setEndingStop(String ending_stop) {
        this.ending_stop = ending_stop;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
