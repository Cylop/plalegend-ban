package at.nipe.playlegend.playlegendbans.shared.exceptions;

/**
 * Exception that gets thrown when an error occurred whilst creating a user account
 *
 * @author NoSleep - Nipe
 */
public class AccountCreationException extends Exception {
  public AccountCreationException(String errorMessage) {
    this(errorMessage, null);
  }

  public AccountCreationException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
