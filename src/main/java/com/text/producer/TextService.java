package com.text.producer;

import com.text.producer.domain.TextStatistic;
import com.text.producer.kafka.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author Sergii
 */
@Service
public class TextService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextService.class);

    @Autowired
    private LoripsumService loripsumService;

    @Autowired
    private TextStatisticService statisticService;

    @Autowired
    private ProducerService producerService;

    public TextStatistic getAndProcessText(@NonNull Integer p, @NonNull String l) {
        LOGGER.debug("In getAndProcessText, paragraphs - {}, length - {}", p, l);

        List<String> dummyText = loripsumService.getDummyText(p, l);
        TextStatistic statistic = statisticService.calculateStatistic(dummyText);
        producerService.produce(statistic);

        LOGGER.debug("Successfully created TextStatistic, Body - {}", statistic);
        return statistic;
    }
}
