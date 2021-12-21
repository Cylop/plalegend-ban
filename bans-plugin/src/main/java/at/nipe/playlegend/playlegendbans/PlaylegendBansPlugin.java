package at.nipe.playlegend.playlegendbans;

import at.nipe.playlegend.playlegendbans.commands.BanCommand;
import at.nipe.playlegend.playlegendbans.config.ConfigLoader;
import at.nipe.playlegend.playlegendbans.entities.Ban;
import at.nipe.playlegend.playlegendbans.entities.User;
import at.nipe.playlegend.playlegendbans.listener.PlayerJoinListener;
import com.google.inject.Injector;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.extern.java.Log;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Log
public class PlaylegendBansPlugin extends JavaPlugin {

  private Injector injector;

  @Override
  public void onEnable() {
    saveDefaultConfig();

    this.injector =
        BansInjector.createInjector(List.of(new PluginModule(this, new ConfigLoader(this).load())));

    var connectionSource = injector.getInstance(ConnectionSource.class);

    if (connectionSource != null) { // make sure connection is present
      try {
        TableUtils.createTableIfNotExists(connectionSource, User.class);
        TableUtils.createTableIfNotExists(connectionSource, Ban.class);
      } catch (SQLException e) {
        log.severe("Seems like the database is not connected");
      }
    }

    var playerJoinListener = injector.getInstance(PlayerJoinListener.class);

    this.getServer().getPluginManager().registerEvents(playerJoinListener, this);

    var banCommand = injector.getInstance(BanCommand.class);
    Objects.requireNonNull(this.getCommand("ban")).setExecutor(banCommand);
  }

  @Override
  public void onDisable() {
    this.injector = null;
  }
}
