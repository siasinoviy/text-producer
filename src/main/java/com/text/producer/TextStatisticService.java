package com.text.producer;

import com.text.producer.domain.TextStatistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sergii
 */
@Service
public class TextStatisticService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextStatisticService.class);

    private ThreadLocal<Long> avgParagraphProcessingTimeHolder = new ThreadLocal<>();

    public TextStatistic calculateStatistic(List<String> paragraphList) {
        LOGGER.debug("In calculateStatistic, paragraphList size - {}", paragraphList.size());

        long start = System.nanoTime();
        String freqWord = getTheMostFreqWord(paragraphList);
        double avgParagraphSize = getAvgParagraphSize(paragraphList);
        long end = System.nanoTime();

        long totalProcessingTime = end - start;

        TextStatistic textStatistic = new TextStatistic();
        textStatistic.setFreqWord(freqWord);
        textStatistic.setAvgParagraphSize(avgParagraphSize);
        textStatistic.setTotalProcessingTime(LocalTime.ofNanoOfDay(totalProcessingTime));
        textStatistic.setAvgParagraphProcessingTime(LocalTime.ofNanoOfDay(avgParagraphProcessingTimeHolder.get()));

        LOGGER.debug("Successfully processed text statistic, TextStatistic body - {}", textStatistic);
        return textStatistic;
    }

    /*
        Search the most freq word in the text.
        If all words occasion once then there's no candidate for the result and the method returns null.
     */
    public String getTheMostFreqWord(List<String> paragraphs) {
        LOGGER.debug("In getTheMostFreqWord, paragraphList size - {}", paragraphs.size());

        int max = 0;
        String freqWord = null;
        long processTime = 0;
        for (String paragraph : paragraphs) {
            long start = System.nanoTime();
            String[] words = paragraph.split("\\W+");
            Map<String, Integer> wordCounter = new HashMap<>();
            for (String word : words) {
                word = word.toLowerCase();
                Integer wordCount = wordCounter.get(word);
                if (wordCount == null) {
                    wordCounter.put(word, 1);
                } else {
                    wordCounter.put(word, wordCount++);
                    if (wordCount > max) {
                        max = wordCount;
                        freqWord = word;
                    }
                }
            }
            long end = System.nanoTime();
            processTime = processTime + (end - start);
        }
        long avgParagraphProcessingTime = processTime / paragraphs.size();
        LOGGER.debug("Average paragraph processing time - {}", avgParagraphProcessingTime);
        avgParagraphProcessingTimeHolder.set(avgParagraphProcessingTime);

        LOGGER.debug("the most frequent word in text - {}", freqWord);
        return freqWord;
    }

    public Double getAvgParagraphSize(List<String> paragraphs) {
        LOGGER.debug("In getAvgParagraphSize, paragraphList size - {}", paragraphs.size());
        double size = paragraphs.stream()
                .mapToInt(String::length)
                .average()
                .getAsDouble();
        LOGGER.debug("Average paragraph size - {}", size);
        return size;
    }

}
