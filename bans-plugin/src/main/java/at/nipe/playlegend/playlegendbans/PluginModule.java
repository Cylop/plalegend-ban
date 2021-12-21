package at.nipe.playlegend.playlegendbans;

import at.nipe.playlegend.playlegendbans.shared.config.Config;
import com.google.inject.AbstractModule;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginModule extends AbstractModule {

  private final JavaPlugin plugin;
  private final Config config;

  public PluginModule(JavaPlugin plugin, Config config) {
    this.plugin = plugin;
    this.config = config;
  }

  @Override
  protected void configure() {
    bind(JavaPlugin.class).toInstance(this.plugin);
    bind(Server.class).toInstance(this.plugin.getServer());
    bind(Config.class).toInstance(this.config);
  }
}
