package src;

import javax.swing.JButton;

public class TicTacToeButton extends JButton {
    private int row;
    private int col;

    public TicTacToeButton(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}