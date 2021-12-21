package at.nipe.playlegend.playlegendbans.shared.entities;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public interface UserEntity {

  UUID getId();

  String getName();

  Date getCreatedAt();

  long getVersion();

  Collection<? extends BanEntity> getBans();
}
