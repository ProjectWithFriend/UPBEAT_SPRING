package com.example.springtest.SingelTon;


import Game.*;

import java.util.HashMap;
import java.util.Map;

public class GameCreation {
    private static Game gameCreation;
    private static final Map<String,Game> map = new HashMap<>();

    public static Game getGame(String nameP1,String nameP2,String configString){
        String gameHash = nameP1 + nameP2;
        if(map.isEmpty() || !map.containsKey(gameHash)){
//            gameCreation = GameUtils.createGame(nameP1, nameP2);
            gameCreation = GameUtils.createCustomGame(configString,nameP1,nameP2);
            map.put(gameHash,gameCreation);
            return gameCreation;
        }else{
            return map.get(gameHash);
        }
//        return null;
    }
}
