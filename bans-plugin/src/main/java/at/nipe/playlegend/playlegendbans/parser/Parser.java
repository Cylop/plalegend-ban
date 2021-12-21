package at.nipe.playlegend.playlegendbans.parser;

import at.nipe.playlegend.playlegendbans.shared.exceptions.ParserException;

public abstract class Parser<I, O> {
    public abstract O parse(I input) throws ParserException;
}
