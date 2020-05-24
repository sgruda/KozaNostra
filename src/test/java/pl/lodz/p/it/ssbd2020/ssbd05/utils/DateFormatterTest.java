package pl.lodz.p.it.ssbd2020.ssbd05.utils;

import lombok.extern.java.Log;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateFormatterTest {

    @Test
    public void DateFormatterTest1(){
        LocalDateTime localDateTime = LocalDateTime.of(2020,05,24,20,23);
        String newDate = DateFormatter.formatDate(localDateTime);
        String predictedDate = "2020-05-24 20:23:00";
        Assert.assertNotEquals(localDateTime.toString(), predictedDate);
        Assert.assertEquals(predictedDate,newDate);
    }
}