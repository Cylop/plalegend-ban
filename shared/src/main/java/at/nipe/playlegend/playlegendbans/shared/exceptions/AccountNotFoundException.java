package at.nipe.playlegend.playlegendbans.shared.exceptions;

public class AccountNotFoundException extends Exception {

  public AccountNotFoundException(String errorMessage) {
    this(errorMessage, null);
  }

  public AccountNotFoundException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
