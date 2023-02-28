package com.example.springtest.model;

import lombok.Data;

@Data
public class Player {
    int player_id;
    String name;
    int[] cityCenter;
    double budget;


    /*
     *mock player
     */
    public static Player mockPlayer1() {
        Player player = new Player();
        player.setPlayer_id(1);
        player.setName("player1");
        player.setCityCenter(new int[]{0, 0});
        player.setBudget(100);
        return player;
    }

    public static Player mockPlayer2() {
        Player player = new Player();
        player.setPlayer_id(2);
        player.setName("player2");
        player.setCityCenter(new int[]{3, 3});
        player.setBudget(100);
        return player;
    }
}
