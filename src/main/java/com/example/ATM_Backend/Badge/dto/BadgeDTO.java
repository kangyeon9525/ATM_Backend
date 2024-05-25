package com.example.ATM_Backend.Badge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadgeDTO {
    private Long id; // 배지 ID
    private String name; // 배지 이름
    private String description; // 배지 설명
    private int criteria; // 배지 획득 기준
    private String image_url; // 배지 이미지 URL

    public BadgeDTO(Long id, String name, String description, int criteria, String image_url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.criteria = criteria;
        this.image_url = image_url;
    }
}
