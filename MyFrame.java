package ClickBoxesGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyFrame extends JFrame implements ActionListener, MouseListener {

    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 600;
    private Image buffer;
    private Graphics bufferGraphics;
    private List<Box> enemies;
    private boolean gameOver;
    private int cont; // Counter for defeated enemies
    private boolean level2; // Flag to activate level 2

    private Timer timer;
    Random random;

    MyFrame() {
        random = new Random();
        enemies = new ArrayList<>();
        gameOver = false;
        cont = 0;
        level2 = false;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setLocationRelativeTo(null);
        timer = new Timer(10, this);
        timer.start();
        this.setVisible(true);
        this.addMouseListener(this);

        // Create double-buffering
        buffer = createImage(PANEL_WIDTH, PANEL_HEIGHT);
        bufferGraphics = buffer.getGraphics();

        startGame(); // Initialize the game
    }

    public void paint(Graphics g) {

        // Paint on the buffer
        bufferGraphics.clearRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        for (Box enemy : enemies) {
            enemy.draw(bufferGraphics);
        }

        if (gameOver) {
            bufferGraphics.setColor(Color.RED);
            bufferGraphics.setFont(new Font("MV Boli", Font.PLAIN, 45));
            bufferGraphics.drawString("GAME OVER!", 150, 100);
            timer.stop();
        }
        if (!gameOver) {
            bufferGraphics.setColor(Color.green);
            bufferGraphics.setFont(new Font("MV Boli", Font.PLAIN, 45));
            bufferGraphics.drawString("ENEMIES DEFEAT: " + cont, 85, 100);
        }

        // Copy the buffer to the window's graphics
        g.drawImage(buffer, 0, 0, null);
    }

    public void createEnemy() {
        int enemyWidth = 50;
        int enemyHeight = 50;
        int enemyX = (int) (Math.random() * (PANEL_WIDTH - enemyWidth));
        int enemyY = (int) (Math.random() * (PANEL_HEIGHT - enemyHeight));

        Box enemy = new Box(enemyX, enemyY, enemyWidth, enemyHeight, new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        enemies.add(enemy);
    }

    public void startGame() {
        // Create enemies continuously every 2 seconds
        Timer enemyTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!level2) {
                    createEnemy();
                }
                if (enemies.size() >= 10) {
                    gameOver = true;
                }
            }
        });
        enemyTimer.start();
    }

    public void nivel2() {
        Timer nivel2 = new Timer(700, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createEnemy();
                if (enemies.size() >= 10) {
                    gameOver = true;
                }
            }
        });
        nivel2.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver) {
            int mouseX = e.getX();
            int mouseY = e.getY();

            for (int i = 0; i < enemies.size(); i++) {
                Box enemy = enemies.get(i);
                if (mouseX >= enemy.getX() && mouseX <= enemy.getX() + enemy.getWidth() && mouseY >= enemy.getY() && mouseY <= enemy.getY() + enemy.getHeight()) {
                    enemies.remove(i);
                    cont++;
                    if (cont == 10 && !level2) {
                        nivel2();
                        level2 = true;
                    }
                    break;
                }
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
