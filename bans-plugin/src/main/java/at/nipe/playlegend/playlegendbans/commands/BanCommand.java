package at.nipe.playlegend.playlegendbans.commands;

import at.nipe.playlegend.playlegendbans.BanFacade;
import at.nipe.playlegend.playlegendbans.context.ContextProperties;
import at.nipe.playlegend.playlegendbans.context.LocalePlaceholderHelper;
import at.nipe.playlegend.playlegendbans.localization.LocalHelper;
import at.nipe.playlegend.playlegendbans.localization.LocalKeys;
import at.nipe.playlegend.playlegendbans.localization.LocalizationContainer;
import at.nipe.playlegend.playlegendbans.parser.durationparser.DurationParser;
import at.nipe.playlegend.playlegendbans.shared.exceptions.AccountNotFoundException;
import at.nipe.playlegend.playlegendbans.shared.exceptions.UnknownDurationUnitException;
import at.nipe.playlegend.playlegendbans.shared.resolution.Component;
import lombok.extern.java.Log;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;

/**
 * Command that bans a player for a given duration and a message.
 *
 * Usage: /ban player [duration in format 5w7d4h] [message or reason]
 *
 * @author NoSleep - NIPE
 */
@Log
@Component
public class BanCommand implements CommandExecutor {

  private final ResourceBundle resourceBundle;
  private final BanFacade banFacade;

  @Inject
  public BanCommand(LocalizationContainer localizationContainer, BanFacade banFacade) {
    this.resourceBundle = localizationContainer.getResourceBundle();
    this.banFacade = banFacade;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof ConsoleCommandSender) && !sender.hasPermission("playlegend.ban")) {
      sender.sendMessage(LocalHelper.translate(this.resourceBundle, LocalKeys.NO_PERMISSION));
      return true;
    }
    // /ban <player> <duration> <message>
    if (args.length < 1) {
      sender.sendMessage(LocalHelper.translate(this.resourceBundle, LocalKeys.NOT_ENOUGH_ARGS));
      return false;
    }

    var player = Bukkit.getPlayerExact(args[0]);
    if (player == null) {
      sender.sendMessage(
          LocalHelper.translate(
              this.resourceBundle,
              ContextProperties.of(LocalePlaceholderHelper.buildTargetPlayerContext(args[0])),
              LocalKeys.PLAYER_NOT_ONLINE));
      return true;
    }

    var duration = "999y";
    if (args.length >= 2) {
      duration = args[1]; // duration
    }

    Date until;
    try {
      until = new DurationParser().parse(duration);
    } catch (UnknownDurationUnitException e) {
      sender.sendMessage(
          LocalHelper.translate(
              this.resourceBundle,
              ContextProperties.of(
                  LocalePlaceholderHelper.combine(
                      LocalePlaceholderHelper.buildPlayerContext(sender),
                      LocalePlaceholderHelper.buildAllowedUnitsContext())),
              LocalKeys.INVALID_DURATION_UNIT));
      return true;
    }

    var message = LocalHelper.translate(this.resourceBundle, LocalKeys.DEFAULT_BAN_REASON);

    if (args.length >= 3) {
      message = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
    }

    try {
      var ban = this.banFacade.banPlayer(player, sender, until, message);

      player.kickPlayer(
          LocalHelper.translate(
              this.resourceBundle,
              ContextProperties.of(LocalePlaceholderHelper.buildBanContext(ban)),
              LocalKeys.BAN_MESSAGE));
      return true;
    } catch (SQLException e) {
      sender.sendMessage(
          LocalHelper.translate(
              this.resourceBundle,
              ContextProperties.of(LocalePlaceholderHelper.buildPlayerContext(sender)),
              LocalKeys.PLAYER_BANNED_ERROR));
      log.log(Level.SEVERE, "SQL Error occurred whilst banning player " + player.getName(), e);
    } catch (AccountNotFoundException e) {
      sender.sendMessage(
      LocalHelper.translate(
              this.resourceBundle,
              ContextProperties.of(LocalePlaceholderHelper.buildPlayerContext(sender)),
              LocalKeys.));
    }
    return false;
  }
}
