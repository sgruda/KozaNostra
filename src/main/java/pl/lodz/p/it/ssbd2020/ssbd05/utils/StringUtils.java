package pl.lodz.p.it.ssbd2020.ssbd05.utils;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Klasa narzędziowa do operacji na ciągach znaków.
 */
public class StringUtils {

    /**
     * Metoda sprawdzająca, czy któryś z elementów kolekcji obiektów typu String zawiera dany ciąg znaków.
     *
     * @param strCollection Kolekcja obiektów typu String.
     * @param searchStr     Szukany ciąg znaków.
     * @return Wartość logiczna.
     */
    public static boolean collectionContainsIgnoreCase(Collection<String> strCollection, String searchStr) {
        return strCollection.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList())
                .contains(searchStr.toLowerCase());
    }
}
