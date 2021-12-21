package at.nipe.playlegend.playlegendbans.localization;

import at.nipe.playlegend.playlegendbans.context.ContextProperties;
import lombok.Synchronized;
import org.bukkit.ChatColor;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LocalHelper {

  private LocalHelper() {}

  public static String translate(ResourceBundle resourceBundle, String key) {
    return translate(resourceBundle, new ContextProperties(), key);
  }

  @Synchronized
  public static String translate(
      ResourceBundle resourceBundle, ContextProperties ctxProperties, String key)
      throws NullPointerException, MissingResourceException {
    if (resourceBundle == null) throw new NullPointerException("Resourcebundle must not be null");

    var message = ChatColor.translateAlternateColorCodes('&', resourceBundle.getString(key));
    if (ctxProperties != null) {
      for (var entry : ctxProperties.entrySet()) {
        message = message.replaceAll("%" + entry.getKey() + "%", entry.getValue());
      }
    }

    return message;
  }
}
