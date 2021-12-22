package at.nipe.playlegend.playlegendbans.context;

import at.nipe.playlegend.playlegendbans.shared.utils.Pair;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ContextProperties {

  private final Map<String, String> properties = new ConcurrentHashMap<>();

  public static ContextProperties of(@Nonnull List<Pair<String, String>> pairs) {
    if (pairs.size() == 0)
      throw new IllegalArgumentException("Provided pairs in context must not be null or empty");

    final var ctxProperties = new ContextProperties();
    pairs.forEach(pair -> ctxProperties.put(pair.key(), pair.value()));
    return ctxProperties;
  }

  public String getProperty(String key) {
    return this.properties.get(key);
  }

  public void put(@Nonnull String key, @Nonnull String property) {
    this.properties.put(key, property);
  }

  public Set<Map.Entry<String, String>> entrySet() {
    return properties.entrySet();
  }
}
