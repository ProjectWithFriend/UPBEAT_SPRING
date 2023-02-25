package com.example.springtest.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Region {
    Player owner;
    double deposit;
    int[] location;


    /*
     * mock region for testing
     */
    public static List<Region> mockRegion() {
        List<Region> mockTerritory = new ArrayList<>();
        Player player1 = Player.mockPlayer1();
        Player player2 = Player.mockPlayer2();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(i == 0 && j == 0){
                    Region region = new Region();
                    region.setOwner(player1);
                    region.setDeposit(100);
                    region.setLocation(new int[]{i, j});
                    mockTerritory.add(region);
                }else if(i == 3 && j == 3) {
                    Region region = new Region();
                    region.setOwner(player2);
                    region.setDeposit(100);
                    region.setLocation(new int[]{i, j});
                    mockTerritory.add(region);
                }else{
                    Region region = new Region();
                    region.setOwner(null);
                    region.setDeposit(100);
                    region.setLocation(new int[]{i, j});
                    mockTerritory.add(region);
                }
            }
        }
        return mockTerritory;
    }
}
