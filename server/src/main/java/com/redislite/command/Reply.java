package com.redislite.command;

import java.util.List;
import java.util.Objects;

/**
 * What the engine sends back for a command. One class covers all the RESP reply types;
 * use the static factory methods instead of the constructor.
 */
public final class Reply {

    public enum Type { SIMPLE_STRING, ERROR, INTEGER, BULK_STRING, NULL_BULK, ARRAY }

    private final Type type;
    private final String text;
    private final long number;
    private final List<Reply> items;

    private Reply(Type type, String text, long number, List<Reply> items) {
        this.type = type;
        this.text = text;
        this.number = number;
        this.items = items;
    }

    public static Reply ok() {
        return simple("OK");
    }

    public static Reply simple(String text) {
        return new Reply(Type.SIMPLE_STRING, text, 0, null);
    }

    public static Reply error(String message) {
        return new Reply(Type.ERROR, message, 0, null);
    }

    public static Reply integer(long value) {
        return new Reply(Type.INTEGER, null, value, null);
    }

    public static Reply bulk(String text) {
        return new Reply(Type.BULK_STRING, text, 0, null);
    }

    public static Reply nullBulk() {
        return new Reply(Type.NULL_BULK, null, 0, null);
    }

    public static Reply array(List<Reply> items) {
        return new Reply(Type.ARRAY, null, 0, List.copyOf(items));
    }

    public Type type() {
        return type;
    }

    /** Text of a simple string, error or bulk string reply. */
    public String text() {
        return text;
    }

    /** Value of an integer reply. */
    public long number() {
        return number;
    }

    /** Elements of an array reply. */
    public List<Reply> items() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reply other)) return false;
        return type == other.type
                && number == other.number
                && Objects.equals(text, other.text)
                && Objects.equals(items, other.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, text, number, items);
    }

    @Override
    public String toString() {
        return switch (type) {
            case INTEGER -> "Reply(" + type + " " + number + ")";
            case ARRAY -> "Reply(" + type + " " + items + ")";
            case NULL_BULK -> "Reply(" + type + ")";
            default -> "Reply(" + type + " " + text + ")";
        };
    }
}
