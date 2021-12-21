package at.nipe.playlegend.playlegendbans.context;

import at.nipe.playlegend.playlegendbans.shared.utils.Pair;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ContextProperties {

    private final Map<String, String> properties = new ConcurrentHashMap<>();

    public String getProperty(String key) {
        return this.properties.get(key);
    }

    public void put(String key, String property) {
        this.properties.put(key, property);
    }

    public Set<Map.Entry<String, String>> entrySet() {
        return properties.entrySet();
    }



    @SafeVarargs
    public static ContextProperties of(Pair<String, String>... pairs) {
        if(pairs == null || pairs.length == 0) throw new IllegalArgumentException("Provided pairs in context must not be null or empty");

        final var ctxProperties = new ContextProperties();
        Arrays.stream(pairs).forEach(pair -> ctxProperties.put(pair.key(), pair.value()));
        return ctxProperties;
    }
}
