package at.nipe.playlegend.playlegendbans.entities;

import at.nipe.playlegend.playlegendbans.dao.BasicBanDao;
import at.nipe.playlegend.playlegendbans.shared.entities.BanEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.util.Date;

/**
 * Java representative Database object for a ban
 *
 * @author NoSleep - Nipe
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "bans", daoClass = BasicBanDao.class)
public class Ban implements BanEntity {

  @DatabaseField(generatedId = true)
  private Long id;

  @DatabaseField(
      canBeNull = false,
      foreignColumnName = "id",
      foreign = true,
      foreignAutoRefresh = true,
      foreignAutoCreate = true)
  private User banned;

  @DatabaseField(
      canBeNull = false,
      foreignColumnName = "id",
      foreign = true,
      foreignAutoRefresh = true,
      foreignAutoCreate = true)
  private User bannedBy;

  @DatabaseField(canBeNull = false)
  private String reason;

  @DatabaseField(canBeNull = false)
  private Date until;

  @DatabaseField private boolean permanent;

  @DatabaseField private boolean active;

  @Setter(AccessLevel.NONE)
  @Builder.Default
  @DatabaseField
  private Date createdAt = new Date();

  @Override
  public String toString() {
    return getClass().getSimpleName()
        + "("
        + "id = "
        + id
        + ", "
        + "banned = "
        + banned
        + ", "
        + "bannedBy = "
        + bannedBy
        + ", "
        + "reason = "
        + reason
        + ", "
        + "until = "
        + until
        + ", "
        + "permanent = "
        + permanent
        + ", "
        + "active = "
        + active
        + ", "
        + "createdAt = "
        + createdAt
        + ")";
  }
}
