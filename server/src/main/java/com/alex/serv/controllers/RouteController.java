package com.alex.serv.controllers;

import com.alex.serv.entities.Bus;
import com.alex.serv.entities.Driver;
import com.alex.serv.entities.Route;
import com.alex.serv.entities.Stops;
import com.alex.serv.services.RouteService;
import com.alex.serv.services.StopsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/route")
public class RouteController {

    private static final Logger logger = LoggerFactory.getLogger(RouteController.class);
    @Autowired
    private RouteService routeService;
    @Autowired
    private StopsService stopsService;
    public List<Route> routes = new ArrayList<>();

    @GetMapping("/get-all")
    public String getAllRoutes(){
        logger.info("-----[GET ALL Route]-----");
        routes = routeService.getRoutes();
        String s = "";
        for (Route route : routes) {
            s += route.getInfo() + "\n";
        }
        return s;
    }

    @GetMapping("/get-one/{id}")
    public String getOneRoute(@PathVariable int id){
        logger.info("-----[GET ONE Route]-----");
        Route route = routeService.findByRouteId(id);
        return route.getInfo();
    }

    @GetMapping("/get-bus-by-driver/{number}")
    public String getBusByDriver(@PathVariable int number){
        logger.info("-----[GET Bus for Driver by Number Route]-----");
        Route route = routeService.findRouteByNumber(number);
        Driver driver = route.getDriver();
        Bus bus = route.getBus();
        String driver_info = driver.getFirstname() + " " + driver.getSecondname();
        String bus_name = bus.getInfo();
        String info = driver_info + " " + bus_name;
        return info;
    }

    @GetMapping("/get-routes-by-stops")
    public String getRoutesByStops(@RequestParam String startStop, @RequestParam String endStop) {
        logger.info("-----[GET List Routes by Stop ids]-----");
        List<Route> find_routes = new ArrayList<>();
        Stops stop1 = stopsService.findStopByName(startStop);
        Stops stop2 = stopsService.findStopByName(endStop);
        routes = routeService.getRoutes();
        for (Route route : routes) {
            if(route.getStops().contains(stop1) && route.getStops().contains(stop2)){
                find_routes.add(route);
            }
        }
        String route_name;
        int route_number;
        int route_id;
        String res = "";
        for(Route route : find_routes){
            route_name = route.getName();
            route_number = route.getNumber();
            route_id = route.getId();
            res += String.valueOf(route_id) + " " + String.valueOf(route_number) + " " + route_name + "\n";
        }
        return res;
    }

    @GetMapping("/get-route-by-driver")
    public String getRouteByDriver(@RequestParam Driver driver) {
        logger.info("-----[GET Route by Driver]-----");
        List<Route> find_routes = new ArrayList<>();
        Route route = routeService.findRouteByDriver(driver);
        String route_name = route.getName();
        int route_number = route.getNumber();
        int route_id = route.getId();
        String firsname = route.getDriver().getFirstname();
        String secondname = route.getDriver().getSecondname();
        String bus = route.getBus().getBus();
        String res = "";
        res += String.valueOf(route_id) + " "
                + String.valueOf(route_number) + " "
                + route_name + " "
                + firsname + " "
                + secondname + " "
                + bus;
        return res;
    }

}
