package com.example.springtest.SingelTon;


import Game.*;

public class GameCreation {
    private static Game gameCreation;

    public static Game getGame(String nameP1,String nameP2){
        if(gameCreation == null){
            gameCreation = new GameUtils().createGame(nameP1, nameP2);
            return gameCreation;
        }else{
            return gameCreation;
        }
    }
}
