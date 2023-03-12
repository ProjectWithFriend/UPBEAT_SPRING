package com.example.springtest.RequestBody;

import lombok.Data;

@Data
public class CreateGameReq {
    private String player_1_name;
    private String player_2_name;
    private updateConfig config;
}
