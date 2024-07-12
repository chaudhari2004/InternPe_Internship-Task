import java.util.Scanner;

public class TicTacToe1 {
    private static char[][] board = new char[3][3];
    private static char currentPlayer = 'X';
    private static boolean gameEnded = false;

    public static void main(String[] args) {
        initializeBoard();
        printBoard();
        playGame();
    }

    private static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    private static void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    private static void playGame() {
        Scanner scanner = new Scanner(System.in);

        while (!gameEnded) {
            if (currentPlayer == 'X') {
                System.out.println("Player X, enter your move (row[1-3] column[1-3]): ");
                int row = scanner.nextInt() - 1;
                int col = scanner.nextInt() - 1;
                if (isValidMove(row, col)) {
                    board[row][col] = currentPlayer;
                    printBoard();
                    if (checkWin()) {
                        gameEnded = true;
                        System.out.println("Player X wins!");
                    } else if (checkTie()) {
                        gameEnded = true;
                        System.out.println("It's a tie!");
                    }
                    currentPlayer = 'O';
                } else {
                    System.out.println("Invalid move, try again.");
                }
            } else {
                int[] move = minimax(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                board[move[0]][move[1]] = currentPlayer;
                System.out.println("Player O chose row: " + (move[0] + 1) + " column: " + (move[1] + 1));
                printBoard();
                if (checkWin()) {
                    gameEnded = true;
                    System.out.println("Player O wins!");
                } else if (checkTie()) {
                    gameEnded = true;
                    System.out.println("It's a tie!");
                }
                currentPlayer = 'X';
            }
        }
        scanner.close();
    }

    private static boolean isValidMove(int row, int col) {
        return (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '-');
    }

    private static boolean checkWin() {
        return (checkRows() || checkColumns() || checkDiagonals());
    }

    private static boolean checkRows() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkColumns() {
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != '-' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkDiagonals() {
        return ((board[0][0] != '-' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
                (board[0][2] != '-' && board[0][2] == board[1][1] && board[1][1] == board[2][0]));
    }

    private static boolean checkTie() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private static int[] minimax(char[][] board, int depth, int alpha, int beta) {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = (currentPlayer == 'X') ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        if (checkWin() || checkTie() || depth == 3) {
            bestScore = evaluate();
        } else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = currentPlayer;
                        if (currentPlayer == 'X') {
                            int score = minimax(board, depth + 1, alpha, beta)[2];
                            if (score > bestScore) {
                                bestScore = score;
                                bestMove[0] = i;
                                bestMove[1] = j;
                            }
                            alpha = Math.max(alpha, bestScore);
                        } else {
                            int score = minimax(board, depth + 1, alpha, beta)[2];
                            if (score < bestScore) {
                                bestScore = score;
                                bestMove[0] = i;
                                bestMove[1] = j;
                            }
                            beta = Math.min(beta, bestScore);
                        }
                        board[i][j] = '-';
                        if (alpha >= beta) break;
                    }
                }
            }
        }
        bestMove[2] = bestScore;
        return bestMove;
    }

    private static int evaluate() {
        if (checkWin() && currentPlayer == 'X') return 10;
        else if (checkWin() && currentPlayer == 'O') return -10;
        else return 0;
    }
}
