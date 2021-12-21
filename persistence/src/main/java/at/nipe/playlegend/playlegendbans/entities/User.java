package at.nipe.playlegend.playlegendbans.entities;

import at.nipe.playlegend.playlegendbans.dao.BasicUserDao;
import at.nipe.playlegend.playlegendbans.shared.entities.UserEntity;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "users", daoClass = BasicUserDao.class)
public class User implements UserEntity {
  @DatabaseField(id = true, canBeNull = false, dataType = DataType.UUID)
  private UUID id;

  @DatabaseField(canBeNull = false)
  private String name;

  @Setter(AccessLevel.NONE)
  @Builder.Default
  @DatabaseField(canBeNull = false)
  private Date createdAt = new Date();

  @DatabaseField(canBeNull = false, version = true)
  private long version;

  @ForeignCollectionField(foreignFieldName = "banned", eager = false)
  private ForeignCollection<Ban> bans;

  @Override
  public String toString() {
    return getClass().getSimpleName()
        + "("
        + "id = "
        + id
        + ", "
        + "name = "
        + name
        + ", "
        + "createdAt = "
        + createdAt
        + ", "
        + "version = "
        + version
        + ")";
  }
}
