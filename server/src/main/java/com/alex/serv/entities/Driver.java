package com.alex.serv.entities;

import javax.persistence.*;

@Entity
@Table(name = "driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    private String login;
    private String password;
    private String firstname;
    private String secondname;
    @OneToOne(mappedBy = "driver", fetch = FetchType.EAGER)
    private Route route;

    public Driver() {
    }

    public Driver(String login, String password, String firstname, String secondname) {
        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.secondname = secondname;
    }

    public String getInfo() {
        return id + " " + firstname + " " + secondname + "\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }
}
