package com.afkl.travel.exercise.controller;

import com.afkl.travel.exercise.model.Location;
import com.afkl.travel.exercise.model.LocationRepository;
import com.afkl.travel.exercise.model.Translation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;

    }

    public List<UserLocation> findAll(String language) {
        return ((List<Location>) locationRepository.findAll())
                .stream()
                .map(x -> transform(x, language)).flatMap(Optional::stream).collect(Collectors.toList());
    }

    public Optional<UserLocation> findByTypeAndCode(String language, String type, String code) {
        return locationRepository.findByTypeAndCode(type, code)
                .flatMap(x -> transform(x, language));
    }

    private Optional<UserLocation> transform(Location location, String language) {

        if (location.getTranslations().isEmpty()){
            return createUserLocation(x);
        }

        if (location.getTranslations().stream().anyMatch(x -> language.equalsIgnoreCase(x.getLanguage()))) {
            return location.getTranslations().stream()
                    .filter(x -> (x.getLanguage().equalsIgnoreCase(language))).findFirst().map(x -> createUserLocation(location));
        }

        return location.getTranslations().stream()
                .filter(x -> (x.getLanguage().equalsIgnoreCase(String.valueOf(Locale.ENGLISH)))).findFirst()
                .map(x -> createUserLocation(location));
    }

    private UserLocation createUserLocation(Location location) {
        UserLocation userLocation = new UserLocation();
        userLocation.setCode(location.getCode());


        userLocation.setType(location.getType());
        userLocation.setLongitude(location.getLongitude());
        userLocation.setLatitude(location.getLatitude());

        location.getParent().ifPresent(x -> {
            userLocation.setParentCode(x.getCode());
            userLocation.setParentType(x.getType());
        });


        return userLocation;
    }

    private void addTranslation(UserLocation userLocation, Translation translation){
        userLocation.setName(translation.getName());
        userLocation.setDescription(translation.getDescription());
        userLocation.setLanguage(translation.getLanguage());
    }
}
