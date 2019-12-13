package com.afkl.travel.exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping(path = "/travel")
public class ApplicationController {

    private LocationService locationService;

    @Autowired
    public ApplicationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @RequestMapping(value = "/locations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserLocation> findAll(Locale locale) {
        return locationService.findAll(locale.getLanguage());
    }

    @RequestMapping(value = "/locations/{type}/{code}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<UserLocation> findByTypeAndCode(Locale locale, @PathVariable("type") String type, @PathVariable("code") String code) {
        return locationService.findByTypeAndCode(locale.getLanguage(), type, code);
    }

}
