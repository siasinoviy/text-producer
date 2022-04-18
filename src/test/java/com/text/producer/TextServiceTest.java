package com.text.producer;

import com.text.producer.domain.TextStatistic;
import com.text.producer.kafka.ProducerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Sergii
 */
@RunWith(MockitoJUnitRunner.class)
public class TextServiceTest {

    @InjectMocks
    private TextService textService;

    @Mock
    private LoripsumService loripsumService;

    @Mock
    private TextStatisticService statisticService;

    @Mock
    private ProducerService producerService;

    @Test
    public void testGetAndProcessText() {
        // Data
        Integer paragraphs = 3;
        String length = "short";
        List<String> paragraphList = List.of("Text one.", "Text two.", "Text three.");

        TextStatistic statistic = new TextStatistic();
        statistic.setFreqWord("text");
        statistic.setAvgParagraphSize(224.33333333333334);
        statistic.setAvgParagraphProcessingTime(LocalTime.ofNanoOfDay(170000));
        statistic.setTotalProcessingTime(LocalTime.ofNanoOfDay(1290000));

        //Mock
        when(loripsumService.getDummyText(paragraphs, length)).thenReturn(paragraphList);
        when(statisticService.calculateStatistic(paragraphList)).thenReturn(statistic);
        doNothing().when(producerService).produce(statistic);

        TextStatistic result = textService.getAndProcessText(paragraphs, length);

        verify(loripsumService).getDummyText(paragraphs, length);
        verify(statisticService).calculateStatistic(paragraphList);
        verify(producerService).produce(statistic);

        assertEquals("text", result.getFreqWord());
        assertEquals(224.33333333333334, result.getAvgParagraphSize(), 0.00001);
        assertEquals(170000, result.getAvgParagraphProcessingTime().toNanoOfDay());
        assertEquals(1290000, result.getTotalProcessingTime().toNanoOfDay());

    }


}
