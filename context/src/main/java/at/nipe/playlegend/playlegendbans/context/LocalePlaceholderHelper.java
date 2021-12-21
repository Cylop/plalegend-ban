package at.nipe.playlegend.playlegendbans.context;


import at.nipe.playlegend.playlegendbans.shared.DurationPossibilities;
import at.nipe.playlegend.playlegendbans.shared.entities.BanEntity;
import at.nipe.playlegend.playlegendbans.shared.utils.DateHelper;
import at.nipe.playlegend.playlegendbans.shared.utils.Pair;

import java.util.Arrays;
import java.util.stream.Stream;

public class LocalePlaceholderHelper {

    private LocalePlaceholderHelper() {}

    public static Pair<String, String>[] buildBanContext(BanEntity ban) {
        return new Pair[] {
                new Pair<String, String>("ban_reason", ban.getReason()),
                new Pair<String, String>("ban_bannedBy", ban.getBannedBy().getName()),
                new Pair<String, String>("ban_createdAt", DateHelper.getFormattedDate(ban.getCreatedAt())),
                new Pair<String, String>("ban_until", DateHelper.getFormattedDate(ban.getUntil()))
        };
    }

    public static Pair<String, String>[] buildPlayerContext(String player) {
        return new Pair[] {
                new Pair<String, String>("player", player),
        };
    }

    public static Pair<String, String>[] buildTargetPlayerContext(String target) {
        return new Pair[] {
                new Pair<String, String>("target_player", target),
        };
    }

    public static Pair<String, String>[] buildAllowedUnitsContext() {
        return new Pair[] {
                new Pair<String, String>("target_player", String.join(", ", DurationPossibilities.getAllowedIdentifiers())),
        };
    }


    public static Pair<?, ?>[] combine(Pair<?, ?>[]... pairs) {
       return Arrays.stream(pairs).flatMap(Stream::of).toArray(Pair[]::new);
    }
}
