package com.afkl.travel.exercise.controller;

public class UserLocation {
    private String code;
    private String name;
    private String language;
    private String type;
    private Double latitude;
    private Double longitude;
    private String description;
    private String parentCode;
    private String parentType;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }

    public String getParentCode() {
        return parentCode;
    }

    public String getParentType() {
        return parentType;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
