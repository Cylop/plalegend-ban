package at.nipe.playlegend.playlegendbans.config;

import at.nipe.playlegend.playlegendbans.shared.config.Config;
import lombok.extern.java.Log;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

/**
 * Class that loads the config.yml in the plugins data-folder. This is needed to store some basic
 * configuration for the database connection and some plugin tweaks.
 *
 * @author NoSleep - Nipe
 */
@Log
public class ConfigLoader {

  private static final String DATABASE_URL_REGX = "jdbc:mysql://";

  private final JavaPlugin plugin;

  public ConfigLoader(@Nonnull JavaPlugin plugin) {
    this.plugin = plugin;
  }

  /**
   * Loads the config
   *
   * @throws IllegalArgumentException if url matches not a jdbc url or if url is empty
   * @return constructed config object from config file
   */
  public Config load() {
    var config = plugin.getConfig();
    var configSection = config.getConfigurationSection("mysql");

    String url = null, user = null, password = null;

    try {
      url = configSection.getString("url");

      if (url == null || !url.startsWith(DATABASE_URL_REGX)) {
        throw new IllegalArgumentException(
            "Database Url in configuration is not matching the required scheme (jdbc:mysql://)");
      }
      // optional parameters
      user = configSection.getString("user");
      password = configSection.getString("password");
    } catch (NullPointerException ex) {
      if (url == null) {
        Bukkit.getServer().getPluginManager().disablePlugin(this.plugin);
        throw new IllegalArgumentException("Missing part of mysql configuration in config.yml", ex);
      }
    }

    return new Config(url, user, password);
  }
}
