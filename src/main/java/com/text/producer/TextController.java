package com.text.producer;

import com.text.producer.domain.TextStatistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author Sergii
 */
@RestController
public class TextController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextController.class);

    private static List<String> sizes = List.of("short", "medium", "long", "verylong");

    @Autowired
    private TextService textService;

    @GetMapping(value = "/betvictor/text", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TextStatistic> generateText(@RequestParam Integer p, @RequestParam String l) {
        LOGGER.debug("Called GET:/betvictor/text?p={}&l={}.", p, l);
        if (p <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid argument 'p'. Must be great then 0. ");
        }
        if (!sizes.contains(l)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Invalid argument 'l'. Must one of: %s. ", String.join(",", sizes)));
        }

        TextStatistic statistic = textService.getAndProcessText(p, l);
        LOGGER.debug("Successfully finished GET:/betvictor/text... Response body: {}", statistic);
        return ResponseEntity.ok(statistic);
    }

}
