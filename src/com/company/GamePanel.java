package com.company;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;


public class GamePanel extends JPanel implements ActionListener, KeyListener, MouseListener {
    static final int COLUMNS = 19;
    static final int ROWS = 21;
    static final int TILE_SIZE = 32;
    static final int SCREEN_WIDTH = COLUMNS * TILE_SIZE;
    static final int SCREEN_HEIGHT = ROWS * TILE_SIZE;
    static final int DELAY = 100;
    private BufferedImage blueGhost;
    private BufferedImage pinkGhost;
    private BufferedImage yellowGhost;
    private BufferedImage redGhost;
    private BufferedImage pacDown;
    private BufferedImage pacLeft;
    private BufferedImage pacRight;
    private BufferedImage pacUp;
    private BufferedImage wallImage;
    private BufferedImage foodDark;
    private BufferedImage foodLight;
    private BufferedImage pause;
    private BufferedImage play;
    private BufferedImage life;
    private BufferedImage sound;
    private BufferedImage mute;
    static HashSet<Block> walls;
    static HashSet<Block> ghosts;
    static HashSet<Block> foods;
    static Block pacman;
    static boolean isRunning = true;
    Timer timer;
    Random random;
    private final char[] directions = {'R', 'L', 'U', 'D'};
    private int score;
    private boolean roundFinished = false;
    private boolean isPaused = false;
    private int rounds;
    private boolean isDark = true;
    private int hi;
    private boolean isHiBroken = false;
    private int lives;
    private boolean isMute;
    private final char[][] tileMap = {
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'O', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', 'X', 'X', 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', 'X', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', 'X', 'X', ' ', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', ' ', 'X', 'X', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', 'X', 'X', 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', 'X', ' ', 'X', 'X', 'X', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'X', ' ', 'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', 'X', 'X', 'X', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X', ' ', 'X', 'X', 'X', 'X', 'X', 'X'},
            {'O', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'O'},
            {'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', 'X', 'X', ' ', 'X', 'X', 'X', 'X', 'r', 'X', 'X', 'X', 'X', ' ', 'X', 'X', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'p', 'y', 'b', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X'},
            {'X', ' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ', ' ', 'X'},
            {'X', 'X', 'X', 'X', ' ', 'X', 'X', 'X', ' ', 'X', ' ', 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X'},
            {'O', 'O', 'O', 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X', 'O', 'O', 'O'},
            {'X', 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X'},
            {'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'P', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'},
            {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}
    };

    public GamePanel(){
        this.hi = FileHandling.readingFromFile("hi.txt");
        this.setBackground(new Color(0,0,0));
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT + 3*TILE_SIZE));
        try {
            blueGhost = ImageIO.read(new File("ghost_blue.png"));
            redGhost = ImageIO.read(new File("ghost_red.png"));
            yellowGhost = ImageIO.read(new File("ghost_yellow.png"));
            pinkGhost = ImageIO.read(new File("ghost_pink.png"));
            pacDown = ImageIO.read(new File("pac_down.png"));
            pacUp = ImageIO.read(new File("pac_up.png"));
            pacLeft = ImageIO.read(new File("pac_left.png"));
            pacRight= ImageIO.read(new File("pac_right.png"));
            wallImage = ImageIO.read(new File("wall.png"));
            pause = ImageIO.read(new File("pause.jpg"));
            play = ImageIO.read(new File("play.jpg"));
            foodDark = ImageIO.read(new File("food.png"));
            foodLight = ImageIO.read(new File("food_light.png"));
            life = ImageIO.read(new File("life.png"));
            sound = ImageIO.read(new File("dark_sound.png"));
            mute = ImageIO.read(new File("light_mute.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
        isMute = FileHandling.readingFromFile("MuteStatus.txt") == 1;
        this.setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        random = new Random();
        start(false);
    }

    public void start(boolean carry){
        isRunning = true;
        roundFinished = false;
        isPaused = false;
        if(!carry) {
            score = 0;
            rounds = 1;
            isDark = true;
            lives = 3;
        }
        loadMap();
        for (Block ghost: ghosts){
            ghost.updateDirection(directions[random.nextInt(4)]);
        }
        timer = new Timer(DELAY - (rounds*10), this);
        timer.start();
    }

    public void loadMap(){
        ghosts = new HashSet<>();
        walls = new HashSet<>();
        foods = new HashSet<>();

        BufferedImage foodImage;
        if(isDark) {    // initialize differently, then assign foodImage according to isDark in start() method
            foodImage = foodDark;
        } else {
            foodImage = foodLight;
        }

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                char tileMapChar = tileMap[r][c];

                int x = c*TILE_SIZE;
                int y = r*TILE_SIZE;

                switch (tileMapChar){
                    case 'X' -> walls.add(new Block(wallImage, x, y, TILE_SIZE, TILE_SIZE));
                    case 'r' -> ghosts.add(new Block(redGhost, x, y, TILE_SIZE, TILE_SIZE));
                    case 'p' -> ghosts.add(new Block(pinkGhost, x, y, TILE_SIZE, TILE_SIZE));
                    case 'y' -> ghosts.add(new Block(yellowGhost, x, y, TILE_SIZE, TILE_SIZE));
                    case 'b' -> ghosts.add(new Block(blueGhost, x, y, TILE_SIZE, TILE_SIZE));
                    case 'P' -> pacman = new Block(pacRight, x, y, TILE_SIZE, TILE_SIZE);
                    case ' ' -> foods.add(new Block(foodImage, x, y, TILE_SIZE, TILE_SIZE));
                }
            }
        }
    }

    public void move(){
        if (!isPaused){
            pacman.x += pacman.velX;
            pacman.y += pacman.velY;
            for (Block wall : walls) {
                if (collision(wall, pacman)) {
                    pacman.x -= pacman.velX;
                    pacman.y -= pacman.velY;
                    break;
                }
            }
            if (pacman.x <= 0 && pacman.y == 9 * TILE_SIZE) {
                pacman.x = 18 * TILE_SIZE;
            } else if (pacman.x >= 18 * TILE_SIZE && pacman.y == 9 * TILE_SIZE) {
                pacman.x = 0;
            }
            for (Block ghost : ghosts) {
                ghost.x += ghost.velX;
                ghost.y += ghost.velY;
                for (Block wall : walls) {
                    if (collision(wall, ghost)) {
                        ghost.x -= ghost.velX;
                        ghost.y -= ghost.velY;
                        ghost.updateDirection(directions[random.nextInt(4)]);
                    }
                }
                if (ghost.x == 0 && ghost.y == 9 * TILE_SIZE) {
                    ghost.x = 18 * TILE_SIZE;
                } else if (ghost.x == 18 * TILE_SIZE && ghost.y == 9 * TILE_SIZE) {
                    ghost.x = 0;
                } else if (ghost.x == 4 * TILE_SIZE && ghost.y == 19 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (ghost.x == 4 * TILE_SIZE && ghost.y == 17 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (ghost.x == 14 * TILE_SIZE && ghost.y == 19 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (ghost.x == 14 * TILE_SIZE && ghost.y == 17 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (ghost.x == 6 * TILE_SIZE && ghost.y == 13 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (ghost.x == 12 * TILE_SIZE && ghost.y == 13 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (ghost.x == 17 * TILE_SIZE && ghost.y == 3 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (ghost.x == TILE_SIZE && ghost.y == 3 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (ghost.x == 17 * TILE_SIZE && ghost.y == 5 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (ghost.x == TILE_SIZE && ghost.y == 13 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (ghost.x == 17 * TILE_SIZE && ghost.y == 13 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (ghost.x == TILE_SIZE && ghost.y == 11 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (ghost.x == 17 * TILE_SIZE && ghost.y == 11 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (ghost.x == 6 * TILE_SIZE && ghost.y == 11 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (ghost.x == 8 * TILE_SIZE && ghost.y == 11 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                }else if (ghost.x == 10 * TILE_SIZE && ghost.y == 11 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                }else if (ghost.x == 12 * TILE_SIZE && ghost.y == 11 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                }else if (ghost.x == 7 * TILE_SIZE && ghost.y == 3 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                }else if (ghost.x == 11 * TILE_SIZE && ghost.y == 3 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (ghost.x == 6 * TILE_SIZE && ghost.y == 9 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (ghost.x == 12 * TILE_SIZE && ghost.y == 9 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                }else if (ghost.x == TILE_SIZE && ghost.y == TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                }else if (ghost.x == 17 * TILE_SIZE && ghost.y == TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (ghost.x == TILE_SIZE && ghost.y == 5 * TILE_SIZE) {
                    ghost.updateDirection(directions[random.nextInt(4)]);
                } else if (collision(pacman, ghost)) {
                    lives--;
                    if(!isMute){
                        try {
                            FileHandling.sound("Crash.wav");
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (lives==0){
                        isRunning = false;
                        timer.stop();
                        continue;
                    }
                    isPaused=true;
                    pacman.velY=0;
                    pacman.velX=0;
                    pacman.image = pacRight;
                    pacman.x = pacman.startX;
                    pacman.y = pacman.startY;
                    for (Block g: ghosts){
                        g.x = g.startX;
                        g.y = g.startY;
                    }
                }
            }
        }

        Block eatenFood = null;
        for (Block food: foods){
            if(collision(food, pacman)){
                eatenFood = food;
                score += 10;
                if (score> hi){
                    hi = score;
                    FileHandling.writingToFile("hi.txt", hi);
                    isHiBroken = true;
                }
                if(!isMute){
                    try {
                        FileHandling.sound("BeepShort.wav");
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        foods.remove(eatenFood);
        if (foods.isEmpty()){
            roundFinished = true;
            timer.stop();
        }
    }

    public static boolean collision(Block a, Block b){
        return (a.x < b.x + b.width) &&
                (a.x + a.width > b.x) &&
                (a.y < b.y + b.height) &&
                (a.y + a.height > b.y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if (rounds%2==1) {
            this.setBackground(new Color(0, 0, 0));
            isDark = true;
        } else{
            this.setBackground(new Color(255, 255, 255));
            isDark = false;
        }
        for (Block wall: walls){
            g.drawImage(wall.image, wall.x+2, wall.y+2, this);
        }
        for (Block food: foods){
            g.drawImage(food.image, food.x+2, food.y+2, this);
        }
        g.drawImage(pacman.image, pacman.x, pacman.y, this);
        for (Block ghost: ghosts){
            g.drawImage(ghost.image, ghost.x, ghost.y, this);
        }
        if(isDark) {
            g.setColor(new Color(255, 255, 255));
        } else {
            g.setColor(new Color(0, 141, 80));
        }
        g.setFont(new Font("dialog", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, SCREEN_HEIGHT+20);
        g.drawString("Hi: " + hi, 20, SCREEN_HEIGHT+45);
        g.drawString("Round: " + rounds, SCREEN_WIDTH - 100, SCREEN_HEIGHT+20);
        if (isHiBroken){
            g.setFont(new Font("dialog", Font.BOLD, 15));
            g.drawString("(Broken!)", 20, SCREEN_HEIGHT+60);
        }
        for (int i=0; i<lives; i++){
            g.drawImage(life, SCREEN_WIDTH-((TILE_SIZE+5)*i)-50, SCREEN_HEIGHT+45, this);
        }

        if (!isRunning){
            g.setColor(new Color(255, 0, 0));
            g.setFont(new Font("dialog", Font.BOLD, 35));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Game Over!", (GamePanel.SCREEN_WIDTH - metrics.stringWidth("Game Over!"))/2, 400);
            g.setFont(new Font("dialog", Font.BOLD, 25));
            metrics = getFontMetrics(g.getFont());
            g.drawString("Press Space to play again", (GamePanel.SCREEN_WIDTH - metrics.stringWidth("Press Space to play again"))/2, 450);
            g.drawString("Press Esc to exit", (GamePanel.SCREEN_WIDTH - metrics.stringWidth("Press Esc to exit"))/2, 480);
        }

        if (roundFinished){
            if (isDark) {
                g.setColor(new Color(0, 255, 0));
            } else {
                g.setColor(new Color(8, 168, 17));
            }
            g.setFont(new Font("dialog", Font.BOLD, 35));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("You finished Round " + rounds + " successfully!", (GamePanel.SCREEN_WIDTH - metrics.stringWidth("You finished Round " + rounds + " successfully!"))/2, 380);
            g.setFont(new Font("dialog", Font.BOLD, 25));
            metrics = getFontMetrics(g.getFont());
            g.drawString("Press Space to go to Round " + ++rounds, (GamePanel.SCREEN_WIDTH - metrics.stringWidth("Press Space to go to Round " + rounds))/2, 430);
            g.drawString("Press Esc to exit", (GamePanel.SCREEN_WIDTH - metrics.stringWidth("Press Esc to exit"))/2, 460);
            isDark = !isDark;
        }

        if(isPaused){
            g.drawImage(pause, SCREEN_WIDTH-TILE_SIZE, 0, this);
        } else {
            g.drawImage(play, SCREEN_WIDTH-TILE_SIZE, 0, this);
        }

        if (!isMute){
            g.drawImage(sound, 20, SCREEN_HEIGHT+60, this);
        } else{
            g.drawImage(mute, 20, SCREEN_HEIGHT+60, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning && (!roundFinished)) {
            move();
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (isRunning && !roundFinished) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT -> {
                    if (!isPaused)
                        pacman.updateDirection('R');
                }
                case KeyEvent.VK_LEFT -> {
                    if (!isPaused)
                        pacman.updateDirection('L');
                }
                case KeyEvent.VK_UP -> {
                    if (!isPaused)
                        pacman.updateDirection('U');
                }
                case KeyEvent.VK_DOWN -> {
                    if (!isPaused)
                        pacman.updateDirection('D');
                }
                case KeyEvent.VK_SPACE -> isPaused = !isPaused;
                case KeyEvent.VK_M -> {
                    isMute = !isMute;
                    if (isMute){
                        FileHandling.writingToFile("MuteStatus.txt", 1);
                    }
                    else {
                        FileHandling.writingToFile("MuteStatus.txt", 0);
                    }
                }
            }
            switch (pacman.direction) {
                case 'R' -> pacman.image = pacRight;
                case 'L' -> pacman.image = pacLeft;
                case 'U' -> pacman.image = pacUp;
                case 'D' -> pacman.image = pacDown;
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                start(roundFinished);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        if (((mouseX >= 20) && (mouseX <= 20 + mute.getWidth())) && ((mouseY >= SCREEN_HEIGHT+60) && ((mouseY <= SCREEN_HEIGHT+60+mute.getHeight())))){
            isMute = !isMute;
            if (isMute){
                FileHandling.writingToFile("MuteStatus.txt", 1);
            }
            else {
                FileHandling.writingToFile("MuteStatus.txt", 0);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}

class FileHandling{
    public static void writingToFile(String path, int contents){
        File file = new File(path);
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(Integer.toString(contents));
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sound(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File(path);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }

    public static int readingFromFile(String path){
        File file = new File(path);
        if (!file.exists()){
            writingToFile(path, 0);
            return 0;
        }
        else {
            try {
                file.createNewFile();
                Scanner scanner = new Scanner(file);
                int contents = scanner.nextInt();
                scanner.close();
                return contents;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }
}

class Block{
    int x;
    int y;
    int width;
    int height;
    BufferedImage image;
    int startX;
    int startY;
    char direction='R';
    int velX=0;
    int velY=0;

    public Block(BufferedImage image, int x, int y, int height, int width) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.height = height;
        this.width = width;
    }

    public void updateDirection(char direction){
        char prevDirection = this.direction;
        this.direction = direction;
        this.updateVelocity();
        this.x += this.velX;
        this.y += this.velY;
        for(Block wall: GamePanel.walls){
            if(GamePanel.collision(wall, this)){
                this.x -= this.velX;
                this.y -= this.velY;
                this.direction = prevDirection;
                this.updateVelocity();
            }
        }
    }

    public void updateVelocity(){
        switch (this.direction){
            case 'R' -> {
                velX = GamePanel.TILE_SIZE/4;
                velY = 0;
            }
            case 'L' -> {
                velX = -GamePanel.TILE_SIZE/4;
                velY=0;
            }
            case 'U' -> {
                velX=0;
                velY = -GamePanel.TILE_SIZE/4;
            }
            case 'D' -> {
                velX=0;
                velY = GamePanel.TILE_SIZE/4;
            }
        }
    }
}
