package at.nipe.playlegend.playlegendbans.dao;

import at.nipe.playlegend.playlegendbans.entities.Ban;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Dao that is the direct connection between database and service
 *
 * @author NoSleep - Nipe
 */
public interface BanDao extends Dao<Ban, Long> {

  List<Ban> findAllForUser(UUID banned) throws SQLException;

  List<Ban> findAllByUser(UUID uuid) throws SQLException;

  boolean unban(UUID uuid) throws SQLException;

  boolean isBanned(UUID user) throws SQLException;

  Optional<Ban> getLongestBan(UUID user) throws SQLException;
}
