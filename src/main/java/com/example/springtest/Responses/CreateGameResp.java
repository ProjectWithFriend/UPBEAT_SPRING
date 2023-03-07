package com.example.springtest.Responses;

import Region.*;
import Player.*;
import lombok.Data;

import javax.swing.plaf.synth.Region;
import java.util.List;

@Data
public class CreateGameResp {
    List<Region> territory;
    Player player1;
    Player player2;
}

