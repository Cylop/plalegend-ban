package at.nipe.playlegend.playlegendbans.shared.exceptions;

/**
 * Exception that gets thrown when unknown duration was provided
 *
 * @author NoSleep - Nipe
 */
public class UnknownDurationUnitException extends ParserException {

  public UnknownDurationUnitException(String errorMessage) {
    this(errorMessage, null);
  }

  public UnknownDurationUnitException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
