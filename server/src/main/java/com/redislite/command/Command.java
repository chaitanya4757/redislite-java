package com.redislite.command;

import java.util.List;

/**
 * One parsed client command. For "SET a 1" the name is "SET" and args are ["a", "1"].
 * The name is stored in upper case so handlers can match it without worrying about case.
 */
public final class Command {

    private final String name;
    private final List<String> args;

    public Command(String name, List<String> args) {
        this.name = name.toUpperCase();
        this.args = List.copyOf(args);
    }

    public String name() {
        return name;
    }

    public List<String> args() {
        return args;
    }

    @Override
    public String toString() {
        return name + " " + args;
    }
}
