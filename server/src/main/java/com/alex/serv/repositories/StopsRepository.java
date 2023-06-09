package com.alex.serv.repositories;

import com.alex.serv.entities.Stops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopsRepository extends JpaRepository<Stops, Integer> {

    public Stops findByName(String name);

}
