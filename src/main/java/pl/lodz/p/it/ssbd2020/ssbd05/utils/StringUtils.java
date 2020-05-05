package pl.lodz.p.it.ssbd2020.ssbd05.utils;

import java.util.Collection;
import java.util.stream.Collectors;

public class StringUtils {

    public static boolean containsIgnoreCase(String str, String searchStr) {
        return str.toLowerCase().contains(searchStr.toLowerCase());
    }

    public static boolean collectionContainsIgnoreCase(Collection<String> strCollection, String searchStr) {
        return strCollection.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList())
                .contains(searchStr.toLowerCase());
    }
}
