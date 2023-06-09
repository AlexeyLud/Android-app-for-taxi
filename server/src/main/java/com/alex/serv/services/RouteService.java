package com.alex.serv.services;

import com.alex.serv.entities.Driver;
import com.alex.serv.entities.Route;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface RouteService {
    public Route findByRouteId(int id);
    public List<Route> getRoutes();
    public Route findRouteByNumber(int number);
    public Route findRouteByDriver(Driver driver);
}
