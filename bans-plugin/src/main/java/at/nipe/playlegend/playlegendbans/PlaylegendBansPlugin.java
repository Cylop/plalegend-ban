package at.nipe.playlegend.playlegendbans;

import at.nipe.playlegend.playlegendbans.commands.BanCommand;
import at.nipe.playlegend.playlegendbans.commands.UnbanCommand;
import at.nipe.playlegend.playlegendbans.config.ConfigLoader;
import at.nipe.playlegend.playlegendbans.entities.Ban;
import at.nipe.playlegend.playlegendbans.entities.User;
import at.nipe.playlegend.playlegendbans.listener.PlayerJoinListener;
import at.nipe.playlegend.playlegendbans.services.users.UserService;
import at.nipe.playlegend.playlegendbans.shared.config.Config;
import at.nipe.playlegend.playlegendbans.shared.utils.CommonUtil;
import com.google.inject.Injector;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.extern.java.Log;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

@Log
public class PlaylegendBansPlugin extends JavaPlugin {

  private Injector injector;

  /** Because we declared another constructor, we need to explicit create an empty one */
  public PlaylegendBansPlugin() {
    super();
  }

  /** This constructor is for testing required */
  protected PlaylegendBansPlugin(
      JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
    super(loader, description, dataFolder, file);
  }

  @Override
  public void onEnable() {
    saveDefaultConfig();

    this.injector =
        BansInjector.createInjector(List.of(new PluginModule(this, new ConfigLoader(this).load())));

    try {
      this.prepareDatabase();
    } catch (SQLException e) {
      log.log(Level.SEVERE, e, () -> "Error occurred whilst creating Server User");
      Bukkit.getPluginManager().disablePlugin(this);
    }

    var playerJoinListener = this.injector.getInstance(PlayerJoinListener.class);

    this.getServer().getPluginManager().registerEvents(playerJoinListener, this);

    var banCommand = injector.getInstance(BanCommand.class);
    Objects.requireNonNull(this.getCommand("ban")).setExecutor(banCommand);
    Objects.requireNonNull(this.getCommand("ban")).setTabCompleter(banCommand);

    var unbanCommand = injector.getInstance(UnbanCommand.class);
    Objects.requireNonNull(this.getCommand("unban")).setExecutor(unbanCommand);
  }

  private void prepareDatabase() throws SQLException {
    var config = this.injector.getInstance(Config.class);

    var connectionSource = this.injector.getInstance(ConnectionSource.class);

    if (connectionSource != null) { // make sure connection is present
      try {
        TableUtils.createTableIfNotExists(connectionSource, User.class);
        TableUtils.createTableIfNotExists(connectionSource, Ban.class);
      } catch (SQLException e) {
        throw new IllegalArgumentException(
            "There was an error whilst trying to create the tables in the database", e);
      }

      var userService = this.injector.getInstance(UserService.class);
      userService.createIfNotExists(
          User.builder().name("Server").id(CommonUtil.SERVER_UUID).build());
    }
  }

  private void registerHandlers() {}

  @Override
  public void onDisable() {
    this.injector = null;
  }
}
