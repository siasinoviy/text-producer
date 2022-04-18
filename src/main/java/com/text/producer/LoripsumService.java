package com.text.producer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergii
 */
@Service
public class LoripsumService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoripsumService.class);

    public List<String> getDummyText(Integer paragraphs, String length) {
        LOGGER.debug("In getDummyText, paragraphs - {}, length - {}",paragraphs, length);
        try {
            Document doc = Jsoup.connect(String.format("https://www.loripsum.net/api/%s/%s", paragraphs, length)).get();
            List<String> listTexts = doc.select("p").stream().map(Element::text).collect(Collectors.toList());
            if(listTexts.size() == 0) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "No text produced by https://www.loripsum.net/ service...");
            }
            LOGGER.debug("Successfully retrieved dummy texts, size - {}", listTexts.size());
            return listTexts;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

}
