import juegoSerpiente.presentacion.GamePanel;

import javax.swing.*;

public class SnakeGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        GamePanel panel = new GamePanel();

        frame.add(panel);
        frame.setSize(420, 440);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
