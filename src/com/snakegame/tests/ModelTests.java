/*package com.snakegame.tests;

import com.snakegame.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.awt.*;
import java.lang.reflect.*;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class ModelTests {

    public static class ModelTestsClassic {
        private Board gameBoard;

        @Before
        public void testData() {
            GameMode.loadGameMods();
            gameBoard = new Board(10, 10, 2, GameMode.gameMods.get("classic"));
        }

        @Test
        public void testSimpleSingleMove() {
            Snake snake = gameBoard.snake;
            snake.move();
            assertEquals(new Point(0, 2), snake.getHead());
        }

        @Test
        public void testSimpleCycleMove() {
            Snake snake = gameBoard.snake;
            for (int i = 0; i != 4; ++i)
                snake.move();
            assertEquals(new Point(0, 5), snake.getHead());
        }

        @Test
        public void testMoveWithChangingDirection() {
            Snake snake = gameBoard.snake;
            snake.move();
            snake.setDirection(Direction.Right);
            snake.move();
            assertEquals(new Point(1, 2), snake.getHead());
        }

        @Test
        public void testHitInWall() {
            Snake snake = gameBoard.snake;
            snake.setDirection(Direction.Left);
            snake.move();
            gameBoard.checkCollision();
            assertEquals(true, gameBoard.finished);
        }

        @Test
        public void testGrowthSnake() throws NoSuchFieldException, IllegalAccessException {
            Snake snake = gameBoard.snake;
            Field fruitPos = gameBoard.getClass().getDeclaredField("fruitPos");
            fruitPos.setAccessible(true);
            fruitPos.set(gameBoard, new Point(0, 2));
            snake.move();
            gameBoard.checkCollision();
            assertEquals(3, snake.snakePoints.size());
        }

        @Test
        public void testIncreaseScore() throws NoSuchFieldException, IllegalAccessException {
            Field fruitPos = gameBoard.getClass().getDeclaredField("fruitPos");
            fruitPos.setAccessible(true);
            fruitPos.set(gameBoard, new Point(0, 1));
            gameBoard.checkCollision();
            assertEquals(1, gameBoard.getScore());
        }

        @Test
        public void testEatingItself() {
            gameBoard = new Board(10, 10, 5, GameMode.gameMods.get("classic"));
            Snake snake = gameBoard.snake;
            snake.snakePoints.set(2, new Point(1, 3));
            snake.snakePoints.set(3, new Point(1, 4));
            snake.snakePoints.set(4, new Point(0, 4));
            gameBoard.checkCollision();
            assertEquals(true, gameBoard.finished);
        }
    }

    public static class ModelTestsInfinitive {
        private Board gameBoard;

        @Before
        public void testData() {
            GameMode.loadGameMods();
            gameBoard = new Board(10, 10, 5, GameMode.gameMods.get("infinite"));
        }

        @Test
        public void testEatingItselfInInfinite() {
            Snake snake = gameBoard.snake;
            snake.snakePoints.set(2, new Point(1,3));
            snake.snakePoints.set(3, new Point(1,4));
            snake.snakePoints.set(4, new Point(0,4));
            gameBoard.checkCollision();
            assertEquals(false, gameBoard.finished);
            assertEquals(4, snake.snakePoints.size());
        }

        @Test
        public void testEatingAppleOrPearInInfinite() throws NoSuchFieldException, IllegalAccessException {
            Snake snake = gameBoard.snake;
            Field fruitPos = gameBoard.getClass().getDeclaredField("fruitPos");
            fruitPos.setAccessible(true);
            fruitPos.set(gameBoard, new Point(0, 4));
            gameBoard.checkCollision();
            assertEquals(true, gameBoard.getScore() == 5 || gameBoard.getScore() == 1);
        }

        @Test
        public void testGrowthSnakeInInfinite() throws NoSuchFieldException, IllegalAccessException {
            Snake snake = gameBoard.snake;
            Field fruitPos = gameBoard.getClass().getDeclaredField("fruitPos");
            fruitPos.setAccessible(true);
            fruitPos.set(gameBoard, new Point(0, 4));
            gameBoard.checkCollision();
            assertEquals(true, snake.snakePoints.size() == 10 || snake.snakePoints.size() == 6);
        }

        @Test
        public void testHitInWallInInfinite() {
            Snake snake = gameBoard.snake;
            snake.setDirection(Direction.Left);
            snake.move();
            gameBoard.checkCollision();
            assertEquals(false, gameBoard.finished);
            assertEquals(5, gameBoard.snake.snakePoints.size());
            assertEquals(0, gameBoard.snake.getHead().x);
            assertEquals(4, gameBoard.snake.getHead().y);
        }
    }
}*/

