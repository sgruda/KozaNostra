package pl.lodz.p.it.ssbd2020.ssbd05.utils;

import lombok.extern.java.Log;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
@Log
public class StringUtilsTest {

    @Test
    public void StringUtilsTest1(){
        Collection<String> strCollection = new ArrayList<String>();
        strCollection.add("TestowyElement");
        String filter = "testowyelement";
        boolean containsTest = StringUtils.collectionContainsIgnoreCase(strCollection,filter);
        Assert.assertTrue(containsTest);
    }
}
