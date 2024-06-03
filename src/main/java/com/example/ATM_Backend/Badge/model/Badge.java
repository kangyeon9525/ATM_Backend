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

    private int criteria_attendance; // 출석 기준
    private int criteria_goalusage; // 목표 사용 시간 기준
    private String description;
    private String name;
    private String image_url;
}
