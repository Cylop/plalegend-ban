package at.nipe.playlegend.playlegendbans.listener;

import at.nipe.playlegend.playlegendbans.context.Context;
import at.nipe.playlegend.playlegendbans.context.ContextProperties;
import at.nipe.playlegend.playlegendbans.context.LocalePlaceholderHelper;
import at.nipe.playlegend.playlegendbans.entities.User;
import at.nipe.playlegend.playlegendbans.localization.LocalHelper;
import at.nipe.playlegend.playlegendbans.localization.LocalKeys;
import at.nipe.playlegend.playlegendbans.localization.LocalizationContainer;
import at.nipe.playlegend.playlegendbans.services.bans.BanService;
import at.nipe.playlegend.playlegendbans.services.users.UserService;
import at.nipe.playlegend.playlegendbans.shared.resolution.Component;
import lombok.extern.java.Log;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ResourceBundle;

@Log
@Component
public class PlayerJoinListener implements Listener {

  private final Context context;
  private final ResourceBundle resourceBundle;
  private final UserService userService;
  private final BanService banService;

  @Inject
  public PlayerJoinListener(
      Context context,
      LocalizationContainer localizationContainer,
      UserService userService,
      BanService banService) {
    this.context = context;
    this.resourceBundle = localizationContainer.getResourceBundle();
    this.userService = userService;
    this.banService = banService;
  }

  @EventHandler
  public void handlePlayerLogin(PlayerLoginEvent event) throws SQLException {
    var player = event.getPlayer();

    try {
      this.userService.createIfNotExists(
          User.builder().id(player.getUniqueId()).name(player.getName()).build());
    } catch (SQLException e) {
      log.severe(LocalHelper.translate(this.resourceBundle, LocalKeys.CREATING_USER_ACCOUNT_ERROR));
      throw e;
    }

    var banOpt = this.banService.getLongestBan(player.getUniqueId());
    System.out.println(banOpt);
    if (banOpt.isPresent()) {
      var ban = banOpt.get();

      context.runInContext(
          player,
          ContextProperties.of(LocalePlaceholderHelper.buildBanContext(ban)),
          (ctxSender, ctxProps) -> {
            event.disallow(
                PlayerLoginEvent.Result.KICK_BANNED,
                LocalHelper.translate(this.resourceBundle, ctxProps, LocalKeys.BAN_MESSAGE));
            return null;
          });
    }
  }

  @EventHandler
  public void handlePlayerJoin(PlayerJoinEvent event) {
    Bukkit.getServer()
        .broadcastMessage(
            String.format("Player %s joined the Server", event.getPlayer().getName()));
  }
}
