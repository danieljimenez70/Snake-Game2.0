import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SnakeGame extends JPanel implements ActionListener {
        private final int TILE_SIZE = 25;
        private final int WIDTH = 600;
        private final int HEIGHT = 600;

        private final java.util.List<Point> snake = new ArrayList<>();
        private Point food;
        private char direction = 'R';
        private boolean running = false;
        private Timer timer;

        public SnakeGame() {
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            setBackground(Color.BLACK);
            setFocusable(true);
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP -> { if (direction != 'D') direction = 'U'; }
                        case KeyEvent.VK_DOWN -> { if (direction != 'U') direction = 'D'; }
                        case KeyEvent.VK_LEFT -> { if (direction != 'R') direction = 'L'; }
                        case KeyEvent.VK_RIGHT -> { if (direction != 'L') direction = 'R'; }
                    }
                }
            });
            startGame();
        }

        private void startGame() {
            snake.clear();
            snake.add(new Point(5, 5));
            spawnFood();
            running = true;
            timer = new javax.swing.Timer(100, this);
            timer.start();
        }

        private void spawnFood() {
            Random rand = new Random();
            food = new Point(rand.nextInt(WIDTH / TILE_SIZE), rand.nextInt(HEIGHT / TILE_SIZE));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (running) {
                draw(g);
            } else {
                gameOver(g);
            }
        }

        private void draw(Graphics g) {
            g.setColor(Color.RED);
            g.fillOval(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

            g.setColor(Color.GREEN);
            for (Point p : snake) {
                g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        private void move() {
            Point head = new Point(snake.get(0));
            switch (direction) {
                case 'U' -> head.y--;
                case 'D' -> head.y++;
                case 'L' -> head.x--;
                case 'R' -> head.x++;
            }
            snake.add(0, head);

            if (head.equals(food)) {
                spawnFood();
            } else {
                snake.remove(snake.size() - 1);
            }
        }

        private void checkCollision() {
            Point head = snake.get(0);

            if (head.x < 0 || head.x >= WIDTH / TILE_SIZE ||
                    head.y < 0 || head.y >= HEIGHT / TILE_SIZE) {
                running = false;
            }

            for (int i = 1; i < snake.size(); i++) {
                if (head.equals(snake.get(i))) {
                    running = false;
                }
            }

            if (!running) {
                timer.stop();
            }
        }

        private void gameOver(Graphics g) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over", WIDTH / 3, HEIGHT / 2);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (running) {
                move();
                checkCollision();
            }
            repaint();
        }

        public static void main(String[] args) {
            JFrame frame = new JFrame("Snake Game");
            SnakeGame game = new SnakeGame();
            frame.add(game);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }
