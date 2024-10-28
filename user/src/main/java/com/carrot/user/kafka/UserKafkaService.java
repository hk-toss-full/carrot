package com.carrot.user.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserKafkaService {

    private final UserKafkaProducer userKafkaProducer;


}
