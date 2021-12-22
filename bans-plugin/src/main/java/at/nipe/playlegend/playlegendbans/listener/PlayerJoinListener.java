package at.nipe.playlegend.playlegendbans.listener;

import at.nipe.playlegend.playlegendbans.context.ContextProperties;
import at.nipe.playlegend.playlegendbans.context.LocalePlaceholderHelper;
import at.nipe.playlegend.playlegendbans.entities.User;
import at.nipe.playlegend.playlegendbans.localization.MessageService;
import at.nipe.playlegend.playlegendbans.localization.Messages;
import at.nipe.playlegend.playlegendbans.services.bans.BanService;
import at.nipe.playlegend.playlegendbans.services.users.UserService;
import at.nipe.playlegend.playlegendbans.shared.exceptions.AccountCreationException;
import at.nipe.playlegend.playlegendbans.shared.resolution.Component;
import lombok.extern.java.Log;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;

/**
 * Handles Player login to prevent banned players from joining
 *
 * <p>On first join, creates User account
 *
 * @author NoSleep - Nipe
 */
@Log
@Singleton
@Component
public class PlayerJoinListener implements Listener {

  private final MessageService messageService;
  private final UserService userService;
  private final BanService banService;

  @Inject
  public PlayerJoinListener(
      @Nonnull MessageService messageService,
      @Nonnull UserService userService,
      @Nonnull BanService banService) {
    this.messageService = messageService;
    this.userService = userService;
    this.banService = banService;
  }

  @EventHandler
  public void handlePlayerLogin(PlayerLoginEvent event)
      throws SQLException, AccountCreationException {
    var player = event.getPlayer();

    try {
      this.userService.createIfNotExists(
          User.builder().id(player.getUniqueId()).name(player.getName()).build());
    } catch (SQLException e) {
      throw new AccountCreationException("Error whilst creating user account", e);
    }

    var banOpt = this.banService.getLongestBan(player.getUniqueId());
    if (banOpt.isPresent()) {
      var ban = banOpt.get();

      event.disallow(
          PlayerLoginEvent.Result.KICK_BANNED,
          this.messageService.receive(
              ContextProperties.of(LocalePlaceholderHelper.buildBanContext(ban, player)),
              Messages.BAN_MESSAGE));
    }
  }
}
