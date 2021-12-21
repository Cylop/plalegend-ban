package at.nipe.playlegend.playlegendbans.localization;

import at.nipe.playlegend.playlegendbans.context.Context;
import org.bukkit.ChatColor;

import java.util.ResourceBundle;

public class LocalHelper {

  private LocalHelper() {}

  public static String translate(ResourceBundle resourceBundle, String key) {
    return translate(resourceBundle, null, key);
  }

  public static String translate(ResourceBundle resourceBundle, Context context, String key) {
    if (resourceBundle == null) throw new NullPointerException("Resourcebundle must not be null");

    var message = ChatColor.translateAlternateColorCodes('&', resourceBundle.getString(key));

    if (context != null && context.getContextProperties() != null) {
      var ctxProps = context.getContextProperties();
      for (var entry : ctxProps.entrySet()) {
        message = message.replaceAll("%" + entry.getKey() + "% ", entry.getValue());
      }
    }

    return message;
  }
}
