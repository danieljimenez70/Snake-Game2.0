package juegoSerpiente.presentacion;

import juegoSerpiente.controladora.GameEngine;
import juegoSerpiente.persistencia.ScoreService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private final int CELL_SIZE = 10; //tamaño de cada celda
    private final int GRID_WIDTH = 60; //ancho del área jugable
    private final int GRID_HEIGHT = 40; //alto del area jugable

    private GameEngine engine;
    private Timer timer;
    private int highScore;

    public GamePanel() {
        engine = new GameEngine();
        highScore = ScoreService.getHighScore();

        timer = new Timer(150, this);
        timer.start();

        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        engine.update();

        if (engine.gameOver) {
            timer.stop();

            if (engine.score > highScore) {
                highScore = engine.score;
                ScoreService.saveHighScore(highScore);
            }
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Borde del area jugable
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, GRID_WIDTH * CELL_SIZE - 1, GRID_HEIGHT * CELL_SIZE - 1);

        // Dibujar serpiente
        g.setColor(Color.GREEN);
        for (Point p : engine.snake) {
            g.fillRect(p.x * CELL_SIZE, p.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        // Dibujar comida
        g.setColor(Color.RED);
        g.fillRect(engine.food.x * CELL_SIZE, engine.food.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        // Récord (arriba grande)
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Record: " + highScore, 10, 20);

        // Score actual (abajo pequeño)
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Score: " + engine.score, 10, getHeight() - 10);

        // Game Over
        if (engine.gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("GAME OVER", 80, 200);

            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString("Pulsa R para reiniciar", 90, 230);
        }
    }

    // Controles
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> engine.setDirection(0, -1);
            case KeyEvent.VK_DOWN -> engine.setDirection(0, 1);
            case KeyEvent.VK_LEFT -> engine.setDirection(-1, 0);
            case KeyEvent.VK_RIGHT -> engine.setDirection(1, 0);

            case KeyEvent.VK_R -> {
                if (engine.gameOver) {
                    engine.reset();
                    timer.start();
                }
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}