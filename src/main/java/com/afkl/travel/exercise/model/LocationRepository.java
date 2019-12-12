package com.afkl.travel.exercise.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {
    List<Location> findByTypeAndCode(String type, String code);
}
