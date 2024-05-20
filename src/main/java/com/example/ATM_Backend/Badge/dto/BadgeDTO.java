package com.example.ATM_Backend.Badge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadgeDTO {
    private Long id;
    private String name;
    private String description;
    private int criteria;
    private String image_url;

    public BadgeDTO(Long id, String name, String description, int criteria, String image_url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.criteria = criteria;
        this.image_url = image_url;
    }
}
