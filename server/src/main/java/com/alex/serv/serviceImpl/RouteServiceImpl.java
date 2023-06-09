package com.alex.serv.serviceImpl;

import com.alex.serv.entities.Driver;
import com.alex.serv.entities.Route;
import com.alex.serv.repositories.RouteRepository;
import com.alex.serv.services.RouteService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("routeServiceImpl")
public class RouteServiceImpl implements RouteService {

    private static final Logger logger = LoggerFactory.getLogger(RouteServiceImpl.class);
    @Autowired
    private RouteRepository routeRepository;
    private List<Route> routelist = new ArrayList<>();

    @Override
    public List<Route> getRoutes() {
        logger.info("-----[findAll Routes in repository]-----");
        routelist = Lists.newArrayList(routeRepository.findAll());
        return routelist;
    }

    @Override
    public Route findByRouteId(int id) {
        logger.info("-----[findByRouteId get Route in repository]-----");
        return routeRepository.findById(id).get();
    }

    @Override
    public Route findRouteByNumber(int number) {
        logger.info("-----[findByNumber get Route in repository]-----");
        return routeRepository.findByNumber(number);
    }
    @Override
    public Route findRouteByDriver(Driver driver){
        logger.info("-----[findRouteByDriver get Route in repository]-----");
        return routeRepository.findByDriver(driver);
    }
}
