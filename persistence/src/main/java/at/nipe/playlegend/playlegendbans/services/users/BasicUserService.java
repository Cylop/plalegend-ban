package at.nipe.playlegend.playlegendbans.services.users;

import at.nipe.playlegend.playlegendbans.dao.UserDao;
import at.nipe.playlegend.playlegendbans.entities.User;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class BasicUserService implements UserService {

  private final UserDao userDao;

  @Inject
  public BasicUserService(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public Optional<User> findById(UUID uuid) throws SQLException {
    var user = this.userDao.queryForId(uuid);
    return user != null ? Optional.of(user) : Optional.empty();
  }

  @Override
  public Optional<User> findByName(String name) throws SQLException {
    return this.userDao.findUserByName(name);
  }

  @Override
  public User createIfNotExists(User user) throws SQLException {
    return this.userDao.createIfNotExists(user);
  }
}
