package com.snakegame.model;
import java.awt.*;
import java.util.ArrayList;

public class Snake {

    public ArrayList<Point> snakePoints = new ArrayList<Point>();
    private Point direction;
    public int number;
    public int score = 0;

    public Point getDirection(){ return direction; }

    public Snake(int size, int num){
        number = num;
        for(int i = size - 1; i != -1; --i)
            snakePoints.add(new Point(number * size, i));
        direction = Direction.Down;
    }

    public void move(){
        for(int i = snakePoints.size() - 1; i != 0; --i)
            snakePoints.set(i, snakePoints.get(i - 1));
        Point head = snakePoints.get(0);
        snakePoints.set(0, new Point(head.x + direction.x, head.y + direction.y));
    }

    public void addLength(){
        int length = snakePoints.size();
        Point firstTail = snakePoints.get(length - 1);
        Point secondTail = snakePoints.get(length - 2);
        snakePoints.add(
                new Point(2 * firstTail.x - secondTail.x,
                        2 * firstTail.y - secondTail.y));
    }

    public void setDirection(Point newDir) {
        if(newDir.equals(new Point(-direction.x, -direction.y)))
            return;
        direction = newDir;
    }

    public Point getHead(){
        return snakePoints.get(0);
    }

}
