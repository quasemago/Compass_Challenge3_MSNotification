package com.compassuol.sp.challenge.msnotification.infra.mqueue.subscriber;

import com.compassuol.sp.challenge.msnotification.domain.model.EventNotification;
import com.compassuol.sp.challenge.msnotification.domain.repository.EventNotificationRepository;
import com.compassuol.sp.challenge.msnotification.infra.mqueue.dto.UserRequestEventDTO;
import com.compassuol.sp.challenge.msnotification.infra.mqueue.exception.InvalidNotificationDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRequestNotificationSubscriber {
    private final EventNotificationRepository repository;

    @RabbitListener(queues = "${mq.queues.user-queue}")
    public EventNotification handleUserRequestNotification(@Valid String notification) {
        try {
            final UserRequestEventDTO data = convertJsonIntoData(notification);

            final EventNotification event = new EventNotification();
            event.setEmail(data.getEmail());
            event.setEvent(data.getEvent());
            event.setDate(data.getDate());
            return repository.save(event);
        } catch (JsonProcessingException e) {
            throw new InvalidNotificationDataException("Não foi possível processar os dados da notificação do evento.");
        }
    }

    private UserRequestEventDTO convertJsonIntoData(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, UserRequestEventDTO.class);
    }
}
