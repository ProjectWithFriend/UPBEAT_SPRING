package com.example.springtest.Business;

import org.springframework.stereotype.Service;

@Service
public class RandomCT {
    private int[] cities;

    public int[] getCities(){
        cities = new int[2];
        //randomly generate two cities 0 - 101
        cities[0] = (int)(Math.random() * 101);
        cities[1] = (int)(Math.random() * 101);
        return cities;
    }
}
