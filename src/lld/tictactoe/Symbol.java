package lld.tictactoe;

public enum Symbol {

    X('X'),
    O('O'),
    EMPTY('.');

    private final char display;

    Symbol(char display) {
        this.display = display;
    }

    public char getDisplay() {
        return display;
    }
}
