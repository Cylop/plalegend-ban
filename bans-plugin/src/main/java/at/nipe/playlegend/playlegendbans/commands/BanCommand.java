package at.nipe.playlegend.playlegendbans.commands;

import at.nipe.playlegend.playlegendbans.BanFacade;
import at.nipe.playlegend.playlegendbans.context.Context;
import at.nipe.playlegend.playlegendbans.context.ContextProperties;
import at.nipe.playlegend.playlegendbans.context.LocalePlaceholderHelper;
import at.nipe.playlegend.playlegendbans.localization.LocalHelper;
import at.nipe.playlegend.playlegendbans.localization.LocalKeys;
import at.nipe.playlegend.playlegendbans.localization.LocalizationContainer;
import at.nipe.playlegend.playlegendbans.parser.durationparser.DurationParser;
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

@Log
@Component
public class BanCommand implements CommandExecutor {

  private final Context context;
  private final ResourceBundle resourceBundle;
  private final BanFacade banFacade;

  @Inject
  public BanCommand(
      Context context, LocalizationContainer localizationContainer, BanFacade banFacade) {
    this.context = context;
    this.resourceBundle = localizationContainer.getResourceBundle();
    this.banFacade = banFacade;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof ConsoleCommandSender) && !sender.hasPermission("playlegend.ban")) {
      context.runInContext(
          sender,
          (ctxSender) -> {
            ctxSender.sendMessage(
                LocalHelper.translate(this.resourceBundle, LocalKeys.NO_PERMISSION));
            return null;
          });
      return true;
    }
    // /ban <player> <duration> <message>
    if (args.length < 1) {
      context.runInContext(
          sender,
          (ctxSender) -> {
            ctxSender.sendMessage(
                LocalHelper.translate(this.resourceBundle, LocalKeys.NOT_ENOUGH_ARGS));
            return null;
          });
      return false;
    }

    var player = Bukkit.getPlayerExact(args[0]);
    if (player == null) {
      context.runInContext(
          sender,
          ContextProperties.of(LocalePlaceholderHelper.buildTargetPlayerContext(args[0])),
          (ctxSender, ctxProps) -> {
            ctxSender.sendMessage(
                LocalHelper.translate(this.resourceBundle, context, LocalKeys.PLAYER_NOT_ONLINE));
            return null;
          });
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
      context.runInContext(
          sender,
          ContextProperties.of(LocalePlaceholderHelper.buildAllowedUnitsContext()),
          (ctxSender, ctxProps) -> {
            ctxSender.sendMessage(
                LocalHelper.translate(
                    this.resourceBundle, context, LocalKeys.INVALID_DURATION_UNIT));
            return null;
          });
      log.log(Level.SEVERE, "An unknown unit has been provided", e);
      return true;
    }

    var message = LocalHelper.translate(this.resourceBundle, LocalKeys.DEFAULT_BAN_REASON);

    if (args.length >= 3) {
      message = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
    }

    try {
      var ban = this.banFacade.banPlayer(player, sender, until, message);
      context.runInContext(
          sender,
          ContextProperties.of(LocalePlaceholderHelper.buildBanContext(ban)),
          (ctxSender, ctxProps) -> {
            player.kickPlayer(
                LocalHelper.translate(this.resourceBundle, context, LocalKeys.BAN_MESSAGE));
            return null;
          });

      return true;
    } catch (SQLException e) {
      sender.sendMessage(
          LocalHelper.translate(this.resourceBundle, context, LocalKeys.PLAYER_BANNED_ERROR));
      log.severe("There was an error whilst banning a player");
    }

    return false;
  }
}
