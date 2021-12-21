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
import at.nipe.playlegend.playlegendbans.shared.resolution.Component;
import com.google.inject.AbstractModule;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.sql.SQLException;
import java.util.logging.Level;

@Log
public class PersistenceModule extends AbstractModule {

    private final JdbcPooledConnectionSource connectionSource;

    @SneakyThrows
    public PersistenceModule() {
        // url, string, password
        connectionSource = new JdbcPooledConnectionSource("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11459737","sql11459737", "FtuBssV8mz");

        TableUtils.createTableIfNotExists(this.connectionSource, Ban.class);
        TableUtils.createTableIfNotExists(this.connectionSource, User.class);
    }

    @Override
    protected void configure() {
        bind(JdbcPooledConnectionSource.class).toInstance(this.connectionSource);
        try {
            bind(BanDao.class).toInstance(DaoManager.createDao(this.connectionSource, Ban.class));
            bind(UserDao.class).toInstance(DaoManager.createDao(this.connectionSource, User.class));

            bind(UserService.class).to(BasicUserService.class);
            bind(BanService.class).to(BasicBanService.class);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Error whilst trying to instantiate instance of dao's", e);
        }
    }
}
