package lld.tictactoe;

import java.util.List;
import java.util.Scanner;

/**
 * Driver. Its only jobs: build the game, read input, print output.
 * All the rules live in Game / Board / WinningStrategy.
 *
 *   ./run.sh tictactoe
 */
public class TicTacToeMain {

    private static final int BOARD_SIZE = 3;

    public static void main(String[] args) {

        Game game = new Game(
                BOARD_SIZE,
                List.of(new Player("Piyyush", Symbol.X),
                        new Player("Kanha", Symbol.O)),
                new RowColumnDiagonalStrategy());

        try (Scanner scanner = new Scanner(System.in)) {

            game.getBoard().printGrid();

            while (game.getStatus() == GameStatus.IN_PROGRESS) {

                System.out.println(game.currentPlayer().getName()
                        + ": enter row and col (0-" + (BOARD_SIZE - 1) + ")");

                Integer row = readInt(scanner);
                Integer col = readInt(scanner);
                if (row == null || col == null) {
                    System.out.println("No more input, stopping.");
                    return;
                }

                if (!game.makeMove(row, col)) {
                    System.out.println("Invalid move — off the board or already taken. Try again.");
                    continue;
                }
                game.getBoard().printGrid();
            }

            if (game.getStatus() == GameStatus.WIN) {
                System.out.println("Winner is " + game.getWinner().getName());
            } else {
                System.out.println("Match is a draw.");
            }
        }
    }

    /** Reads one int, skipping junk. Returns null when the input runs out. */
    private static Integer readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            if (!scanner.hasNext()) {
                return null;
            }
            scanner.next();
            System.out.println("Numbers only, please.");
        }
        return scanner.nextInt();
    }
}
