package at.nipe.playlegend.playlegendbans.shared.exceptions;

import lombok.Getter;

/**
 * Exception that gets thrown when no account for the specified player was found
 *
 * @author NoSleep - Nipe
 */
@Getter
public class AccountNotFoundException extends Exception {

  private final String playerName;

  public AccountNotFoundException(String errorMessage, String playerName) {
    this(errorMessage, null, playerName);
  }

  public AccountNotFoundException(String errorMessage, Throwable err, String playerName) {
    super(errorMessage, err);
    this.playerName = playerName;
  }
}
