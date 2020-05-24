package pl.lodz.p.it.ssbd2020.ssbd05.utils;

import org.junit.Assert;
import org.junit.Test;

public class HashGeneratorTest {

    @Test
    public void HashGeneratorTest1(){
        String predictedPassword= "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
        String generatedPassword= "test";
        generatedPassword = HashGenerator.sha256(generatedPassword);
        Assert.assertEquals(predictedPassword,generatedPassword);
    }
}
