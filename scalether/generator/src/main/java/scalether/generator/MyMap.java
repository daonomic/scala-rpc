package scalether.generator;

import java.util.HashMap;
import java.util.Map;

public class MyMap {
    private final Map<String, Integer> map = new HashMap<>();

    public Integer getValue(String key) {
        return map.get(key);
    }

    public MyMap setValue(String key, Integer value) {
        map.put(key, value);
        return this;
    }

    public MyMap clear() {
        map.clear();
        return this;
    }
}
