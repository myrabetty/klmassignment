package com.afkl.travel.exercise.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String code;

    @Column(nullable = false)
    String type;

    double longitude;
    double latitude;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="parent_id")
    private Location parent;

    @OneToMany(mappedBy="parent")
    private List<Location> children;

    @OneToMany(mappedBy = "location")
    List<Translation> translations;

    public Location() {
    }
}
