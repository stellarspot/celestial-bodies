package celestial.data;

import java.util.Map;

public class DataTestUtils {

    public static void dumpData(Data data) {
        Map<String, Object> objects = data.getObjects();

        System.out.printf("Loaded objects: %d%n", objects.size());
        for (String key : objects.keySet()) {
            System.out.printf("  key: %s, object: %s%n", key, objects.get(key));
        }
    }
}
