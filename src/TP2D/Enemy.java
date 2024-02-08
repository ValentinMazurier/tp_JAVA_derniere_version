package TP2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class Enemy extends DynamicThings {
    private static final int DEFAULT_WIDTH = 32;
    private static final int DEFAULT_HEIGHT = 32;

    private Random random;

    public Enemy(int x, int y, Image spriteSheet) {
        super(x, y, spriteSheet);
        this.random = new Random();
        this.orientation = Orientation.LEFT;
    }

    public Enemy(int x, int y) {
        super(x, y, 25, 36);
        try {
            this.image = ImageIO.read(new File("img/MonsterFireElemental.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.random = new Random();
    }

    public void move() {
        // Déplacement aléatoire en ajustant les coordonnées x et y
        int deltaX = random.nextInt(5) - 2; // Déplacement aléatoire entre -2 et 2 en x
        int deltaY = random.nextInt(5) - 2; // Déplacement aléatoire entre -2 et 2 en y

        x += deltaX;
        y += deltaY;
    }

    public void draw(Graphics g) {
        // Implémentez le code pour dessiner l'ennemi sur l'écran
        int attitude = orientation.getI();

        // Utilisez les dimensions correctes de votre sprite
        int spriteWidth = 32;  // Remplacez par la largeur correcte de votre sprite
        int spriteHeight = 48;  // Remplacez par la hauteur correcte de votre sprite

        g.drawImage(image, (int) x, (int) y,
                (int) x + spriteWidth, (int) y + spriteHeight,
                0, attitude * 72, 300, (attitude + 1) * 72, null);
    }

    public void moveTowardsHero(Hero hero) {
        double dx = hero.getX() - this.x;
        double dy = hero.getY() - this.y;

        // Calculer la distance totale pour normaliser le déplacement
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Normaliser le déplacement
        dx /= distance;
        dy /= distance;

        // Déplacer l'ennemi
        x += dx * 2; // Assurez-vous de définir la vitesse (par exemple, 2) de l'ennemi
        y += dy * 2;
    }

}
