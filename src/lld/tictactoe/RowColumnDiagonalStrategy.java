package lld.tictactoe;

/**
 * Classic rule: the move at (row, col) wins if it completed its own row, its own
 * column, or — when it sits on one — a diagonal. Works for any N x N board.
 *
 * Only the lines passing through (row, col) are checked, so this is O(n) per
 * move instead of scanning the whole board.
 */
public class RowColumnDiagonalStrategy implements WinningStrategy {

    @Override
    public boolean checkWinner(Board board, Symbol symbol, int row, int col) {

        int size = board.getGridSize();

        return isRowComplete(board, symbol, row, size)
                || isColumnComplete(board, symbol, col, size)
                || (row == col && isMainDiagonalComplete(board, symbol, size))
                || (row + col == size - 1 && isAntiDiagonalComplete(board, symbol, size));
    }

    private boolean isRowComplete(Board board, Symbol symbol, int row, int size) {
        for (int col = 0; col < size; col++) {
            if (board.getCell(row, col) != symbol) {
                return false;
            }
        }
        return true;
    }

    private boolean isColumnComplete(Board board, Symbol symbol, int col, int size) {
        for (int row = 0; row < size; row++) {
            if (board.getCell(row, col) != symbol) {
                return false;
            }
        }
        return true;
    }

    /** Top-left to bottom-right: (0,0), (1,1), (2,2) ... */
    private boolean isMainDiagonalComplete(Board board, Symbol symbol, int size) {
        for (int i = 0; i < size; i++) {
            if (board.getCell(i, i) != symbol) {
                return false;
            }
        }
        return true;
    }

    /** Top-right to bottom-left: (0,n-1), (1,n-2), (2,n-3) ... */
    private boolean isAntiDiagonalComplete(Board board, Symbol symbol, int size) {
        for (int i = 0; i < size; i++) {
            if (board.getCell(i, size - 1 - i) != symbol) {
                return false;
            }
        }
        return true;
    }
}
