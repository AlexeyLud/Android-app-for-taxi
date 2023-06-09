package com.alex.serv.serviceImpl;

import com.alex.serv.entities.Trip;
import com.alex.serv.entities.Users;
import com.alex.serv.repositories.TripRepository;
import com.alex.serv.services.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tripServiceImpl")
public class TripServiceImpl implements TripService {

    private static final Logger logger = LoggerFactory.getLogger(TripServiceImpl.class);

    @Autowired
    private TripRepository tripRepository;

    @Override
    public Trip saveTrip(Trip trip) {
        logger.info("-----[save Trip in repository]-----");
        trip = tripRepository.save(trip);
        return trip;
    }

    @Override
    public String deleteTripByUser(Users user){
        logger.info("-----[deleteTrip Trip in repository]-----");
        Trip t = tripRepository.findByUser(user);
        tripRepository.deleteById(t.getId());
        return "Ok! success deleted with id = " + t.getId();
    }
}
