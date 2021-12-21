package at.nipe.playlegend.playlegendbans.localization;

import at.nipe.playlegend.playlegendbans.context.Context;
import at.nipe.playlegend.playlegendbans.context.ContextProperties;
import lombok.Synchronized;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;

import java.util.ResourceBundle;

public class LocalHelper {

  private LocalHelper() {}

  public static String translate(ResourceBundle resourceBundle, String key) {
    return translate(resourceBundle, new ContextProperties(), key);
  }

  @Synchronized
  public static String translate(
      ResourceBundle resourceBundle, ContextProperties ctxProperties, String key) {
    if (resourceBundle == null) throw new NullPointerException("Resourcebundle must not be null");

    var message = ChatColor.translateAlternateColorCodes('&', resourceBundle.getString(key));
    if (ctxProperties != null) {
      for (var entry : ctxProperties.entrySet()) {
        message = message.replaceAll("%" + entry.getKey() + "%", entry.getValue());
      }
    }

    return message;
  }

  public static String translate(ResourceBundle resourceBundle, Context context, String key) {
    Validate.notNull(context);
    return translate(resourceBundle, context.getContextProperties(), key);
  }
}
