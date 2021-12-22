package at.nipe.playlegend.playlegendbans.shared.entities;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * Interface to provide an api between modules
 *
 * @author NoSleep - Nipe
 */
public interface UserEntity {

  UUID getId();

  String getName();

  Date getCreatedAt();

  long getVersion();

  Collection<? extends BanEntity> getBans();
}
