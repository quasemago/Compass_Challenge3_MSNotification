package com.compassuol.sp.challenge.msnotification.domain.repository;

import com.compassuol.sp.challenge.msnotification.domain.model.EventNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventNotificationRepository extends MongoRepository<EventNotification, String> {
}
