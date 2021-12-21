package at.nipe.playlegend.playlegendbans.dao;

import at.nipe.playlegend.playlegendbans.entities.Ban;
import at.nipe.playlegend.playlegendbans.entities.User;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class BasicUserDao extends BaseDaoImpl<User, UUID> implements UserDao {

    public BasicUserDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, User.class);
    }

    @Override
    public Optional<User> findUserByName(String name) throws SQLException {
        User user = super.queryForFirst(super.queryBuilder().where().eq("name", name).prepare());
        return user != null ? Optional.of(user) : Optional.empty();
    }
}
