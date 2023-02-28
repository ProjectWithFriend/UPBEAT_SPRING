package com.example.springtest.Responses;

import com.example.springtest.model.Player;
import com.example.springtest.model.Region;
import lombok.Data;

import java.util.List;

@Data
public class CreateGameResp {
    List<Region> territory;
    Player player1;
    Player player2;
}

