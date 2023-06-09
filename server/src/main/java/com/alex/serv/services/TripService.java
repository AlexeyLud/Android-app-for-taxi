package com.alex.serv.services;

import com.alex.serv.entities.Trip;
import com.alex.serv.entities.Users;
import org.springframework.stereotype.Component;

@Component
public interface TripService {
    public Trip saveTrip(Trip trip);
    public String deleteTripByUser(Users user);
}
