package com.alex.serv.serviceImpl;

import com.alex.serv.entities.Route;
import com.alex.serv.entities.Stops;
import com.alex.serv.repositories.RouteRepository;
import com.alex.serv.repositories.StopsRepository;
import com.alex.serv.services.StopsService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service("stopsServiceImpl")
public class StopsServiceImpl implements StopsService {

    private static final Logger logger = LoggerFactory.getLogger(StopsServiceImpl.class);

    @Autowired
    private StopsRepository stopsRepository;
    @Autowired
    private RouteRepository routeRepository;
    private List<Stops> stopslist = new ArrayList<>();
    private List<Stops> stopsByRoute = new ArrayList<>();

    @Override
    public List<Stops> getStops() {
        logger.info("-----[findAll Stops in repository]-----");
        stopslist = Lists.newArrayList(stopsRepository.findAll());
        return stopslist;
    }

    @Override
    public Stops findByStopId(int id) {
        logger.info("-----[findByStopId get Stop in repository]-----");
        return stopsRepository.findById(id).get();
    }

    @Override
    public Stops findStopByName(String name){
        logger.info("-----[findStopByName get Stop in repository]-----");
        return stopsRepository.findByName(name);
    }

    @Override
    public List<Stops> getStopsByRoute(int number) {
        logger.info("-----[findAll Stops by Route in repository]-----");
        Route route = routeRepository.findByNumber(number);
        stopsByRoute = route.getStops();
        return stopsByRoute;
    }

}
