package com.snakegame.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.plaf.synth.ColorType;

import com.snakegame.model.*;

public class Panel extends JPanel implements ActionListener  {

    //private Image snake_circle = new ImageIcon(getClass().getResource("snake_circle.png")).getImage();
    private HashMap<Fruit, Image> fruitSprites = new HashMap<Fruit, Image>();
    private JLabel[] scoreLabels;
    private static Board board;
    private static GameMode gameMode;
    private int fruitTimer;
    private int delay;

    private static Color[] snakeColors =
            {Color.blue, Color.green, Color.red, Color.magenta};

    private static int[][] playersControls =
            {
                    {KeyEvent.VK_D, KeyEvent.VK_A, KeyEvent.VK_W, KeyEvent.VK_S},
                    {KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_DOWN}
            };

    public Panel(int w, int h, int del, GameMode mode) {
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new TAdapter());
        scoreLabels = new JLabel[mode.snakeCount];
        for(int i = 0; i != mode.snakeCount; ++i) {
            scoreLabels[i] = new JLabel("Score: ");
            scoreLabels[i].setForeground(snakeColors[i]);
            scoreLabels[i].setLocation(300, 300 + i * 30);
            add(scoreLabels[i]);
        }

        board = new Board(w, h, 3, mode);
        gameMode = mode;
        LoadImages();
        delay = del;
        new Timer(delay, this).start();
        fruitTimer = board.fruit.timeToDestroy;
    }

    private void LoadImages() {
        for(Fruit fruit : gameMode.usingFruits)
            fruitSprites.put(fruit,
                    new ImageIcon(getClass().getResource(fruit.name + ".png")).getImage());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        g.drawImage(fruitSprites.get(board.fruit), board.getFruitPos().x * 30,  board.getFruitPos().y * 30, this);
        for(Snake snake: board.snakes) {
            scoreLabels[snake.number].setText("Score: " + snake.score);
            for (Point point : snake.snakePoints) {
                //g.drawImage(snake_circle, point.x * 30, point.y * 30, this);
                g.setColor(snakeColors[snake.number]);
                g.drawOval(point.x * 30, point.y * 30, 30, 30);
                g.fillOval(point.x * 30, point.y * 30, 30, 30);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int score = board.score;
        if(board.finished) {
            JFrameExtentions.closeInfoBox("Game finised! Score: " + score, "Game Over!");
        }
        board.moveSnakes();
        board.checkCollisions();
        if(board.score != score)
            fruitTimer = board.fruit.timeToDestroy;
        else {
            fruitTimer -= delay;
            if(fruitTimer < 0) {
                board.dropFruit();
                fruitTimer = board.fruit.timeToDestroy;
            }
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            for (int i = 0; i != gameMode.snakeCount; ++i)
                for(int j = 0; j != 4; ++j)
                    if(playersControls[i][j] == key)
                        board.snakes[i].setDirection(Direction.getDirection(j));
        }
    }
}
