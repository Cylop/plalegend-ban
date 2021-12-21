package at.nipe.playlegend.playlegendbans.shared.entities;

import java.util.Date;

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
