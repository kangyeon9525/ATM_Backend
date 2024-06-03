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

    private int criteria_attendance;
    private Integer criteria_goalusage; // 변경: Integer로 변경하여 null 허용
    private String description;
    private String name;
    private String image_url;
}
