package com.compassuol.sp.challenge.msnotification.domain.model;

import com.compassuol.sp.challenge.msnotification.domain.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document("event_notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventNotification {
    @Id
    private String id;
    private String email;
    private EventType event;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date;
}
