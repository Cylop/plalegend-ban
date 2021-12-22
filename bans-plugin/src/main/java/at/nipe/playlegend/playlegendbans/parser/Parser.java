package at.nipe.playlegend.playlegendbans.parser;

import at.nipe.playlegend.playlegendbans.shared.exceptions.ParserException;

import javax.annotation.Nonnull;

/**
 * Abstract class to provide some basic functionality for custom parser
 *
 * @param <I> Input
 * @param <O> Output
 *
 * @author NoSleep - Nipe
 */
public abstract class Parser<I, O> {
  public abstract O parse(@Nonnull I input) throws ParserException;
}
