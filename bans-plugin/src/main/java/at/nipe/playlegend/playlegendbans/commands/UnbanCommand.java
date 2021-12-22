package at.nipe.playlegend.playlegendbans.commands;

import at.nipe.playlegend.playlegendbans.BanFacade;
import at.nipe.playlegend.playlegendbans.context.ContextProperties;
import at.nipe.playlegend.playlegendbans.localization.MessageService;
import at.nipe.playlegend.playlegendbans.localization.Messages;
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
import java.util.logging.Level;

import static at.nipe.playlegend.playlegendbans.context.LocalePlaceholderHelper.*;

/**
 * Command that unbans a player.
 *
 * <p>Usage: /unban player
 *
 * @author NoSleep - Nipe
 */
@Log
@Component
public class UnbanCommand implements CommandExecutor {

  private final MessageService messageService;
  private final BanFacade banFacade;

  @Inject
  public UnbanCommand(@Nonnull MessageService messageService, @Nonnull BanFacade banFacade) {
    this.messageService = messageService;
    this.banFacade = banFacade;
  }

  @Override
  public boolean onCommand(
      @Nonnull CommandSender sender,
      @Nonnull Command command,
      @Nonnull String label,
      @Nonnull String[] args) {
    if (!(sender instanceof ConsoleCommandSender) && !sender.hasPermission("playlegend.unban")) {
      sender.sendMessage(this.messageService.receive(Messages.ERRORS_NO_PERMISSION));
      return true;
    }

    if (args.length < 1) {
      sender.sendMessage(this.messageService.receive(Messages.ERRORS_UNBAN_ARGS_NOT_ENOUGH));
      return false;
    }

    try {
      var unbanned = this.banFacade.unban(args[0]);

      if (unbanned) {
        sender.sendMessage(
            this.messageService.receive(
                ContextProperties.of(
                    combine(buildPlayerContext(sender), buildTargetPlayerContext(args[0]))),
                Messages.SUCCESS_UNBAN_SUCCESSFUL));
      } else {
        sender.sendMessage(
            this.messageService.receive(
                ContextProperties.of(
                    combine(buildPlayerContext(sender), buildTargetPlayerContext(args[0]))),
                Messages.ERRORS_UNBAN_NOT_BANNED));
      }
      return true;
    } catch (SQLException e) {
      sender.sendMessage(
          this.messageService.receive(
              ContextProperties.of(
                  combine(buildPlayerContext(sender), buildTargetPlayerContext(args[0]))),
              Messages.ERRORS_UNBAN_ERROR));
      log.log(Level.SEVERE, "SQL Error occurred whilst unbanning player " + args[0], e);
    } catch (AccountNotFoundException e) {
      sender.sendMessage(
          this.messageService.receive(
              ContextProperties.of(buildPlayerContext(e.getPlayerName())),
              Messages.ERRORS_USER_NO_ACCOUNT));
    }

    return false;
  }
}
