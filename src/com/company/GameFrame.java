package com.company;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public GameFrame(){
        GamePanel panel = new GamePanel();
        this.add(panel);
        this.setTitle("Pac Man");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
        this.setSize(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        panel.requestFocus(true);
        this.setLocationRelativeTo(null);
    }
}
