package pl.lodz.p.it.ssbd2020.ssbd05.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class StringUtilsTest {

    @Test
    public void StringUtilsTest1(){
        Collection<String> strCollection = new ArrayList<>();
        strCollection.add("TestowyElement");
        String filter = "testowyelement";
        boolean containsTest = StringUtils.collectionContainsIgnoreCase(strCollection,filter);
        Assert.assertTrue(containsTest);
    }
}
