package at.nipe.playlegend.playlegendbans.services.bans;

import at.nipe.playlegend.playlegendbans.entities.Ban;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BanService {

  List<Ban> findAllForUser(UUID uuid) throws SQLException;

  List<Ban> findAllByUser(UUID uuid) throws SQLException;

  Ban createIfNotExists(Ban ban) throws SQLException;

  int delete(Ban ban) throws SQLException;

  boolean unban(UUID user) throws SQLException;

  boolean isBanned(UUID user) throws SQLException;

  Optional<Ban> getLongestBan(UUID user) throws SQLException;
}
