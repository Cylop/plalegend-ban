package at.nipe.playlegend.playlegendbans.context;

import at.nipe.playlegend.playlegendbans.shared.DurationPossibilities;
import at.nipe.playlegend.playlegendbans.shared.entities.BanEntity;
import at.nipe.playlegend.playlegendbans.shared.utils.DateHelper;
import at.nipe.playlegend.playlegendbans.shared.utils.Pair;
import at.nipe.playlegend.playlegendbans.shared.utils.ServerUtil;
import lombok.experimental.UtilityClass;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class LocalePlaceholderHelper {

  public static List<Pair<String, String>> buildBanContext(BanEntity ban, CommandSender sender) {
    return combine(buildPlayerContext(sender), buildBanContext(ban));
  }

  public static List<Pair<String, String>> buildBanContext(BanEntity ban) {
    return List.of(
        new Pair<>("ban_reason", ban.getReason()),
        new Pair<>("ban_bannedBy", ban.getBannedBy().getName()),
        new Pair<>("ban_createdAt", DateHelper.getFormattedDate(ban.getCreatedAt())),
        new Pair<>("ban_until", DateHelper.getFormattedDate(ban.getUntil())));
  }

  public static List<Pair<String, String>> buildPlayerContext(CommandSender sender) {
    return List.of(
        new Pair<>("player", sender.getName()),
        new Pair<>(
            "player_uuid",
            sender instanceof Player
                ? ((Player) sender).getUniqueId().toString()
                : ServerUtil.SERVER_UUID.toString()));
  }

  public static List<Pair<String, String>> buildTargetPlayerContext(String target) {
    return List.of(new Pair<>("target_player", target));
  }

  public static List<Pair<String, String>> buildAllowedUnitsContext() {
    return List.of(
        new Pair<>(
            "allowed_units", String.join(", ", DurationPossibilities.getAllowedIdentifiers())));
  }

  @SafeVarargs
  public static List<Pair<String, String>> combine(List<Pair<String, String>>... pairs) {
    return Arrays.stream(pairs).flatMap(Collection::stream).collect(Collectors.toList());
  }
}
