package at.nipe.playlegend.playlegendbans.dao;

import at.nipe.playlegend.playlegendbans.entities.Ban;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BasicBanDao extends BaseDaoImpl<Ban, Long> implements BanDao {
  public BasicBanDao(ConnectionSource connectionSource) throws SQLException {
    super(connectionSource, Ban.class);
  }

  @Override
  public List<Ban> findAllForUser(UUID banned) throws SQLException {
    return this.query(this.queryBuilder().where().eq("banned_id", banned).prepare());
  }

  @Override
  public List<Ban> findAllByUser(UUID uuid) throws SQLException {
    return this.query(this.queryBuilder().where().eq("bannedBy_id", uuid).prepare());
  }

  @Override
  public boolean unban(UUID user) throws SQLException {
    var builder = updateBuilder();
    builder.updateColumnValue("active", "false");
    builder.where().eq("banned_id", "user");
    return builder.update() > 0;
  }

  @Override
  public boolean isBanned(UUID user) throws SQLException {
    return this.getLongestBan(user).isEmpty();
  }

  @Override
  public Optional<Ban> getLongestBan(UUID user) throws SQLException {
    var ban =
        this.queryForFirst(
            this.queryBuilder()
                .limit(1L)
                .orderBy("createdAt", true)
                .where()
                .eq("banned_id", user)
                .and()
                .eq("active", 1)
                .and()
                .gt("until", new Date())
                .prepare());
    return ban == null ? Optional.empty() : Optional.of(ban);
  }
}
