package at.nipe.playlegend.playlegendbans.services.bans;

import at.nipe.playlegend.playlegendbans.dao.BanDao;
import at.nipe.playlegend.playlegendbans.entities.Ban;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicBanService implements BanService {

  private final BanDao banDao;

  @Inject
  public BasicBanService(BanDao banDao) {
    this.banDao = banDao;
  }

  @Override
  public List<Ban> findAllForUser(@Nonnull UUID uuid) throws SQLException {
    return this.banDao.findAllForUser(uuid);
  }

  @Override
  public List<Ban> findAllByUser(@Nonnull UUID uuid) throws SQLException {
    return this.banDao.findAllByUser(uuid);
  }

  @Override
  public Ban createIfNotExists(@Nonnull Ban ban) throws SQLException {
    return this.banDao.createIfNotExists(ban);
  }

  @Override
  public int delete(@Nonnull Ban ban) throws SQLException {
    return this.banDao.delete(ban);
  }

  @Override
  public boolean unban(@Nonnull UUID uuid) throws SQLException {
    return this.banDao.unban(uuid);
  }

  @Override
  public boolean isBanned(@Nonnull UUID user) throws SQLException {
    return this.banDao.isBanned(user);
  }

  @Override
  public Optional<Ban> getLongestBan(@Nonnull UUID user) throws SQLException {
    return this.banDao.getLongestBan(user);
  }
}
