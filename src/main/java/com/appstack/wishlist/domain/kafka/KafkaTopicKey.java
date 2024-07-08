package com.appstack.wishlist.domain.kafka;

public enum KafkaTopicKey {
    PRODUCT_EXTERNAL_TOPIC("product-external-events");

    private final String topic;

    KafkaTopicKey(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
