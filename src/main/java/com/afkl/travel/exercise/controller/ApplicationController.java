package com.afkl.travel.exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(path = "/travel")
public class ApplicationController {

    private LocationService locationService;

    @Autowired
    public ApplicationController(LocationService locationService){
        this.locationService = locationService;
    }

    @GetMapping(value = "/locations")
    public ResponseEntity<List<UserLocation>> findAll(Locale locale){
        return new ResponseEntity<>(locationService.findAll(locale.getLanguage()), HttpStatus.OK);
    }

}
