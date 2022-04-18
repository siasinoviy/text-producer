package com.text.producer.kafka;

import com.text.producer.domain.TextStatistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/*
 * @author Sergii
 */
@Service
public class ProducerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);
    private static final String ORDER_KEY = "order_key";

    @Value("${spring.kafka.topic}")
    private String kafkaTopic;

    @Value("${spring.kafka.ordered}")
    private boolean ordered;

    @Autowired
    private KafkaTemplate<String, TextStatistic> kafkaTemplate;

    public void produce(TextStatistic statistic) {
        LOGGER.debug("In kafka producer, message body: {} ", statistic);
        if(statistic.getFreqWord() == null) {
            LOGGER.debug("No message body, skip producing...");
            return;
        }
        if(ordered) {
            kafkaTemplate.send(kafkaTopic, ORDER_KEY, statistic);
        } else {
            kafkaTemplate.send(kafkaTopic, statistic);
        }
        LOGGER.debug("Message successfully sent to 'words.processed'...");
    }
}
