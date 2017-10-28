package com.snakegame.tests;

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
        private Snake snake;

        @Before
        public void testData() {
            GameMode.loadGameMods();
            gameBoard = new Board(10, 10, 2, GameMode.gameMods.get("classic"));
            snake = gameBoard.snakes[0];
        }

        @Test
        public void testSimpleSingleMove() {
            Point prevPosition = snake.getHead();

            snake.move();

            assertEquals(new Point
                    (prevPosition.x + snake.getDirection().x, prevPosition.y + snake.getDirection().y),
                    snake.getHead());
        }

        @Test
        public void testSimpleDoubleMove() {
            Point prevPosition = snake.getHead();

            snake.move();
            snake.move();

            assertEquals(new Point
                            (prevPosition.x + 2 *snake.getDirection().x,
                                    prevPosition.y + 2 * snake.getDirection().y),
                    snake.getHead());
        }

        @Test
        public void testMoveWithChangingDirection() {
            Point prevPoint = snake.getHead();
            Point startingDirection = snake.getDirection();

            snake.move();
            snake.setDirection(Direction.Right);
            snake.move();
            prevPoint.translate(startingDirection.x,startingDirection.y);
            prevPoint.translate(snake.getDirection().x, snake.getDirection().y);

            assertEquals(prevPoint, snake.getHead());
        }

        @Test
        public void testHitInWall() {
            gameBoard.snakes[0] = new Snake(0, 0, Direction.Left, 3, 1);
            Snake snake = gameBoard.snakes[0];

            snake.move();
            gameBoard.checkCollisions();

            assertEquals(true, gameBoard.finished);
        }

        @Test
        public void testGrowthSnake() throws NoSuchFieldException, IllegalAccessException {
            Field fruitPos = gameBoard.getClass().getDeclaredField("fruitPos");
            int prevSnakeSize = snake.snakePoints.size();

            fruitPos.setAccessible(true);//можно было не париться с рефлексией и просто написать сеттер, но исправлять не буду
            fruitPos.set(gameBoard, new Point(snake.getHead().x + snake.getDirection().x,
                    snake.getHead().y + snake.getDirection().y));
            snake.move();
            gameBoard.checkCollisions();

            assertEquals(prevSnakeSize + 1, snake.snakePoints.size());
        }

        @Test
        public void testIncreaseScore() throws NoSuchFieldException, IllegalAccessException {
            int prevScore = gameBoard.score;
            int bonusPoints = gameBoard.fruit.givenScore;

            Field fruitPos = gameBoard.getClass().getDeclaredField("fruitPos");
            fruitPos.setAccessible(true);
            fruitPos.set(gameBoard, new Point(snake.getHead().x, snake.getHead().y));
            gameBoard.checkCollisions();

            assertEquals(prevScore+bonusPoints, gameBoard.score);
        }

        @Test
        public void testEatingItself() {
            gameBoard.snakes[0] = new Snake(2, 2, Direction.Down, 5, 1);
            Snake snake = gameBoard.snakes[0];

            snake.move();
            snake.setDirection(Direction.Right);
            gameBoard.checkCollisions();
            snake.move();
            snake.setDirection(Direction.Up);
            gameBoard.checkCollisions();
            snake.move();
            snake.setDirection(Direction.Left);
            gameBoard.checkCollisions();
            snake.move();
            gameBoard.checkCollisions();

            assertEquals(true, gameBoard.finished);
        }
    }

//    public static class ModelTestsInfinitive {
//        private Board gameBoard;
//
//        @Before
//        public void testData() {
//            GameMode.loadGameMods();
//            gameBoard = new Board(10, 10, 5, GameMode.gameMods.get("infinite"));
//        }
//
//        @Test
//        public void testEatingItselfInInfinite() {
//            Snake snake = gameBoard.snake;
//            snake.snakePoints.set(2, new Point(1,3));
//            snake.snakePoints.set(3, new Point(1,4));
//            snake.snakePoints.set(4, new Point(0,4));
//            gameBoard.checkCollision();
//            assertEquals(false, gameBoard.finished);
//            assertEquals(4, snake.snakePoints.size());
//        }
//
//        @Test
//        public void testEatingAppleOrPearInInfinite() throws NoSuchFieldException, IllegalAccessException {
//            Snake snake = gameBoard.snake;
//            Field fruitPos = gameBoard.getClass().getDeclaredField("fruitPos");
//            fruitPos.setAccessible(true);
//            fruitPos.set(gameBoard, new Point(0, 4));
//            gameBoard.checkCollision();
//            assertEquals(true, gameBoard.getScore() == 5 || gameBoard.getScore() == 1);
//        }
//
//        @Test
//        public void testGrowthSnakeInInfinite() throws NoSuchFieldException, IllegalAccessException {
//            Snake snake = gameBoard.snake;
//            Field fruitPos = gameBoard.getClass().getDeclaredField("fruitPos");
//            fruitPos.setAccessible(true);
//            fruitPos.set(gameBoard, new Point(0, 4));
//            gameBoard.checkCollision();
//            assertEquals(true, snake.snakePoints.size() == 10 || snake.snakePoints.size() == 6);
//        }
//
//        @Test
//        public void testHitInWallInInfinite() {
//            Snake snake = gameBoard.snake;
//            snake.setDirection(Direction.Left);
//            snake.move();
//            gameBoard.checkCollision();
//            assertEquals(false, gameBoard.finished);
//            assertEquals(5, gameBoard.snake.snakePoints.size());
//            assertEquals(0, gameBoard.snake.getHead().x);
//            assertEquals(4, gameBoard.snake.getHead().y);
//        }
//     }
}


