package com.example.ATM_Backend.goal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String appName;
    private String date;

    private Integer goalTime;
    private Integer howLong;
    private Integer onGoing;
}
