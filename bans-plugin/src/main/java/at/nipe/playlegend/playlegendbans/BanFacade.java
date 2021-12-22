package at.nipe.playlegend.playlegendbans;

import at.nipe.playlegend.playlegendbans.entities.Ban;
import at.nipe.playlegend.playlegendbans.entities.User;
import at.nipe.playlegend.playlegendbans.services.bans.BanService;
import at.nipe.playlegend.playlegendbans.services.users.UserService;
import at.nipe.playlegend.playlegendbans.shared.exceptions.AccountNotFoundException;
import at.nipe.playlegend.playlegendbans.shared.resolution.Component;
import at.nipe.playlegend.playlegendbans.shared.utils.CommonUtil;
import lombok.extern.java.Log;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Date;

/**
 * Facade to provide a simple and combined interface for the ban and user service
 *
 * @author NoSleep - Nipe
 */
@Log
@Component
public class BanFacade {

  private final UserService userService;
  private final BanService banService;

  @Inject
  public BanFacade(UserService userService, BanService banService) {
    this.userService = userService;
    this.banService = banService;
  }

  /**
   * Unbans the provided player
   *
   * @param name of the player to unban
   * @return <code>true</code> if the operation was a success
   * @throws SQLException if something goes wrong
   */
  public boolean unban(@Nonnull String name) throws SQLException, AccountNotFoundException {
    var userOpt = this.userService.findByName(name);
    if (userOpt.isEmpty()) {
      throw new AccountNotFoundException("Couldn't find an User account to this name", name);
    }
    return this.banService.unban(userOpt.get().getId());
  }

  /**
   * Bans the specified player until the given date with the provided message.
   *
   * @param player who should be banned
   * @param sender who bans the player
   * @param until the ban is valid
   * @param message reason for the punishment. Will be displayed to the player
   * @return created BanEntity
   * @throws SQLException if some sql error occurred
   * @throws AccountNotFoundException if one of the players doesn't have a user account
   */
  public Ban banPlayer(
      @Nonnull String player,
      @Nonnull CommandSender sender,
      @Nonnull Date until,
      @Nonnull String message)
      throws SQLException, AccountNotFoundException {
    User toBeBanned;
    User banner;

    try {
      var userOpt = this.userService.findByName(player);
      if (userOpt.isEmpty()) {
        throw new AccountNotFoundException(
            String.format("The player %s has no User profile", player), player);
      }
      toBeBanned = userOpt.get();

      var bannerOpt =
          this.userService.findById(
              sender instanceof Player ? ((Player) sender).getUniqueId() : CommonUtil.SERVER_UUID);
      if (bannerOpt.isEmpty()) {
        throw new AccountNotFoundException(
            String.format("The player %s has no User profile", sender.getName()), sender.getName());
      }
      banner = bannerOpt.get();
    } catch (SQLException e) {
      throw new IllegalArgumentException("Whilst trying to ban a user an exception occurred", e);
    }

    final var ban =
        Ban.builder()
            .banned(toBeBanned)
            .bannedBy(banner)
            .reason(message)
            .until(until)
            .active(true)
            .build();

    return this.banService.createIfNotExists(ban);
  }
}
