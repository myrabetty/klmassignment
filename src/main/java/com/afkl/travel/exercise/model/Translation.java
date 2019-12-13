package com.afkl.travel.exercise.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Translation {

    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location", nullable = false)
    private Location location;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String name;

    private String description;

    public Translation(){

    }

    public Location getLocation() {
        return location;
    }

    public String getLanguage() {
        return language;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getId(){
        return id;
    }
}
