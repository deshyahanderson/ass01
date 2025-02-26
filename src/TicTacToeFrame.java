package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {

    private JPanel mainPnl;
    private JPanel boardPnl;
    private JPanel quitPnl;
    private String[][] board;
    private TicTacToeButton[][] tiles;
    private JButton quitBtn;
    private final int ROW = 3;
    private final int COL = 3;
    private String currentPlayer = "X";
    private int moveCnt = 0;
    private final int MOVES_FOR_WIN = 5;
    private final int MOVES_FOR_TIE = 9;
    private TileListener tileListener;

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe Game");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPnl = new JPanel(new BorderLayout()); // Use BorderLayout for main panel
        add(mainPnl);

        createBoardPanel();
        mainPnl.add(boardPnl, BorderLayout.CENTER);

        createQuitPanel();
        mainPnl.add(quitPnl, BorderLayout.SOUTH);

        setVisible(true);
        clearBoard();
    }

    private void createBoardPanel() {
        boardPnl = new JPanel(new GridLayout(ROW, COL));
        board = new String[ROW][COL];
        tiles = new TicTacToeButton[ROW][COL];
        tileListener = new TileListener();

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                tiles[row][col] = new TicTacToeButton(row, col);
                tiles[row][col].setText(" ");
                tiles[row][col].addActionListener(tileListener);
                boardPnl.add(tiles[row][col]);
            }
        }
    }

    private void createQuitPanel() {
        quitPnl = new JPanel();
        quitBtn = new JButton("Quit");
        quitBtn.addActionListener((ActionEvent ae) -> System.exit(0));
        quitPnl.add(quitBtn);
    }

    private class TileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicTacToeButton tile = (TicTacToeButton) e.getSource();
            int row = tile.getRow();
            int col = tile.getCol();

            if (tile.getText().equals(" ")) {
                tile.setText(currentPlayer);
                tile.setEnabled(false);
                board[row][col] = currentPlayer;
                moveCnt++;

                if (moveCnt >= MOVES_FOR_WIN) {
                    if (isWin(currentPlayer)) {
                        display();
                        JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                        resetGame();
                        return;
                    }
                }

                if (moveCnt == MOVES_FOR_TIE) {
                    if (isTie()) {
                        display();
                        JOptionPane.showMessageDialog(null, "It's a Tie!");
                        resetGame();
                        return;
                    }
                }

                currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
            } else {
                JOptionPane.showMessageDialog(null, "Tile already taken!");
            }
        }
    }

    private void clearBoard() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                board[row][col] = " ";
            }
        }
    }

    private void display() {
        for (int row = 0; row < ROW; row++) {
            System.out.print("| ");
            for (int col = 0; col < COL; col++) {
                System.out.print(board[row][col] + " | ");
            }
            System.out.println();
        }
    }

    private boolean isWin(String player) {
        for (int i = 0; i < ROW; i++) {
            if (board[i][0].equals(player) && board[i][1].equals(player) && board[i][2].equals(player)) return true;
            if (board[0][i].equals(player) && board[1][i].equals(player) && board[2][i].equals(player)) return true;
        }
        if (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) return true;
        if (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player)) return true;
        return false;
    }

    private boolean isTie() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (board[row][col].equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        int playAgain = JOptionPane.showConfirmDialog(null, "Play another game?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (playAgain == JOptionPane.YES_OPTION) {
            currentPlayer = "X";
            moveCnt = 0;
            clearBoard();
            for (int row = 0; row < ROW; row++) {
                for (int col = 0; col < COL; col++) {
                    tiles[row][col].setText(" ");
                    tiles[row][col].setEnabled(true);
                }
            }
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeFrame());
    }
}