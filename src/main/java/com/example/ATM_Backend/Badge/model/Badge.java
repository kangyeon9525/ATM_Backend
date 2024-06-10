package com.example.ATM_Backend.Badge.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer criteria_attendance; // null 허용
    private Integer criteria_goalusage; // null 허용
    private String description;
    private String name;
    private String image_url;
}
