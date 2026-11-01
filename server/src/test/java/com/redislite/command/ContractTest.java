package com.redislite.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ContractTest {

    @Test
    void commandNameIsUpperCased() {
        Command command = new Command("set", List.of("a", "1"));
        assertEquals("SET", command.name());
    }

    @Test
    void commandCopiesItsArguments() {
        List<String> args = new ArrayList<>(List.of("a", "1"));
        Command command = new Command("SET", args);
        args.add("extra");
        assertEquals(List.of("a", "1"), command.args());
    }

    @Test
    void commandArgumentsCannotBeChanged() {
        Command command = new Command("GET", List.of("a"));
        assertThrows(UnsupportedOperationException.class, () -> command.args().add("b"));
    }

    @Test
    void okIsTheSimpleStringOk() {
        assertEquals(Reply.simple("OK"), Reply.ok());
    }

    @Test
    void repliesOfDifferentTypesAreNotEqual() {
        assertNotEquals(Reply.bulk("1"), Reply.simple("1"));
        assertNotEquals(Reply.integer(1), Reply.bulk("1"));
    }

    @Test
    void nullBulkRepliesAreEqual() {
        assertEquals(Reply.nullBulk(), Reply.nullBulk());
    }

    @Test
    void arrayRepliesCompareByContents() {
        Reply a = Reply.array(List.of(Reply.bulk("x"), Reply.integer(2)));
        Reply b = Reply.array(List.of(Reply.bulk("x"), Reply.integer(2)));
        assertEquals(a, b);
    }
}
