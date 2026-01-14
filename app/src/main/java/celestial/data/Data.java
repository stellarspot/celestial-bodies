package celestial.data;

import java.util.LinkedHashMap;
import java.util.Map;

public class Data {

    private final Map<String, Object> objects = new LinkedHashMap<>();
    private final Map<String, Object> systems = new LinkedHashMap<>();

    public Map<String, Object> getObjects() {
        return objects;
    }

    public Map<String, Object> getSystems() {
        return systems;
    }
}
