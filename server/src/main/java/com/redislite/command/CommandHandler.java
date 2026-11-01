package com.redislite.command;

/**
 * The one contract between the network side and the engine side.
 * The network layer parses bytes into a Command and calls execute;
 * the engine runs it against the store and returns a Reply.
 */
public interface CommandHandler {

    Reply execute(Command command);
}
