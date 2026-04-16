package juegoSerpiente.controladora;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class GameEngine {
    public int width_area = 60;
    public int height_area = 40;
    public LinkedList<Point> snake;
    public Point direction;
    public Point food;
    public int score;
    public boolean gameOver;

    public GameEngine() {
        reset();
    }

    // Reiniciar juego
    public void reset() {
        snake = new LinkedList<>();
        snake.add(new Point(5, 5));
        direction = new Point(1, 0);
        food = randomFood();
        score = 0;
        gameOver = false;
    }

    // Generar comida aleatoria
    private Point randomFood() {
        Random r = new Random();
        return new Point(r.nextInt(width_area), r.nextInt(height_area));
    }

    // Cambiar dirección
    public void setDirection(int dx, int dy) {
        direction = new Point(dx, dy);
    }

    // Actualizar estado del juego
    public void update() {
        if (gameOver) return;

        Point head = new Point(snake.getFirst());
        head.translate(direction.x, direction.y);

        // Colisión con pared
        if (head.x < 0 || head.y < 0 || head.x >= width_area || head.y >= height_area) {
            gameOver = true;
            return;
        }

        // Colisión consigo mismo
        for (Point p : snake) {
            if (p.equals(head)) {
                gameOver = true;
                return;
            }
        }

        snake.addFirst(head);

        // Comer comida
        if (head.equals(food)) {
            score++;
            food = randomFood();
        } else {
            snake.removeLast();
        }
    }
}
