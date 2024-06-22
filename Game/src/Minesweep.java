import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Minesweep implements MouseListener {
    JFrame frame;
    Btn[][] board = new Btn[10][10];
    public Minesweep() {
        frame = new JFrame("MineSweep");
        frame.setSize(800,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(10,10));

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                Btn btn = new Btn(row,col);
                frame.add(btn);
                board[row][col] = btn;
                btn.addMouseListener(this);
            }
        }

        generateBomb();
        updateCount();
//      print();

        frame.setVisible(true);
    }

    public void generateBomb() {
        int i = 0;
        while (i < 10) {
            int randRow = (int) (Math.random() * board.length);
            int randCol = (int) (Math.random() * board[0].length);
            while (board[randRow][randCol].isMine()) {
                randRow = (int) (Math.random() * board.length);
                randCol = (int) (Math.random() * board[0].length);
            }
            board[randRow][randCol].setMine(true);
            i++;
        }
    }
    public void print() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col].isMine()) {
                    ImageIcon icon = new ImageIcon(getClass().getResource("bombhontai.png"));
                    board[row][col].setIcon(icon);
                }
                else {
                    board[row][col].setText(board[row][col].getCount()+"");
                }
            }
        }
    }
    public void updateCount() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if(board[row][col].isMine()) {
                    counting(row,col);
                }
            }
        }
    }
    public void counting(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                try {
                    int value = board[i][j].getCount();
                    board[i][j].setCount(++value);
                }catch (Exception e) {

                }
            }
        }
    }
    public void open(int r, int c) {
        if(r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c].getText().length() > 0 || board[r][c].isEnabled() == false) {
            return;
        }else if(board[r][c].getCount() != 0) {
            board[r][c].setText(board[r][c].getCount()+"");
            board[r][c].setEnabled(false);
        }else {
            board[r][c].setEnabled(false);
            open(r+1,c);
            open(r,c+1);
            open(r-1,c);
            open(r,c-1);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        Btn btn = (Btn) e.getComponent();
        if(e.getButton() == 1) {
            if(btn.isMine()) {
                JOptionPane.showMessageDialog(frame, "Mayına bastınız. Oyun bitti !");
                print();
                frame.getDefaultCloseOperation();
            }else {
                open(btn.getRow(),btn.getCol());
            }
        }
        else if(e.getButton() == 3) {
            if(!btn.isFlag()) {
                ImageIcon icon2 = new ImageIcon(getClass().getResource("flag_icon.png"));
                btn.setIcon(icon2);
                btn.setFlag(true);
            }
            else {
                btn.setIcon(null);
                btn.setFlag(false);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
