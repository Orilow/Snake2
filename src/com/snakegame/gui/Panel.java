package com.snakegame.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.plaf.synth.ColorType;

import com.snakegame.client.Client;
import com.snakegame.model.*;

public class Panel extends JPanel  {

    //private Image snake_circle = new ImageIcon(getClass().getResource("snake_circle.png")).getImage();
    private HashMap<Fruit, Image> fruitSprites = new HashMap<Fruit, Image>();
    private JLabel[] scoreLabels;
    public Game game;

    private static Color[] snakeColors =
            {Color.blue, Color.green, Color.red, Color.magenta};

    private static int[][] playersControls =
            {
                    {KeyEvent.VK_D, KeyEvent.VK_A, KeyEvent.VK_W, KeyEvent.VK_S},
                    {KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_DOWN}
            };

    public Panel(int w, int h, int del, GameMode mode, Client client) {
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
        game = new Game(w, h, del, mode, this, client);
        LoadImages();
    }

    private void LoadImages() {
        GameMode gameMode = game.gameMode;
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
        Board board = game.board;
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
        //Toolkit.getDefaultToolkit().sync();
    }

<<<<<<< HEAD
    public void actionPerformed() {
        Board board = game.board;
=======
    @Override
    public void actionPerformed(ActionEvent e) {
>>>>>>> 91c594f716de3a4a9a5948fb86d7ec2d58585b56
        if(board.finished) {
            String infoMes = "Game finised!\n";
            for(int i = 0; i != board.snakes.length; ++i) {
                infoMes += "Player " + i + ". Score: " + board.snakes[i].score;
                if(board.loserNum == i) infoMes += ". Loser";
                infoMes += "\n";
<<<<<<< HEAD
=======
            }
            JFrameExtentions.closeInfoBox(infoMes, "Game Over!");

        }
        int score = board.score;
        board.moveSnakes();
        board.checkCollisions();
        if(board.score != score)
            fruitTimer = board.fruit.timeToDestroy;
        else {
            fruitTimer -= delay;
            if(fruitTimer < 0) {
                board.dropFruit();
                fruitTimer = board.fruit.timeToDestroy;
>>>>>>> 91c594f716de3a4a9a5948fb86d7ec2d58585b56
            }
            JFrameExtentions.closeInfoBox(infoMes, "Game Over!");

        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            Board board = game.board;
            GameMode gameMode = game.gameMode;
            int key = e.getKeyCode();
            for (int i = 0; i != gameMode.snakeCount; ++i)
                for(int j = 0; j != 4; ++j)
                    if(playersControls[i][j] == key)
                        board.snakes[i].setDirection(Direction.getDirection(j));
        }
    }
}
