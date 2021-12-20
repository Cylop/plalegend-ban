package at.nipe.playlegend.playlegendbans.exceptions;

public class ParserException extends Exception {
    public ParserException(String errorMessage) {
        this(errorMessage, null);
    }

    public ParserException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
