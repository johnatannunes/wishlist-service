package com.appstack.wishlist.domain.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExternalProductCacheProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic,  String event) {
        kafkaTemplate.send(topic, event);
    }
}
