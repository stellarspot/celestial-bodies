package celestial.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ObjectsBaseTest {

    private static final String PREFIX = "base.objects.milkyway.solar";

    @Test
    public void testObjects() throws Exception {
        Data data = DataLoader.loadData(getDataPath());
        DataTestUtils.dumpData(data);

        Map<String, Object> objects = data.getObjects();

        Object sunObj = objects.get(withPrefix("Sun"));
        Assertions.assertNotNull(sunObj);
        Map<String, Object> sun = (Map<String, Object>) sunObj;
        Assertions.assertEquals(1.9e30, sun.get("mass"));
        Assertions.assertEquals(6.96e8, sun.get("radius"));
    }

    private static String withPrefix(String name) {
        return String.format("%s.%s", PREFIX, name);
    }

    private static Path getDataPath() {
        return Paths.get("../data");
    }
}
