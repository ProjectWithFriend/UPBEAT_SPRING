package com.example.springtest.REST_API;

import Game.*;
import Region.*;
import Player.*;
import com.example.springtest.Exception.BadRequestException;
import com.example.springtest.RequestBody.CreateGameReq;
import com.example.springtest.Responses.CreateGameResp;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CreateGame {
    @PostMapping("/createGame")
    public ResponseEntity<Game> createGame(@RequestBody CreateGameReq req) {
        try {
            if (req.getPlayer_2_name() == null || req.getPlayer_1_name() == null) {
                throw new BadRequestException("Player names cannot be null");
            } else {
                Game game = GameUtils.createGame(req.getPlayer_1_name(), req.getPlayer_2_name());
                return ResponseEntity.ok(game);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
