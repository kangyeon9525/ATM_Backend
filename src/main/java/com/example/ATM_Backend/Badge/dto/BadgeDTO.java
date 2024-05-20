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
    private String imageUrl;

    public BadgeDTO(Long id, String name, String description, int criteria, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.criteria = criteria;
        this.imageUrl = imageUrl;  // 필드 초기화
    }
}
