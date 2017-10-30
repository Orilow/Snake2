package com.snakegame.model;
import java.awt.*;

public class Direction {
    public static final Point Down = new Point(0,1);
    public static final Point Up = new Point(0,-1);
    public static final Point Right = new Point(1,0);
    public static final Point Left = new Point(-1,0);

    public static Point getDirection(int number) {
        switch (number) {
            case 0: return Right;
            case 1: return Left;
            case 2: return Up;
            case 3: return Down;
        }
        return null;
    }
}
