package dummyData;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CountryData {

   public Map<String, Set<String>> getCountryData() {
        Map<String, Set<String>> data = new HashMap<>();

        Set<String> set = new TreeSet<>();
        set.add("New York");
        set.add("San Fransisco");
        set.add("Denver");
        set.add("Los Angeles");
        data.put("USA", set);

        set = new TreeSet<>();
        set.add("Berlin");
        set.add("Munich");
        set.add("Frankfurt");
        data.put("Germany", set);

        set = new TreeSet<>();
        set.add("Sao Paolo");
        set.add("Rio de Janeiro");
        set.add("Salvador");
        data.put("Brazil", set);

        set = new TreeSet<>();
        set.add("Athens");
        set.add("Volos");
        set.add("Kavala");
        data.put("Greece", set);

        set = new TreeSet<>();
        set.add("London");
        set.add("Bristol");
        set.add("Chester");
        data.put("England", set);

        return data;
    }
}
