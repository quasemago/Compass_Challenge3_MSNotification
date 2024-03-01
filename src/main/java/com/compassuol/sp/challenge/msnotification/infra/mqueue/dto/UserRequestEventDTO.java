package com.compassuol.sp.challenge.msnotification.infra.mqueue.dto;

import com.compassuol.sp.challenge.msnotification.domain.enums.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class UserRequestEventDTO {
    private String email;
    private EventType event;
    private Date date;
}