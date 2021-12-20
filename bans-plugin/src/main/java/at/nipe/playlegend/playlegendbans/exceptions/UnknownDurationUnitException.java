package at.nipe.playlegend.playlegendbans.exceptions;

public class UnknownDurationUnitException extends ParserException{

    public UnknownDurationUnitException(String errorMessage) {
        this(errorMessage, null);
    }

    public UnknownDurationUnitException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
