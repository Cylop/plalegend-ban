package at.nipe.playlegend.playlegendbans.services.users;

import at.nipe.playlegend.playlegendbans.entities.User;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> findById(final UUID uuid) throws SQLException;
    Optional<User> findByName(final String name) throws SQLException;

    User createIfNotExists(final User user) throws SQLException;
}
