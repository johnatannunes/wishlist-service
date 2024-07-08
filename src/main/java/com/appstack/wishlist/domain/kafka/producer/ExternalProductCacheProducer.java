package com.appstack.wishlist.domain.kafka.producer;

import com.appstack.wishlist.common.logging.Logging;
import com.appstack.wishlist.config.MDCKey;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExternalProductCacheProducer {

    private static final Logger logger = LoggerFactory.getLogger(ExternalProductCacheProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic,  String event) {

        Logging.logger(logger).mdcKey(MDCKey.PROCESS_ID)
                .info("method: sendMessage");

        kafkaTemplate.send(topic, event);
    }
}
