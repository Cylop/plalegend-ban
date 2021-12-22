package at.nipe.playlegend.playlegendbans.context;

import at.nipe.playlegend.playlegendbans.shared.resolution.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;
import java.util.UUID;

/**
 * Main idea and codebase from
 * https://github.com/Alan-Gomes/mcspring-boot/blob/master/src/main/java/dev/alangomes/springspigot/util/ServerUtil.java
 *
 * <p>Utilities to get sender id by the object and vice-versa, since storing the instance is not a
 * good practice (can cause memory issues if the sender quits and the object keep alive for too
 * long)</p>
 */
@Singleton
@Component
public class ServerUtil {

  private static final String CONSOLE_SENDER_ID = "*console*";

  private final Server server;

  @Inject
  public ServerUtil(Server server) {
    this.server = server;
  }

  /**
   * Get the unique id available for the {@param sender}. If the server is in online mode, it
   * will return the {@param sender} UUID, otherwise will return the player username in lower case.
   *
   * @param sender the sender to get the id from
   * @return the sender id, null if null sender input
   */
  public String getSenderId(@Nonnull CommandSender sender) {
    if (!(sender instanceof OfflinePlayer player)) {
      return CONSOLE_SENDER_ID;
    }
      return server.getOnlineMode()
        ? player.getUniqueId().toString()
        : Objects.requireNonNull(player.getName()).toLowerCase();
  }

  /**
   * Return the {@link org.bukkit.command.CommandSender} associated to the {@param id}, normally
   * used with {@link ServerUtil#getSenderId}
   *
   * @param id the id of the sender
   * @return the sender associated with {@param id}, null if null id input
   */
  public CommandSender getSenderFromId(@Nonnull String id) {
    if (CONSOLE_SENDER_ID.equals(id)) {
      return server.getConsoleSender();
    }
    if (id.length() <= 16) {
      return server.getPlayer(id);
    }
    return server.getPlayer(UUID.fromString(id));
  }
}
