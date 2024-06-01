package com.example.ATM_Backend.appTime.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AppTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName; // 유저 이름
    private String appName;//어플 이름
    private String date; //날짜
    private Integer dayOfWeek; // 1:일요일 ~ 7:토요일

    private Integer hour00; // 00시부터 1시간 단위로 앱 사용시간
    private Integer hour01;
    private Integer hour02;
    private Integer hour03;
    private Integer hour04;
    private Integer hour05;
    private Integer hour06;
    private Integer hour07;
    private Integer hour08;
    private Integer hour09;
    private Integer hour10;
    private Integer hour11;
    private Integer hour12;
    private Integer hour13;
    private Integer hour14;
    private Integer hour15;
    private Integer hour16;
    private Integer hour17;
    private Integer hour18;
    private Integer hour19;
    private Integer hour20;
    private Integer hour21;
    private Integer hour22;
    private Integer hour23;

}


