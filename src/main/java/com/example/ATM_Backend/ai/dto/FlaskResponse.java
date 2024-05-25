package com.example.ATM_Backend.ai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlaskResponse {
    @JsonProperty("predict_time")
    private double predictTime;

    @JsonProperty("advice")
    private String advice;

    @JsonProperty("total_usage_time")
    private double totalUsageTime;

    @JsonProperty("aim_time")
    private double aimTime;
}
