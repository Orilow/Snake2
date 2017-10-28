package com.snakegame.tests;

import com.snakegame.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.awt.*;
import java.lang.reflect.*;

import static org.junit.Assert.*;

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
        public void testInitializationWidthHeightScoreSize() throws NoSuchFieldException, IllegalAccessException {
            Board anotherGameBoard = new Board(10,11,3,GameMode.gameMods.get("classic"));

            int width = anotherGameBoard.getWidth();
            int height = anotherGameBoard.getHeight();

            assertEquals(width, 10);
            assertEquals(height, 11);
            assertEquals(anotherGameBoard.score, 0);
            assertEquals(anotherGameBoard.snakes[0].snakePoints.size(), 3);
        }

        @Test(expected = IllegalArgumentException.class)
        public void testWrongInitialization(){
            Board anotherGameBoard = new Board(0, 0, 0, GameMode.gameMods.get("classic"));
        }

        @Test
        public void testInitializationFruitGameModeSnakeAndfinished() throws NoSuchFieldException, IllegalAccessException {
            Board anotherGameBoard = new Board(10,11,3,GameMode.gameMods.get("classic"));
            GameMode gameMode = anotherGameBoard.getGameMode();

            assertNotNull(anotherGameBoard.fruit);
            assertFalse(anotherGameBoard.finished);
            assertNotNull(anotherGameBoard.snakes);
            assertEquals(gameMode, GameMode.gameMods.get("classic"));
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
        public void testHitInWallGameOver() {
            gameBoard.snakes[0] = new Snake(0, 0, Direction.Left, 3, 1);
            Snake snake = gameBoard.snakes[0];

            snake.move();
            gameBoard.checkCollisions();

            assertEquals(true, gameBoard.finished);
        }

        @Test
        public void testGrowthSnakeAndCorrectEating() throws NoSuchFieldException, IllegalAccessException {
            Field fruitPos = gameBoard.getClass().getDeclaredField("fruitPos");
            int prevSnakeSize = snake.snakePoints.size();
            int prevScore = snake.score;

            fruitPos.setAccessible(true);//можно было не париться с рефлексией и просто написать сеттер, но исправлять не буду
            Point prevFruitPos = gameBoard.getFruitPos();
            fruitPos.set(gameBoard, new Point(snake.getHead().x + snake.getDirection().x,
                    snake.getHead().y + snake.getDirection().y));
            snake.move();
            gameBoard.checkCollisions();

            assertEquals(prevSnakeSize + 1, snake.snakePoints.size());
            assertNotEquals(prevScore, snake.score);
            assertNotEquals(prevFruitPos, gameBoard.getFruitPos());
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
        public void testEatingItselfGameOver() {
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

        @Test
        public void testRandomRespawningFruit() throws NoSuchFieldException {
            gameBoard.getClass().getDeclaredField("fruitPos").setAccessible(true);
            Point prevFruitPos = gameBoard.getFruitPos();
            prevFruitPos = new Point(snake.getHead().x+snake.getDirection().x,
                    snake.getHead().y + snake.getDirection().y);
            snake.move();
            gameBoard.checkCollisions();

            assertNotEquals(prevFruitPos, gameBoard.getFruitPos());
        }

//        @Test
//        public void testSpawningSnakeHeadNotCloserThan3Cells(){
//            int headX = snake.getHead().x;
//            int headY = snake.getHead().y;
//
//            if (2 > headX ||
//                    headX > gameBoard.getWidth() - 3 ||
//                    2 > headY ||
//                    headY > gameBoard.getHeight() - 3)
//                fail("Snake is too close to the boundaries");
//        }
    }

    public static class ModelTestsInfinitive {
        private Board gameBoard;
        private Snake snake;

        @Before
        public void testData() {
            GameMode.loadGameMods();
            gameBoard = new Board(20, 20, 6, GameMode.gameMods.get("infinite"));
            snake = gameBoard.snakes[0];
        }

        @Test
        public void testEatingItselfInInfinite() {
            gameBoard.snakes[0] = new Snake(10, 10, Direction.Down, 6,1);
            snake = gameBoard.snakes[0];
            snake.move();
            snake.setDirection(Direction.Right);
            snake.move();
            snake.setDirection(Direction.Up);
            snake.move();
            snake.setDirection(Direction.Left);
            snake.move();
            gameBoard.checkCollisions();

            assertEquals(false, gameBoard.finished);
            assertEquals(4, snake.snakePoints.size());
        }

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
     }

    public class ModelTestMultiplayer {
        private Board gameBoard;
        private Snake snake1;
        private Snake snake2;

        @Before
         public void testData(){
            GameMode.loadGameMods();
            gameBoard = new Board(15,15, 3, GameMode.gameMods.get("twosnakesinf"));
            snake1 = gameBoard.snakes[0];
            snake2 = gameBoard.snakes[1];
        }
    }
}


