package at.nipe.playlegend.playlegendbans.services.users;

import at.nipe.playlegend.playlegendbans.entities.User;
import at.nipe.playlegend.playlegendbans.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultUserService implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(final UUID uuid) {
        return this.userRepository.findById(uuid);
    }

    @Override
    public Optional<User> findByName(final String name) {
        return this.userRepository.findUserByName(name);
    }

    @Override
    public void delete(final User user) {
        this.userRepository.delete(user);
    }

    @Override
    public void save(final User user) {
        this.userRepository.save(user);
    }
}
