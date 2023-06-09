package com.alex.serv.services;

import com.alex.serv.entities.Stops;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StopsService {
    public Stops findByStopId(int id);
    public Stops findStopByName(String name);
    public List<Stops> getStops();
    public List<Stops> getStopsByRoute(int number);

}
