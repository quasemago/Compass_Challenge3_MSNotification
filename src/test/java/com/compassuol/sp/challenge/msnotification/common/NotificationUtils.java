package com.compassuol.sp.challenge.msnotification.common;

import com.compassuol.sp.challenge.msnotification.domain.model.EventNotification;
import com.compassuol.sp.challenge.msnotification.infra.mqueue.dto.UserRequestEventDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationUtils {
    public static String mockNotificationData() {
        return "{\"email\":\"teste@hotmail.com\",\"event\":\"LOGIN\",\"date\":\"2024-02-25T01:56:30.621+00:00\"}";
    }

    public static String mockInvalidNotificationData() {
        return "{\"email\":\"teste@hotmail.com\",\"event\":\"TESTE\",\"date\":\"2024-02-25\"}";
    }

    public static UserRequestEventDTO mockUserRequestEventDTO(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, UserRequestEventDTO.class);
    }

    public static EventNotification mockEventNotification(UserRequestEventDTO notification) {
        EventNotification eventNotification = new EventNotification();
        eventNotification.setEmail(notification.getEmail());
        eventNotification.setEvent(notification.getEvent());
        eventNotification.setDate(notification.getDate());
        return eventNotification;
    }
}
