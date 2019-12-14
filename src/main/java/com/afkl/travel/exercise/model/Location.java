package com.afkl.travel.exercise.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Optional;

@Entity
public class Location {

    @Id
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String type;

    private Double longitude;
    private Double latitude;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "parent_id")
    private Location parent;

    @OneToMany(mappedBy = "parent")
    private List<Location> children;

    @OneToMany(mappedBy = "location")
    List<Translation> translations;

    public Location() {
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Optional<Location> getParent() {
        return Optional.ofNullable(parent);
    }

    public List<Location> getChildren() {
        return children;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public Long getId() {
        return id;
    }
}
