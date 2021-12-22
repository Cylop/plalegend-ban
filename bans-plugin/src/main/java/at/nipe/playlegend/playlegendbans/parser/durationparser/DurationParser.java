package at.nipe.playlegend.playlegendbans.parser.durationparser;

import at.nipe.playlegend.playlegendbans.parser.Parser;
import at.nipe.playlegend.playlegendbans.shared.DurationPossibilities;
import at.nipe.playlegend.playlegendbans.shared.exceptions.UnknownDurationUnitException;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Parser for parsing string duration to date.
 *
 * <p>For example: <b>4w3d7h25s</b> would be:
 *
 * <p>today + 4 weeks + 3 days + 7 hours + 25 seconds
 *
 * @author NoSleep - NIPE
 */
public class DurationParser extends Parser<String, Date> {

  /**
   * Validates, parses and returns current timestamp + parsed duration.
   *
   * @param input - duration in format 4w7s7d. Order doesn't matter and segments can be present
   *     multiple times
   * @return current timestamp + parsed duration
   * @throws UnknownDurationUnitException if one of the segments is not valid. Like 4z {@link
   *     DurationPossibilities} for all possibilities
   */
  @Override
  public Date parse(@Nonnull String input) throws UnknownDurationUnitException {
    return this.parse(input, new Date());
  }

  /**
   * This method is needed for testing
   *
   * <p>Validates, parses and returns current timestamp + parsed duration.
   *
   * @param input - duration in format 4w7s7d. Order doesn't matter and segments can be present
   *     multiple times
   * @return current timestamp + parsed duration
   * @throws UnknownDurationUnitException if one of the segments is not valid. Like 4z {@link
   *     DurationPossibilities} for all possibilities
   */
  protected Date parse(@Nonnull String input, @Nonnull Date startDate)
      throws UnknownDurationUnitException {
    this.validate(input);

    Date currentDate = new Date();

    LocalDateTime localDateTime =
        currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

    var segments = this.getSegments(input);

    for (Map.Entry<DurationPossibilities, Long> entry : segments.entrySet()) {
      localDateTime = entry.getKey().getFunction().apply(localDateTime, entry.getValue());
    }

    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  /**
   * Validates the input. Input must be not null
   *
   * @throws NullPointerException if Input is null
   * @param input to be validated
   */
  private void validate(String input) {
    Objects.requireNonNull(input);
  }

  /**
   * Transforms the input string duration to a map and transforms the unit to {@link
   * DurationPossibilities} as key and the value as value
   *
   * @param input duration as string
   * @return splitted input string as map
   * @throws UnknownDurationUnitException if the unit is unknown
   */
  private Map<DurationPossibilities, Long> getSegments(@Nonnull String input)
      throws UnknownDurationUnitException {
    final var segments = new HashMap<DurationPossibilities, Long>();

    StringBuilder duration = new StringBuilder();
    for (var currentChar : input.split("")) {
      try {
        var num = Integer.parseInt(currentChar);
        duration.append(num);
      } catch (NumberFormatException ex) {
        var durationUnit = DurationPossibilities.fromIdentifier(currentChar);
        if (durationUnit == null) {
          throw new UnknownDurationUnitException("Wrong unit provided");
        }

        if (segments.containsKey(durationUnit)) {
          segments.put(
              durationUnit, segments.get(durationUnit) + Long.parseLong(duration.toString()));
        } else {
          segments.put(durationUnit, Long.parseLong(duration.toString()));
        }

        duration = new StringBuilder();
      }
    }

    return segments;
  }
}
