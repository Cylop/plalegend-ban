package at.nipe.playlegend.playlegendbans.services.users;

import at.nipe.playlegend.playlegendbans.entities.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> findById(final UUID uuid);
    Optional<User> findByName(final String name);

    // probably never used
    void delete(final User user);

    void save(final User user);
}
