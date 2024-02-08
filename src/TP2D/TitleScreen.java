package TP2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleScreen extends JFrame {
    private JButton startButton;

    public TitleScreen() {
        super("Écran Titre");

        // Création du bouton "Commencer le jeu"
        startButton = new JButton("Commencer le jeu");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action à effectuer lorsque le bouton est cliqué
                // Par exemple, fermer l'écran titre et lancer le jeu principal
                dispose();
                launchGame();
            }
        });

        // Personnalisation de l'écran titre (ajoutez des éléments, des couleurs, etc.)
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(startButton, BorderLayout.CENTER);
        panel.setBackground(Color.RED); // Fond noir pour l'écran titre
        startButton.setForeground(Color.BLACK); // Texte vert pour le bouton

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void launchGame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Création de l'interface principale du jeu
                MainInterface mainInterface = new MainInterface();
                // Assurez-vous que l'interface principale obtient le focus pour la gestion des touches
                mainInterface.requestFocus();
            }
        });
    }

    public static void main(String[] args) {
        // Lancement de l'écran titre
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TitleScreen();
            }
        });
    }
}

