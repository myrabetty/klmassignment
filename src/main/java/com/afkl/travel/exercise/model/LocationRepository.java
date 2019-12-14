package com.afkl.travel.exercise.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Long> {
    Optional<Location> findByTypeAndCode(String type, String code);
}
