package com.alex.serv.repositories;

import com.alex.serv.entities.Trip;
import com.alex.serv.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

    public Trip findByUser(Users user);

}
