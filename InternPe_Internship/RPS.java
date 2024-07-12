import java.util.Scanner;

public class  RPS{}
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final char EMPTY = '-';
    private static char[][] board = new char[ROWS][COLS];
    private static char currentPlayer = 'X';

    public static void main(String[] args) {
        initializeBoard();
        displayBoard();

        Scanner scanner = new Scanner(System.in);
        boolean gameFinished = false;

        while (!gameFinished) {
            System.out.println("Player " + currentPlayer + "'s turn");
            int col = getColumn(scanner);
            dropPiece(col);
            displayBoard();
            if (checkWin()) {
                System.out.println("Player " + currentPlayer + " wins!");
                gameFinished = true;
            } else if (isBoardFull()) {
                System.out.println("It's a draw!");
                gameFinished = true;
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
        scanner.close();
    }

    private static void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    private static void displayBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        for (int j = 0; j < COLS; j++) {
            System.out.print("- ");
        }
        System.out.println();
    }

    private static int getColumn(Scanner scanner) {
        int col;
        while (true) {
            System.out.print("Enter column (1-" + COLS + "): ");
            col = scanner.nextInt();
            if (col >= 1 && col <= COLS && board[0][col - 1] == EMPTY) {
                break;
            } else {
                System.out.println("Invalid column. Please try again.");
            }
        }
        return col - 1;
    }

    private static void dropPiece(int col) {
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][col] == EMPTY) {
                board[i][col] = currentPlayer;
                break;
            }
        }
    }

    private static boolean checkWin() {
        // Check horizontally
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS - 3; j++) {
                if (board[i][j] == currentPlayer &&
                        board[i][j + 1] == currentPlayer &&
                        board[i][j + 2] == currentPlayer &&
                        board[i][j + 3] == currentPlayer) {
                    return true;
                }
            }
        }
        // Check vertically
        for (int i = 0; i < ROWS - 3; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == currentPlayer &&
                        board[i + 1][j] == currentPlayer &&
                        board[i + 2][j] == currentPlayer &&
                        board[i + 3][j] == currentPlayer) {
                    return true;
                }
            }
        }
        // Check diagonally (left to right)
        for (int i = 0; i < ROWS - 3; i++) {
            for (int j = 0; j < COLS - 3; j++) {
                if (board[i][j] == currentPlayer &&
                        board[i + 1][j + 1] == currentPlayer &&
                        board[i + 2][j + 2] == currentPlayer &&
                        board[i + 3][j + 3] == currentPlayer) {
                    return true;
                }
            }
        }
        // Check diagonally (right to left)
        for (int i = 0; i < ROWS - 3; i++) {
            for (int j = 3; j < COLS; j++) {
                if (board[i][j] == currentPlayer &&
                        board[i + 1][j - 1] == currentPlayer &&
                        board[i + 2][j - 2] == currentPlayer &&
                        board[i + 3][j - 3] == currentPlayer) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isBoardFull() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}
