package com.compassuol.sp.challenge.msnotification.infra;

import com.compassuol.sp.challenge.msnotification.domain.model.EventNotification;
import com.compassuol.sp.challenge.msnotification.domain.repository.EventNotificationRepository;
import com.compassuol.sp.challenge.msnotification.infra.mqueue.dto.UserRequestEventDTO;
import com.compassuol.sp.challenge.msnotification.infra.mqueue.exception.InvalidNotificationDataException;
import com.compassuol.sp.challenge.msnotification.infra.mqueue.subscriber.UserRequestNotificationSubscriber;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.compassuol.sp.challenge.msnotification.common.NotificationUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class RabbitMQNotificationSubscriberTest {
    @InjectMocks
    private UserRequestNotificationSubscriber subscriber;
    @Mock
    private EventNotificationRepository repository;

    @Test
    public void handleUserRequestNotification_WithValidData_ReturnsNotificationEvent() throws JsonProcessingException {
        final String notification = mockNotificationData();
        final UserRequestEventDTO userRequestEventDTO = mockUserRequestEventDTO(notification);
        final EventNotification eventNotification = mockEventNotification(userRequestEventDTO);

        when(repository.save(any(EventNotification.class))).thenReturn(eventNotification);

        EventNotification sut = subscriber.handleUserRequestNotification(notification);

        assertThat(sut).isNotNull();
        assertThat(sut.getEmail()).isEqualTo(userRequestEventDTO.getEmail());
        assertThat(sut.getEvent()).isEqualTo(userRequestEventDTO.getEvent());
        assertThat(sut.getDate()).isEqualTo(userRequestEventDTO.getDate());
    }

    @Test
    public void handleUserRequestNotification_WithInvalidData_ThrowsException() {
        final String notification = mockInvalidNotificationData();

        assertThatThrownBy(() -> subscriber.handleUserRequestNotification(notification))
                .isInstanceOf(InvalidNotificationDataException.class);
    }
}
