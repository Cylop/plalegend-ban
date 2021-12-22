package at.nipe.playlegend.playlegendbans.commands;

import at.nipe.playlegend.playlegendbans.BanFacade;
import at.nipe.playlegend.playlegendbans.context.ContextProperties;
import at.nipe.playlegend.playlegendbans.context.LocalePlaceholderHelper;
import at.nipe.playlegend.playlegendbans.localization.LocalHelper;
import at.nipe.playlegend.playlegendbans.localization.LocalKeys;
import at.nipe.playlegend.playlegendbans.localization.LocalizationContainer;
import at.nipe.playlegend.playlegendbans.parser.durationparser.DurationParser;
import at.nipe.playlegend.playlegendbans.shared.DurationPossibilities;
import at.nipe.playlegend.playlegendbans.shared.ExampleReasons;
import at.nipe.playlegend.playlegendbans.shared.exceptions.AccountNotFoundException;
import at.nipe.playlegend.playlegendbans.shared.exceptions.UnknownDurationUnitException;
import at.nipe.playlegend.playlegendbans.shared.resolution.Component;
import lombok.extern.java.Log;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Command that bans a player for a given duration and a message.
 *
 * <p>Usage: /ban player [duration in format 5w7d4h] [message or reason]</p>
 *
 * @author NoSleep - NIPE
 */
@Log
@Component
public class BanCommand implements CommandExecutor, TabCompleter {

  private final ResourceBundle resourceBundle;
  private final BanFacade banFacade;

  @Inject
  public BanCommand(LocalizationContainer localizationContainer, BanFacade banFacade) {
    this.resourceBundle = localizationContainer.getResourceBundle();
    this.banFacade = banFacade;
  }

  @Override
  public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
    if (!(sender instanceof ConsoleCommandSender) && !sender.hasPermission("playlegend.ban")) {
      sender.sendMessage(LocalHelper.translate(this.resourceBundle, LocalKeys.ERRORS_NO_PERMISSION));
      return true;
    }
    // /ban <player> <duration> <message>
    if (args.length < 1) {
      sender.sendMessage(LocalHelper.translate(this.resourceBundle, LocalKeys.ERRORS_BAN_ARGS_NOT_ENOUGH));
      return false;
    }

    var playerName = args[0];

    var duration = "999y";
    if (args.length >= 2) {
      duration = args[1];
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
              LocalKeys.ERRORS_DURATION_INVALID_UNIT));
      return true;
    }

    var message = LocalHelper.translate(this.resourceBundle, LocalKeys.BAN_DEFAULT_REASON);

    if (args.length >= 3) {
      message = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
      message = ChatColor.stripColor(message);
    }

    try {
      // args[0] -> player that should be banned
      var ban = this.banFacade.banPlayer(playerName, sender, until, message);

      var player = Bukkit.getPlayerExact(playerName);
      if(player != null) {
        player.kickPlayer(
          LocalHelper.translate(
              this.resourceBundle,
              ContextProperties.of(LocalePlaceholderHelper.buildBanContext(ban)),
              LocalKeys.BAN_MESSAGE));
      }
      sender.sendMessage(LocalHelper.translate(this.resourceBundle, ContextProperties.of(LocalePlaceholderHelper.combine(LocalePlaceholderHelper.buildPlayerContext(sender), LocalePlaceholderHelper.buildTargetPlayerContext(playerName))), LocalKeys.SUCCESS_BAN_SUCCESSFUL)
      );
      return true;
    } catch (SQLException e) {
      sender.sendMessage(
          LocalHelper.translate(
              this.resourceBundle,
              ContextProperties.of(LocalePlaceholderHelper.buildPlayerContext(sender)),
              LocalKeys.ERRORS_BAN_ERROR));
      log.log(Level.SEVERE, String.format("SQL Error occurred whilst banning player %s", playerName), e);
    } catch (AccountNotFoundException e) {
      sender.sendMessage(
          LocalHelper.translate(
              this.resourceBundle,
              ContextProperties.of(LocalePlaceholderHelper.buildPlayerContext(e.getPlayerName())),
              LocalKeys.ERRORS_USER_NO_ACCOUNT));
    }
    return false;
  }

  @Override
  public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, String[] args) {
    List<String> result = null;

    // args[0] -> player that should be banned
    // args[2] -> message or reason
   switch (args.length) {
     case 1 -> result = Bukkit.getOnlinePlayers().stream()
             .filter(player -> !player.getName().equals(sender.getName()) && player.getName().startsWith(args[0]))
             .map(Player::getName)
             .collect(Collectors.toList());
     case 2 -> result = DurationPossibilities.getAllowedIdentifiers();
     case 3 -> result = ExampleReasons.EXAMPLE_BAN_REASONS.stream().filter(reason -> reason.startsWith(args[2])).collect(Collectors.toList());
     default -> {}
   }
    return result;
  }
}
