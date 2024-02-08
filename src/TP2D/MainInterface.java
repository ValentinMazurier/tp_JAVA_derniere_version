package TP2D;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainInterface extends JFrame implements KeyListener {
    private TileManager tileManager = new TileManager(48, 48, "./img/tileSet.png");
    private Dungeon dungeon = new Dungeon("img/level1.txt", tileManager);
    private Hero hero = Hero.getInstance();
    private GameRender panel = new GameRender(dungeon, hero);
    private List<Bomb> bombs = new ArrayList<>();
    private boolean gameOver = false;

    public MainInterface() throws HeadlessException {
        super();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel);
        this.setVisible(true);
        this.setSize(new Dimension(dungeon.getWidth() * tileManager.getWidth(), dungeon.getHeight() * tileManager.getHeight()));
        this.addKeyListener(this);

        ActionListener bombTimerAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBombs();
                repaint();
            }
        };

        Timer bombTimer = new Timer(100, bombTimerAction); // Le timer s'exécute toutes les 100 millisecondes
        bombTimer.start();

        ActionListener animationTimer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver) {
                    checkEnemyCollisions();
                    repaint();
                    final int speed = 20;
                    if (hero.isWalking()) {
                        switch (hero.getOrientation()) {
                            case LEFT:
                                hero.moveIfPossible(-speed, 0, dungeon);
                                break;
                            case RIGHT:
                                hero.moveIfPossible(speed, 0, dungeon);
                                break;
                            case UP:
                                hero.moveIfPossible(0, -speed, dungeon);
                                break;
                            case DOWN:
                                hero.moveIfPossible(0, speed, dungeon);
                                break;
                        }
                    }

                    if (hero.getHealth() <= 0) {
                        gameOver = true;
                        displayGameOver();
                    }
                }
            }
        };

        Timer timer = new Timer(50, animationTimer);
        timer.start();
    }

    private void displayGameOver() {
        JOptionPane.showMessageDialog(this, "Retente ta chance pour seulement 2.99€", "Tu es mort !!", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public static void main(String[] args) {
        MainInterface mainInterface = new MainInterface();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    hero.setOrientation(Orientation.LEFT);
                    hero.setWalking(true);
                    break;
                case KeyEvent.VK_RIGHT:
                    hero.setOrientation(Orientation.RIGHT);
                    hero.setWalking(true);
                    break;
                case KeyEvent.VK_UP:
                    hero.setOrientation(Orientation.UP);
                    hero.setWalking(true);
                    break;
                case KeyEvent.VK_DOWN:
                    hero.setOrientation(Orientation.DOWN);
                    hero.setWalking(true);
                    break;
                case KeyEvent.VK_P:
                    inflictDamage(35);
                    break;
                case KeyEvent.VK_G:
                    placeBomb();
                    break;
            }
            this.repaint();
        }
    }

    private void checkEnemyCollisions() {
        for (Things things : dungeon.getRenderList()) {
            if (things instanceof Enemy) {
                if (((Enemy) things).getHitBox().intersect(hero.getHitBox())) {
                    hero.takeDamage(10);
                }
                ((Enemy) things).move();
            }
        }
    }

    private void inflictDamage(int damage) {
        hero.takeDamage(damage);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver) {
            hero.setWalking(false);
        }
    }

    private void updateBombs() {
        Iterator<Bomb> iterator = bombs.iterator();
        while (iterator.hasNext()) {
            Bomb bomb = iterator.next();
            if (bomb.update()) {
                iterator.remove();
            }
        }
    }

    private void placeBomb() {
        bombs.add(new Bomb((int) hero.getX(), (int) hero.getY(), 3000));
    }

    class Bomb {
        private int x;
        private int y;
        private int explosionTime;

        public Bomb(int x, int y, int explosionTime) {
            this.x = x;
            this.y = y;
            this.explosionTime = explosionTime;
        }

        public boolean update() {
            explosionTime -= 100;
            return explosionTime <= 0;
        }

        public void draw(Graphics g) {
            g.setColor(Color.BLACK);
            g.fillOval(x, y, 20, 20);
        }
    }
}
