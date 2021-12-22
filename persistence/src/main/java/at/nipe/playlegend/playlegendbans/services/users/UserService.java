package at.nipe.playlegend.playlegendbans.services.users;

import at.nipe.playlegend.playlegendbans.entities.User;

import javax.annotation.Nonnull;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

  Optional<User> findById(@Nonnull final UUID uuid) throws SQLException;

  Optional<User> findByName(@Nonnull final String name) throws SQLException;

  User createIfNotExists(@Nonnull final User user) throws SQLException;
}
