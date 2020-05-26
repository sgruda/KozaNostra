package pl.lodz.p.it.ssbd2020.ssbd05.utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class DateFormatterTest {

    @Test
    public void DateFormatterTest1(){
        LocalDateTime localDateTime = LocalDateTime.of(2020,5,24,20,23);
        String newDate = DateFormatter.formatDate(localDateTime);
        String predictedDate = "2020-05-24 20:23:00";
        Assert.assertNotEquals(localDateTime.toString(), predictedDate);
        Assert.assertEquals(predictedDate,newDate);
    }
}