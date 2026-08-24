package lld.tictactoe;

public class Player {

    private String name;
    private Symbol symbol;

    Player(String name, Symbol symbol)
    {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName()
    {
        return name;
    }
    public Symbol getSymbol()
    {
        return symbol;
    }

}
