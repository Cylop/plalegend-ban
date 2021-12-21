package at.nipe.playlegend.playlegendbans.parser;

import at.nipe.playlegend.playlegendbans.shared.exceptions.ParserException;

/**
 * Abstract class to provide some basic functionality for custom parser
 *
 * @param <I> Input
 * @param <O> Output
 */
public abstract class Parser<I, O> {
  public abstract O parse(I input) throws ParserException;
}
