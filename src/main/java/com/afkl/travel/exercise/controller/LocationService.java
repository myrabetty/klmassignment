package com.afkl.travel.exercise.controller;

import com.afkl.travel.exercise.model.Location;
import com.afkl.travel.exercise.model.LocationRepository;
import com.afkl.travel.exercise.model.Translation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;

    }

    public List<UserLocation> findAll(String language) {
        return ((List<Location>) locationRepository.findAll())
                .stream()
                .map(x -> transform(x, language)).collect(Collectors.toList());
    }

    public UserLocation findByTypeAndCode(String language, String type, String code) {
        return locationRepository.findByTypeAndCode(type, code)
                .map(x -> transform(x, language)).orElseGet(UserLocation::new);
    }

    private UserLocation transform(Location location, String language) {

        final UserLocation userLocation = createUserLocation(location);

        location.getTranslations().stream().filter(x -> language.equalsIgnoreCase(x.getLanguage())).findFirst()
                .ifPresentOrElse(x -> addTranslation(userLocation, x),
                        () -> setEnglish(location, userLocation));

        return userLocation;
    }

    private void setEnglish(Location location, UserLocation userLocation) {
        location.getTranslations().stream()
                .filter(x -> (x.getLanguage().equalsIgnoreCase(String.valueOf(Locale.ENGLISH)))).findFirst()
                .ifPresent(x -> addTranslation(userLocation, x));
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

    private void addTranslation(UserLocation userLocation, Translation translation) {
        userLocation.setName(translation.getName());
        userLocation.setDescription(translation.getDescription());
        userLocation.setLanguage(translation.getLanguage());
    }
}
