package at.nipe.playlegend.playlegendbans.localization;

import at.nipe.playlegend.playlegendbans.context.ContextProperties;
import at.nipe.playlegend.playlegendbans.shared.resolution.Component;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Synchronized;
import org.bukkit.ChatColor;

import javax.annotation.Nonnull;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Service that receives the requested key from properties file and replaces any present placeholder
 *
 * @author NoSleep - NIPE
 */
@Singleton
@Component
public class MessageService {

  private final ResourceBundle resourceBundle;

  @Inject
  private MessageService(MessageContainer localizationContainer) {
    this.resourceBundle = localizationContainer.getResourceBundle();
  }

  public String receive(@Nonnull String key) {
    return receive(new ContextProperties(), key);
  }

  @Synchronized
  public String receive(@Nonnull ContextProperties ctxProperties, @Nonnull String key)
      throws MissingResourceException {
    var message = ChatColor.translateAlternateColorCodes('&', resourceBundle.getString(key));
    for (var entry : ctxProperties.entrySet()) {
      message = message.replaceAll("%" + entry.getKey() + "%", entry.getValue());
    }
    return message;
  }
}
