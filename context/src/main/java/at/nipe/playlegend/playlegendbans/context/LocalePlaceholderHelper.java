package at.nipe.playlegend.playlegendbans.context;

import at.nipe.playlegend.playlegendbans.shared.DurationPossibilities;
import at.nipe.playlegend.playlegendbans.shared.entities.BanEntity;
import at.nipe.playlegend.playlegendbans.shared.utils.DateHelper;
import at.nipe.playlegend.playlegendbans.shared.utils.Pair;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.stream.Stream;

@UtilityClass
public class LocalePlaceholderHelper {

  public static Pair<String, String>[] buildBanContext(BanEntity ban) {
    return new Pair[] {
      new Pair<>("ban_reason", ban.getReason()),
      new Pair<>("ban_bannedBy", ban.getBannedBy().getName()),
      new Pair<>("ban_createdAt", DateHelper.getFormattedDate(ban.getCreatedAt())),
      new Pair<>("ban_until", DateHelper.getFormattedDate(ban.getUntil()))
    };
  }

  public static Pair<String, String>[] buildPlayerContext(String player) {
    return new Pair[] {
      new Pair<>("player", player),
    };
  }

  public static Pair<String, String>[] buildTargetPlayerContext(String target) {
    return new Pair[] {
      new Pair<>("target_player", target),
    };
  }

  public static Pair<String, String>[] buildAllowedUnitsContext() {
    return new Pair[] {
      new Pair<>("target_player", String.join(", ", DurationPossibilities.getAllowedIdentifiers())),
    };
  }

  public static Pair[] combine(Pair<String, String>[]... pairs) {
    return Arrays.stream(pairs).flatMap(Stream::of).toArray(Pair[]::new);
  }
}
