package at.nipe.playlegend.playlegendbans;

import at.nipe.playlegend.playlegendbans.dao.BanDao;
import at.nipe.playlegend.playlegendbans.dao.BasicBanDao;
import at.nipe.playlegend.playlegendbans.dao.BasicUserDao;
import at.nipe.playlegend.playlegendbans.dao.UserDao;
import at.nipe.playlegend.playlegendbans.entities.Ban;
import at.nipe.playlegend.playlegendbans.entities.User;
import at.nipe.playlegend.playlegendbans.services.bans.BanService;
import at.nipe.playlegend.playlegendbans.services.bans.BasicBanService;
import at.nipe.playlegend.playlegendbans.services.users.BasicUserService;
import at.nipe.playlegend.playlegendbans.services.users.UserService;
import at.nipe.playlegend.playlegendbans.shared.config.Config;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
public class PersistenceModule extends AbstractModule {

  @Provides
  @Inject
  @Singleton
  @SneakyThrows
  public static ConnectionSource provideConnectionSource(Config config) {
    return new JdbcPooledConnectionSource(config.url(), config.user(), config.password());
  }

  @Provides
  @Inject
  @Singleton
  @SneakyThrows
  public static BasicBanDao provideBanDao(ConnectionSource connectionSource) {
    return DaoManager.createDao(connectionSource, Ban.class);
  }

  @Provides
  @Inject
  @Singleton
  @SneakyThrows
  public static BasicUserDao provideUserDao(ConnectionSource connectionSource) {
    return DaoManager.createDao(connectionSource, User.class);
  }

  @Override
  protected void configure() {

    bind(BanDao.class).to(BasicBanDao.class);
    bind(UserDao.class).to(BasicUserDao.class);

    bind(UserService.class).to(BasicUserService.class);
    bind(BanService.class).to(BasicBanService.class);
  }
}
