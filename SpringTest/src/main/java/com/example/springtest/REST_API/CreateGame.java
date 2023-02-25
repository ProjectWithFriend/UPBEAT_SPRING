package com.example.springtest.REST_API;

import com.example.springtest.Exception.BadRequestException;
import com.example.springtest.RequestBody.CreateGameReq;
import com.example.springtest.Responses.CreateGameResp;
import com.example.springtest.model.Player;
import com.example.springtest.model.Region;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CreateGame {
    @PostMapping("/createGame")
    public ResponseEntity<Object> createGame(@RequestBody CreateGameReq req) {
        try {
            if (req.getPlayer_2_name() == null || req.getPlayer_1_name() == null) {
                //Handle from frontend already.
                throw new BadRequestException("Player names cannot be null");
            } else {
                //TODO: Create game and return response.
                return null;
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }


    /*
     * Mock API
     */
    @PostMapping("/createGameMock")
    public ResponseEntity<Object> createGameMock() {
        CreateGameResp resp = new CreateGameResp();
        resp.setTerritory(Region.mockRegion());
        resp.setPlayer1(Player.mockPlayer1());
        resp.setPlayer2(Player.mockPlayer2());
        return ResponseEntity.ok(resp);
    }
}
