package com.example.ATM_Backend.Badge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadgeDTO {
    private Long id;
    private String name;
    private String description;
    private int criteria_attendance;
    private Integer criteria_goalusage;
    private String image_url;

    public BadgeDTO(Long id, String name, String description, int criteria_attendance, Integer criteria_goalusage, String image_url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.criteria_attendance = criteria_attendance;
        this.criteria_goalusage = criteria_goalusage;
        this.image_url = image_url;
    }
}
