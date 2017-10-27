package com.snakegame.model;

import java.util.HashMap;

public class GameMode {
    public Fruit[] usingFruits;
    public boolean infMode;
    public int snakeCount;

    private GameMode(Fruit[] fruits, boolean inf, int count) {
        usingFruits = fruits;
        infMode = inf;
        snakeCount = count;
    }
    public static void loadGameMods() {


        GameMode classic = new GameMode(new Fruit[]{Fruit.apple}, false, 1);
        GameMode infinitive = new GameMode(new Fruit[]{Fruit.apple, Fruit.pear}, true, 1);
        GameMode twoSnakesInf = new GameMode(new Fruit[]{Fruit.apple}, true, 2);

        gameMods.put("classic", classic);
        gameMods.put("infinite", infinitive);
        gameMods.put("twosnakesinf", twoSnakesInf);
    }
    public static HashMap<String, GameMode> gameMods = new HashMap<>();
}
