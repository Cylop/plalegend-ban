package at.nipe.playlegend.playlegendbans.shared.exceptions;

public class AccountCreationException extends Exception {
  public AccountCreationException(String errorMessage) {
    this(errorMessage, null);
  }

  public AccountCreationException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
