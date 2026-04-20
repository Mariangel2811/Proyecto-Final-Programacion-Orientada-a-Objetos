import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PongGame extends JPanel implements ActionListener, KeyListener {

    static final int WIDTH = 800;
    static final int HEIGHT = 500;

    int playerY = 200;
    int aiY = 200;

    int ballX = WIDTH / 2;
    int ballY = HEIGHT / 2;
    int ballSpeedX = 3;
    int ballSpeedY = 3;

    int playerScore = 0;
    int aiScore = 0;

    boolean gameOver = false;
    String result = "";

    Timer timer;

    public PongGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            moveBall();
            moveAI();
            checkCollision();
            checkWinCondition();
        }
        repaint();
    }

    private void moveBall() {
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        if (ballY <= 0 || ballY >= HEIGHT - 20) {
            ballSpeedY *= -1;
        }

        if (ballX <= 0) {
            aiScore++;
            resetBall();
        }

        if (ballX >= WIDTH - 20) {
            playerScore++;
            resetBall();
        }
    }

    private void moveAI() {
        if (ballY > aiY) aiY += 2;
        else aiY -= 2;
    }

    private void checkCollision() {
        if (ballX <= 50 && ballY >= playerY && ballY <= playerY + 80) {
            ballSpeedX *= -1;
        }

        if (ballX >= WIDTH - 60 && ballY >= aiY && ballY <= aiY + 80) {
            ballSpeedX *= -1;
        }
    }

    private void resetBall() {
        ballX = WIDTH / 2;
        ballY = HEIGHT / 2;
        ballSpeedX *= -1;
    }

    // 🏁 CONDICIÓN DE VICTORIA
    private void checkWinCondition() {
        if (playerScore == 10) {
            gameOver = true;
            result = "¡GANASTE!";
            timer.stop();
        }

        if (aiScore == 10) {
            gameOver = true;
            result = "PERDISTE";
            timer.stop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!gameOver) {
            // jugador
            g.setColor(Color.WHITE);
            g.fillRect(30, playerY, 10, 80);

            // IA
            g.fillRect(WIDTH - 40, aiY, 10, 80);

            // pelota
            g.fillOval(ballX, ballY, 20, 20);

            // puntaje
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString(playerScore + " - " + aiScore, WIDTH / 2 - 40, 40);
        } else {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString(result, WIDTH / 2 - 150, HEIGHT / 2);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                playerY -= 20;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                playerY += 20;
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}