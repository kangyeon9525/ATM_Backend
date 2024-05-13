package com.example.ATM_Backend.appTime.model;
import com.example.ATM_Backend.appUser.model.AppUser;
import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Getter
@Setter
@Entity
public class AppTime {
    private String userName; // 유저 이름

    @Getter
    private String appName;//어플 이름

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date; //날짜
    private Integer dayOfWeek; // 1:일요일 ~ 7:토요일

    private Integer t_0; // 00시부터 1시간 단위로 앱 사용시간
    private Integer t_1;
    private Integer t_2;
    private Integer t_3;
    private Integer t_4;
    private Integer t_5;
    private Integer t_6;
    private Integer t_7;
    private Integer t_8;
    private Integer t_9;
    private Integer t_10;
    private Integer t_11;
    private Integer t_12;
    private Integer t_13;
    private Integer t_14;
    private Integer t_15;
    private Integer t_16;
    private Integer t_17;
    private Integer t_18;
    private Integer t_19;
    private Integer t_20;
    private Integer t_21;
    private Integer t_22;
    private Integer t_23;


}
