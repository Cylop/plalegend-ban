package at.nipe.playlegend.playlegendbans.shared.exceptions;

/**
 * General parser exception that gets thrown when an error occurs whilst parsing some input
 *
 * @author NoSleep - Nipe
 */
public class ParserException extends Exception {
  public ParserException(String errorMessage) {
    this(errorMessage, null);
  }

  public ParserException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
