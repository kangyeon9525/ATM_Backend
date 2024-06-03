package com.example.ATM_Backend.Badge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBadgeDTO {
    private Long id;
    private BadgeDTO badge;
    private boolean achieved;

    public UserBadgeDTO(Long id, BadgeDTO badge, boolean achieved) {
        this.id = id;
        this.badge = badge;
        this.achieved = achieved;
    }
}
