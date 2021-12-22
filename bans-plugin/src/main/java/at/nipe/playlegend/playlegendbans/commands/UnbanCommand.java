package at.nipe.playlegend.playlegendbans.commands;

import at.nipe.playlegend.playlegendbans.BanFacade;
import at.nipe.playlegend.playlegendbans.context.ContextProperties;
import at.nipe.playlegend.playlegendbans.context.LocalePlaceholderHelper;
import at.nipe.playlegend.playlegendbans.localization.LocalHelper;
import at.nipe.playlegend.playlegendbans.localization.LocalKeys;
import at.nipe.playlegend.playlegendbans.localization.LocalizationContainer;
import at.nipe.playlegend.playlegendbans.shared.exceptions.AccountNotFoundException;
import at.nipe.playlegend.playlegendbans.shared.resolution.Component;
import lombok.extern.java.Log;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;

/**
 * Command that unbans a player.
 *
 * <p>Usage: /unban player
 *
 * @author NoSleep - NIPE
 */
@Log
@Component
public class UnbanCommand implements CommandExecutor {

  private final ResourceBundle resourceBundle;
  private final BanFacade banFacade;

  @Inject
  public UnbanCommand(LocalizationContainer localizationContainer, BanFacade banFacade) {
    this.resourceBundle = localizationContainer.getResourceBundle();
    this.banFacade = banFacade;
  }

  @Override
  public boolean onCommand(
      @Nonnull CommandSender sender,
      @Nonnull Command command,
      @Nonnull String label,
      @Nonnull String[] args) {
    if (!(sender instanceof ConsoleCommandSender) && !sender.hasPermission("playlegend.unban")) {
      sender.sendMessage(
          LocalHelper.translate(this.resourceBundle, LocalKeys.ERRORS_NO_PERMISSION));
      return true;
    }

    if (args.length < 1) {
      sender.sendMessage(
          LocalHelper.translate(this.resourceBundle, LocalKeys.ERRORS_UNBAN_ARGS_NOT_ENOUGH));
      return false;
    }

    try {
      var unbanned = this.banFacade.unban(args[0]);

      if (unbanned) {
        sender.sendMessage(
            LocalHelper.translate(
                this.resourceBundle,
                ContextProperties.of(
                    LocalePlaceholderHelper.combine(
                        LocalePlaceholderHelper.buildPlayerContext(sender),
                        LocalePlaceholderHelper.buildTargetPlayerContext(args[0]))),
                LocalKeys.SUCCESS_UNBAN_SUCCESSFUL));
      } else {
        sender.sendMessage(
            LocalHelper.translate(
                this.resourceBundle,
                ContextProperties.of(
                    LocalePlaceholderHelper.combine(
                        LocalePlaceholderHelper.buildPlayerContext(sender),
                        LocalePlaceholderHelper.buildTargetPlayerContext(args[0]))),
                LocalKeys.ERRORS_UNBAN_NOT_BANNED));
      }
      return true;
    } catch (SQLException e) {
      sender.sendMessage(
          LocalHelper.translate(
              this.resourceBundle,
              ContextProperties.of(
                  LocalePlaceholderHelper.combine(
                      LocalePlaceholderHelper.buildPlayerContext(sender),
                      LocalePlaceholderHelper.buildTargetPlayerContext(args[0]))),
              LocalKeys.ERRORS_UNBAN_ERROR));
      log.log(Level.SEVERE, "SQL Error occurred whilst unbanning player " + args[0], e);
    } catch (AccountNotFoundException e) {
      sender.sendMessage(
          LocalHelper.translate(
              this.resourceBundle,
              ContextProperties.of(LocalePlaceholderHelper.buildPlayerContext(e.getPlayerName())),
              LocalKeys.ERRORS_USER_NO_ACCOUNT));
    }

    return false;
  }
}
