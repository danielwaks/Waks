
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGame {
    public static void main(String[] args) {
        new PlayerInputPage();
    }
}

class PlayerInputPage extends JFrame {
    JTextField player1Field, player2Field;
    JButton startButton;

    PlayerInputPage() {
        setTitle("Tic-Tac-Toe - Enter Player Names");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Player 1 (X):"));
        player1Field = new JTextField();
        add(player1Field);

        add(new JLabel("Player 2 (O):"));
        player2Field = new JTextField();
        add(player2Field);

        startButton = new JButton("Start Game");
        add(new JLabel());
        add(startButton);

        startButton.addActionListener(e -> {
            String player1 = player1Field.getText().trim();
            String player2 = player2Field.getText().trim();
            if (!player1.isEmpty() && !player2.isEmpty()) {
                dispose();
                new GamePage(player1, player2);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter both player names.");
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}

class GamePage extends JFrame implements ActionListener {
    JButton[] buttons = new JButton[9];
    boolean xTurn = true;
    String player1, player2;

    GamePage(String p1, String p2) {
        player1 = p1;
        player2 = p2;

        setTitle("Tic-Tac-Toe");
        setSize(400, 400);
        setLayout(new GridLayout(3, 3));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
            buttons[i].addActionListener(this);
            add(buttons[i]);
        }

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (!btn.getText().equals("")) return;

        btn.setText(xTurn ? "X" : "O");
        btn.setEnabled(false);

        if (checkWinner()) {
            String winner = xTurn ? player1 : player2;
            dispose();
            new WinPage(winner);
        } else if (isDraw()) {
            dispose();
            new WinPage("It's a Draw!");
        } else {
            xTurn = !xTurn;
        }
    }

    boolean checkWinner() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 9; i++) {
            board[i / 3][i % 3] = buttons[i].getText();
        }

        for (int i = 0; i < 3; i++) {
            if (!board[i][0].isEmpty() &&
                board[i][0].equals(board[i][1]) &&
                board[i][1].equals(board[i][2])) return true;

            if (!board[0][i].isEmpty() &&
                board[0][i].equals(board[1][i]) &&
                board[1][i].equals(board[2][i])) return true;
        }

        if (!board[0][0].isEmpty() &&
            board[0][0].equals(board[1][1]) &&
            board[1][1].equals(board[2][2])) return true;

        if (!board[0][2].isEmpty() &&
            board[0][2].equals(board[1][1]) &&
            board[1][1].equals(board[2][0])) return true;

        return false;
    }

    boolean isDraw() {
        for (JButton b : buttons) {
            if (b.getText().isEmpty()) return false;
        }
        return true;
    }
}

class WinPage extends JFrame {
    WinPage(String message) {
        setTitle("Game Over");
        setSize(300, 150);
        setLayout(new BorderLayout());

        JLabel winLabel = new JLabel(message + (message.equals("It's a Draw!") ? "" : " Wins!"), SwingConstants.CENTER);
        winLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        add(winLabel, BorderLayout.CENTER);

        JButton restartButton = new JButton("Play Again");
        add(restartButton, BorderLayout.SOUTH);

        restartButton.addActionListener(e -> {
            dispose();
            new PlayerInputPage();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
