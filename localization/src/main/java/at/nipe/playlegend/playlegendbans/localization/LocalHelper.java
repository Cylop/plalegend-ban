package at.nipe.playlegend.playlegendbans.localization;

import at.nipe.playlegend.playlegendbans.context.ContextProperties;
import lombok.Synchronized;
import org.bukkit.ChatColor;

import javax.annotation.Nonnull;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LocalHelper {

  private LocalHelper() {}

  public static String translate(ResourceBundle resourceBundle, String key) {
    return translate(resourceBundle, new ContextProperties(), key);
  }

  @Synchronized
  public static String translate(
      @Nonnull ResourceBundle resourceBundle,
      @Nonnull ContextProperties ctxProperties,
      @Nonnull String key)
      throws MissingResourceException {
    var message = ChatColor.translateAlternateColorCodes('&', resourceBundle.getString(key));
    for (var entry : ctxProperties.entrySet()) {
      message = message.replaceAll("%" + entry.getKey() + "%", entry.getValue());
    }
    return message;
  }
}
