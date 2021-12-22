package at.nipe.playlegend.playlegendbans.dao;

import at.nipe.playlegend.playlegendbans.entities.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

/**
 * Dao that is the direct connection between database and service
 *
 * @author NoSleep - Nipe
 */
public interface UserDao extends Dao<User, UUID> {
  Optional<User> findUserByName(String name) throws SQLException;
}
