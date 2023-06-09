package com.alex.serv.repositories;

import com.alex.serv.entities.Driver;
import com.alex.serv.entities.Route;
import com.alex.serv.entities.Trip;
import com.alex.serv.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
    public Route findByNumber(int number);
    public Route findByDriver(Driver driver);
}
