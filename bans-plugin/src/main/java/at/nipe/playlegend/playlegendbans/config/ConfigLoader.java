package at.nipe.playlegend.playlegendbans.config;

import at.nipe.playlegend.playlegendbans.shared.config.Config;
import lombok.extern.java.Log;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

@Log
public class ConfigLoader {

    private final JavaPlugin plugin;

    public ConfigLoader(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public Config load() {
        var config = plugin.getConfig();
        var configSection = config.getConfigurationSection("mysql");

        String url = null, user = null, password = null;

        try {
            assert configSection != null;
            url = configSection.getString("url");
            user = configSection.getString("user");
            password = configSection.getString("password");
        } catch (NullPointerException ex) {
            log.log(Level.SEVERE, "Missing part of mysql configuration in config.yml", ex);
            Bukkit.getServer().getPluginManager().disablePlugin(this.plugin);
        }

        return new Config(url, user, password);
    }
}
