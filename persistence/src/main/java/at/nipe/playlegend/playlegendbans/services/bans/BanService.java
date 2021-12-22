package at.nipe.playlegend.playlegendbans.services.bans;

import at.nipe.playlegend.playlegendbans.entities.Ban;

import javax.annotation.Nonnull;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for {@link Ban} to provide the required functions to handle bans
 *
 * @author NoSleep - Nipe
 */
public interface BanService {

  List<Ban> findAllForUser(@Nonnull UUID uuid) throws SQLException;

  List<Ban> findAllByUser(@Nonnull UUID uuid) throws SQLException;

  Ban createIfNotExists(@Nonnull Ban ban) throws SQLException;

  int delete(@Nonnull Ban ban) throws SQLException;

  boolean unban(@Nonnull UUID uuid) throws SQLException;

  boolean isBanned(@Nonnull UUID user) throws SQLException;

  Optional<Ban> getLongestBan(@Nonnull UUID user) throws SQLException;
}
