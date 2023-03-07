
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nhath
 */
public class TicTacToe implements Serializable {

    private String player1;
    private String player2;

    private int activePlayer;
    private int gameState;
    private char[][] board;

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public void checkwin() {
        // Check for rows and columns
        for (int index = 0; index < 3; index++) {
            char symbolRow = board[index][0];
            char symbolCol = board[0][index];

            if ((int) symbolRow != 0 && (int) board[index][1] != 0 && (int) board[index][2] != 0) {
                if (board[index][1] == symbolRow && board[index][2] == symbolRow) {
                    setGameState(4);
                    break;
                }
            }

            if ((int) symbolCol != 0 && (int) board[1][index] != 0 && (int) board[2][index] != 0) {
                if (board[1][index] == symbolCol && board[2][index] == symbolCol) {
                    setGameState(4);
                    break;
                }
            }

        }

        // X - -
        // - X -
        // - - X
        if ((int) board[0][0] != 0 && (int) board[1][1] != 0 && (int) board[2][2] != 0) {
            if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
                setGameState(4);
            }
        }

        // - - X
        // - X -
        // X - -
        if ((int) board[2][0] != 0 && (int) board[1][1] != 0 && (int) board[0][2] != 0) {
            if (board[2][0] == board[1][1] && board[1][1] == board[0][2]) {
                setGameState(4);
            }
        }
    }

    public void updateBoard(int move) {
        if (move >= 9 || move < 0) {
            return;
        }

        char symbol;
        if (activePlayer == 1) {
            symbol = 'X';
        } else {
            symbol = 'O';
        }

        board[move / 3][move % 3] = symbol;
        checkwin();
    }
}
