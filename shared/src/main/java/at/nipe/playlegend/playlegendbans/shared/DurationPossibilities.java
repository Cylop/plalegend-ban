package at.nipe.playlegend.playlegendbans.shared;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

@Getter
public enum DurationPossibilities {

    SECOND("s", LocalDateTime::plusSeconds),
    MINUTE("m", LocalDateTime::plusMinutes),
    HOUR("h", LocalDateTime::plusHours),
    DAY("d", LocalDateTime::plusDays),
    WEEK("w", LocalDateTime::plusWeeks),
    MONTH("m", LocalDateTime::plusMonths),
    YEAR("y", LocalDateTime::plusYears);

    private final String identifier;
    private final BiFunction<LocalDateTime, Long, LocalDateTime> function;

    DurationPossibilities(String identifier, BiFunction<LocalDateTime, Long, LocalDateTime> function) {
        this.identifier = identifier;
        this.function = function;
    }

    public static List<String> getAllowedIdentifiers() {
        return Arrays.stream(values()).map(DurationPossibilities::getIdentifier).toList();
    }

    public static DurationPossibilities fromIdentifier(String identifier) {
        for(var unit : values())
            if(Objects.equals(unit.getIdentifier(), identifier)) return unit;

        return null;
    }
}
