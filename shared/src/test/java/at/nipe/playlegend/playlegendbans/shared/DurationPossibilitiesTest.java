package at.nipe.playlegend.playlegendbans.shared;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class DurationPossibilitiesTest {

  private String[] allowedUnits;

  @BeforeEach
  public void initialize() {
    this.allowedUnits = new String[] {"y", "m", "w", "d", "h", "min", "s"};
  }

  @Test
  public void getDurationByIdentifierTest() {
    Arrays.stream(this.allowedUnits)
        .forEach(
            unit -> {
              Assertions.assertNotNull(
                  DurationPossibilities.fromIdentifier(unit), "Unknown unit found");
            });
  }

  @Test
  public void getDurationUnits() {
    Assertions.assertArrayEquals(
        this.allowedUnits,
        DurationPossibilities.getAllowedIdentifiers().toArray(String[]::new),
        "Wrong unit provided");
  }
}
