package lld.tictactoe;

public interface WinningStrategy {

    boolean checkWinner(Board board, Symbol symbol, int row, int col);
}

