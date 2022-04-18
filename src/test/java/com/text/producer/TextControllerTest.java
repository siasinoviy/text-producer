package com.text.producer;

import com.text.producer.domain.TextStatistic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Sergii
 */
@WebMvcTest(TextController.class)
public class TextControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TextService textService;

    @Test
    public void testBetvictorTextRequest() throws Exception {
        Integer paragraphs = 3;
        String length = "short";

        TextStatistic statistic = new TextStatistic();
        statistic.setFreqWord("inquam");
        statistic.setAvgParagraphSize(224.33333333333334);
        statistic.setAvgParagraphProcessingTime(LocalTime.ofNanoOfDay(170000));
        statistic.setTotalProcessingTime(LocalTime.ofNanoOfDay(1290000));

        when(textService.getAndProcessText(paragraphs, length)).thenReturn(statistic);

        this.mockMvc.perform(get("/betvictor/text?p=3&l=short")).andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "\"freq_word\": \"inquam\",\n" +
                        "\"avg_paragraph_size\": 224.33333333333334,\n" +
                        "\"avg_paragraph_processing_time\": \"00:00:00.000170\",\n" +
                        "\"total_processing_time\": \"00:00:00.001290\"\n" +
                        "}"));
    }

    @Test
    public void testBetvictorTextRequestWithEmptyArguments() throws Exception {
        this.mockMvc.perform(get("/betvictor/text")).andExpect(status().isBadRequest());
    }

    @Test
    public void testBetvictorTextRequestWithBedParagraphsArgument() throws Exception {
        this.mockMvc.perform(get("/betvictor/text?p=-1&l=short")).andExpect(status().isBadRequest());
    }

    @Test
    public void testBetvictorTextRequestWithBadLengthArgument() throws Exception {
        this.mockMvc.perform(get("/betvictor/text?p=2&l=wrong")).andExpect(status().isBadRequest());
    }
}
