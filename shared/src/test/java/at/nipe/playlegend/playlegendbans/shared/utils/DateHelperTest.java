package at.nipe.playlegend.playlegendbans.shared.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelperTest {

  private SimpleDateFormat simpleDateFormat;

  @BeforeEach
  public void before() {
    simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
  }

  @Test
  public void getFormattedDateTest() {
    Date currentDate = new Date();

    var actualResult = DateHelper.getFormattedDate(currentDate, simpleDateFormat);

    var expected = this.simpleDateFormat.format(currentDate);

    Assertions.assertEquals(expected, actualResult, "Date received in wrong format");
  }
}
