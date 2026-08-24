package lld.tictactoe;

import java.util.List;

/**
 * Runs the game: whose turn it is, and where the game stands.
 *
 * Deliberately does NOT know:
 *   - how the grid is stored        -> Board's job
 *   - how a win is decided          -> WinningStrategy's job
 *   - where the moves come from     -> the caller's job (console, GUI, test)
 *
 * If the word "getCell" ever appears in this class, something has leaked.
 */
public class Game {

    private final Board board;
    private final List<Player> players;
    private final WinningStrategy winningStrategy;

    private int currentPlayerIndex = 0;
    private GameStatus status = GameStatus.IN_PROGRESS;
    private Player winner;

    public Game(int boardSize, List<Player> players, WinningStrategy winningStrategy) {
        if (players == null || players.size() < 2) {
            throw new IllegalArgumentException("need at least 2 players");
        }
        if (winningStrategy == null) {
            throw new IllegalArgumentException("need a winning strategy");
        }
        this.board = new Board(boardSize);
        this.players = List.copyOf(players);
        this.winningStrategy = winningStrategy;
    }

    /**
     * Play one move for the current player.
     *
     * @return false if the move was rejected (out of bounds, cell taken, or the
     *         game is already over) — in that case the turn does NOT change.
     */
    public boolean makeMove(int row, int col) {

        if (status != GameStatus.IN_PROGRESS) {
            return false;
        }
        if (!board.isWithinBounds(row, col) || !board.isCellEmpty(row, col)) {
            return false;
        }

        Player player = currentPlayer();
        board.placeMove(row, col, player.getSymbol());

        if (winningStrategy.checkWinner(board, player.getSymbol(), row, col)) {
            winner = player;
            status = GameStatus.WIN;
        } else if (board.isGridFull()) {
            status = GameStatus.DRAW;
        } else {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
        return true;
    }

    public Player currentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public GameStatus getStatus() {
        return status;
    }

    /** null unless the status is WIN. */
    public Player getWinner() {
        return winner;
    }

    public Board getBoard() {
        return board;
    }
}
