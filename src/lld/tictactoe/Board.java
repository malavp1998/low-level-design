package lld.tictactoe;

/**
 * The grid, and nothing else. Knows about cells — not about turns, players or
 * who is winning. Owns its array: no one outside can touch it.
 */
public class Board {

    private final Symbol[][] grid;
    private int moveCount = 0;

    public Board(int size) {
        this.grid = new Symbol[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = Symbol.EMPTY;
            }
        }
    }

    public boolean isGridFull() {
        return moveCount == grid.length * grid[0].length;
    }

    public boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < grid.length
                && col >= 0 && col < grid[0].length;
    }

    public boolean isCellEmpty(int row, int col) {
        return grid[row][col] == Symbol.EMPTY;
    }

    /** Caller must validate first: bounds, then empty. */
    public void placeMove(int row, int col, Symbol symbol) {
        moveCount++;
        grid[row][col] = symbol;
    }

    public Symbol getCell(int row, int col) {
        return grid[row][col];
    }

    public int getGridSize() {
        return grid.length;
    }

    public void printGrid() {
        System.out.println("----------------------");
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                System.out.print(" " + grid[row][col].getDisplay() + " ");
            }
            System.out.println();
        }
        System.out.println("----------------------");
    }
}
