package at.nipe.playlegend.playlegendbans.shared;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

@Getter
public enum DurationPossibilities {
  YEAR("y", LocalDateTime::plusYears),
  MONTH("M", LocalDateTime::plusMonths),
  WEEK("w", LocalDateTime::plusWeeks),
  DAY("d", LocalDateTime::plusDays),
  HOUR("h", LocalDateTime::plusHours),
  MINUTE("m", LocalDateTime::plusMinutes),
  SECOND("s", LocalDateTime::plusSeconds);

  private final String identifier;
  private final BiFunction<LocalDateTime, Long, LocalDateTime> function;

  DurationPossibilities(
      String identifier, BiFunction<LocalDateTime, Long, LocalDateTime> function) {
    this.identifier = identifier;
    this.function = function;
  }

  public static List<String> getAllowedIdentifiers() {
    return Arrays.stream(values()).map(DurationPossibilities::getIdentifier).toList();
  }

  public static DurationPossibilities fromIdentifier(String identifier) {
    for (var unit : values()) if (Objects.equals(unit.getIdentifier(), identifier)) return unit;

    return null;
  }
}
