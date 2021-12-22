package at.nipe.playlegend.playlegendbans.shared.entities;

import java.util.Date;

/**
 * Interface to provide an api between modules
 *
 * @author NoSleep - Nipe
 */
public interface BanEntity {

  Long getId();

  UserEntity getBanned();

  UserEntity getBannedBy();

  String getReason();

  Date getUntil();

  boolean isPermanent();

  boolean isActive();

  Date getCreatedAt();
}
