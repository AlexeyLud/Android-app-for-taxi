package com.alex.serv.controllers;

import com.alex.serv.entities.Route;
import com.alex.serv.entities.Trip;
import com.alex.serv.entities.Users;
import com.alex.serv.services.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trip")
public class TripController {

    private static final Logger logger = LoggerFactory.getLogger(TripController.class);
    @Autowired
    private TripService tripService;

    @PostMapping("/add-trip")
    public String createTrip(@RequestParam Route route, @RequestParam Users user){
        logger.info("-----[CREATE Trip]-----");
        Trip trip = new Trip(user.getStartStop(), user.getEndStop(), route, user);
        Trip t = tripService.saveTrip(trip);
        String id = String.valueOf(trip.getId());
        return id + "\n" + t.getInfo();
    }

    @DeleteMapping("/delete-trip-by-user")
    public String deleteTrip(@RequestParam Users user){
        logger.info("-----[DELETE Trip]-----");
        return tripService.deleteTripByUser(user);
    }
}
