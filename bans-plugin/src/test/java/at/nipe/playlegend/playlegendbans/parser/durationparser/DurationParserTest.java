package at.nipe.playlegend.playlegendbans.parser.durationparser;

import at.nipe.playlegend.playlegendbans.shared.exceptions.UnknownDurationUnitException;
import org.apache.commons.lang.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DurationParserTest {

  private DurationParser durationParser;

  @BeforeEach
  public void prepare() {
    this.durationParser = new DurationParser();
  }

  @Test
  public void testParser_Ok() throws UnknownDurationUnitException {
    var durationString = "1y3M2w7h10s";

    var currentDate = new Date();

    var expectedLocalDate =
        currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    expectedLocalDate = expectedLocalDate.plusYears(1);
    expectedLocalDate = expectedLocalDate.plusMonths(3);
    expectedLocalDate = expectedLocalDate.plusWeeks(2);
    expectedLocalDate = expectedLocalDate.plusHours(7);
    expectedLocalDate = expectedLocalDate.plusSeconds(10);

    var expectedDate = Date.from(expectedLocalDate.atZone(ZoneId.systemDefault()).toInstant());
    var actualParsedDate = this.durationParser.parse(durationString, currentDate);

    // DateUtils.truncate for trimming date because of mismatches in nano or milliseconds
    Assertions.assertEquals(
        DateUtils.truncate(expectedDate, Calendar.SECOND),
        DateUtils.truncate(actualParsedDate, Calendar.SECOND),
        "DateParser wrong date returned");
  }

  @Test
  public void testParser_expectUnknownDurationUnitException() {
    var durationString = "1z3M2w7h10s";

    Exception exception =
        Assertions.assertThrows(
            UnknownDurationUnitException.class,
            () -> {
              this.durationParser.parse(durationString);
            });

    var expectedMessage = "Wrong unit provided";
    var actualMessage = exception.getMessage();

    Assertions.assertTrue(
        actualMessage.contains(expectedMessage),
        "Exception message does not contain expected Message");
  }
}
