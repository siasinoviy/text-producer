package com.text.producer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

/**
 * @author Sergii
 *
 * <freq_word>- the word that was the most frequent in the paragraphs
 * <avg_paragraph_size> - the average size of a paragraph
 * <avg_paragraph_processing_time> - the average time spent analyzing a paragraph
 * <total_processing_time> - total processing time to generate the final response
 *
 */
public class TextStatistic {

    @JsonProperty("freq_word")
    private String freqWord;

    @JsonProperty("avg_paragraph_size")
    private double avgParagraphSize;

    @JsonProperty("avg_paragraph_processing_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSSSSS")
    private LocalTime avgParagraphProcessingTime;

    @JsonProperty("total_processing_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSSSSS")
    private LocalTime totalProcessingTime;

    public String getFreqWord() {
        return freqWord;
    }

    public void setFreqWord(String freqWord) {
        this.freqWord = freqWord;
    }

    public double getAvgParagraphSize() {
        return avgParagraphSize;
    }

    public void setAvgParagraphSize(double avgParagraphSize) {
        this.avgParagraphSize = avgParagraphSize;
    }

    public LocalTime getAvgParagraphProcessingTime() {
        return avgParagraphProcessingTime;
    }

    public void setAvgParagraphProcessingTime(LocalTime avgParagraphProcessingTime) {
        this.avgParagraphProcessingTime = avgParagraphProcessingTime;
    }

    public LocalTime getTotalProcessingTime() {
        return totalProcessingTime;
    }

    public void setTotalProcessingTime(LocalTime totalProcessingTime) {
        this.totalProcessingTime = totalProcessingTime;
    }

    @Override
    public String toString() {
        return "TextStatistic{" +
                "freqWord='" + freqWord + '\'' +
                ", avgParagraphSize=" + avgParagraphSize +
                ", avgParagraphProcessingTime=" + avgParagraphProcessingTime +
                ", totalProcessingTime=" + totalProcessingTime +
                '}';
    }
}
