package com.text.producer;

import com.text.producer.domain.TextStatistic;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Sergii
 */
@SpringBootTest
public class StatisticServiceTest {

    @Autowired
    private TextStatisticService statisticService;

    @Test
    public void testTheMostFreqWord() {
        List<String> paragraphList = List.of("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in rebus" +
                        " apertissimis nimium longi sumus. Deinde disputat, quod cuiusque generis animantium statui" +
                        " deceat extremum. At eum nihili facit; Quid de Platone aut de Democrito loquar? Duo Reges:" +
                        " constructio interrete. Quid ad utilitatem tantae pecuniae? Non enim iam stirpis bonum" +
                        " quaeret, sed animalis. Inquit, dasne adolescenti veniam? ",
                "Huius ego nunc auctoritatem sequens idem faciam. Quorum altera prosunt, nocent altera." +
                        " Paria sunt igitur. Sequitur disserendi ratio cognitioque naturae; Istic sum, inquit. ",
                "Restinguet citius, si ardentem acceperit. Est, ut dicis, inquam. Poterat autem inpune;" +
                        " Si enim ad populum me vocas, eum. ");

        String freqWord = statisticService.getTheMostFreqWord(paragraphList);
        Assert.assertEquals("de", freqWord);
    }

    @Test
    public void testAvgParagraphSize() {
        List<String> paragraphList = List.of("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                        " Sed in rebus apertissimis nimium longi sumus. Deinde disputat, quod cuiusque" +
                        " generis animantium statui deceat extremum. At eum nihili facit; Quid de Platone" +
                        " aut de Democrito loquar? Duo Reges: constructio interrete. Quid ad utilitatem" +
                        " tantae pecuniae? Non enim iam stirpis bonum quaeret, sed animalis. Inquit," +
                        " dasne adolescenti veniam? ",
                "Huius ego nunc auctoritatem sequens idem faciam. Quorum altera prosunt, nocent altera." +
                        " Paria sunt igitur. Sequitur disserendi ratio cognitioque naturae; Istic sum, inquit. ",
                "Restinguet citius, si ardentem acceperit. Est, ut dicis, inquam. Poterat autem inpune;" +
                        " Si enim ad populum me vocas, eum. ");

        double avgParagraphSize = statisticService.getAvgParagraphSize(paragraphList);
        Assert.assertEquals(228.666, avgParagraphSize, 0.001);
    }

    @Test
    public void testCalculateStatistic() {
        List<String> paragraphList = List.of("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in rebus" +
                        " apertissimis nimium longi sumus. Deinde disputat, quod cuiusque generis animantium statui" +
                        " deceat extremum. At eum nihili facit; Quid de Platone aut de Democrito loquar? Duo Reges:" +
                        " constructio interrete. Quid ad utilitatem tantae pecuniae? Non enim iam stirpis bonum" +
                        " quaeret, sed animalis. Inquit, dasne adolescenti veniam? ",
                "Huius ego nunc auctoritatem sequens idem faciam. Quorum altera prosunt, nocent altera." +
                        " Paria sunt igitur. Sequitur disserendi ratio cognitioque naturae; Istic sum, inquit. ",
                "Restinguet citius, si ardentem acceperit. Est, ut dicis, inquam. Poterat autem inpune;" +
                        " Si enim ad populum me vocas, eum. ");

        TextStatistic statistic = statisticService.calculateStatistic(paragraphList);
        Assert.assertEquals("de", statistic.getFreqWord());
        Assert.assertEquals(228.666, statistic.getAvgParagraphSize(), 0.001);
    }



}
