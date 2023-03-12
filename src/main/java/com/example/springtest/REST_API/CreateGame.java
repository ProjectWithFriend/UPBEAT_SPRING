package com.example.springtest.REST_API;

import Game.*;
import com.example.springtest.BusinessLogic.Plan;
import com.example.springtest.Exception.BadRequestException;
import com.example.springtest.RequestBody.CreateGameReq;
import com.example.springtest.RequestBody.updateConfig;
import com.example.springtest.SingelTon.GameCreation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class CreateGame {

    //get to game data to submitPlan
    @PostMapping("/createGame")
    public ResponseEntity<Game> createGame(@RequestBody CreateGameReq req) {
        try {
            if (req.getPlayer_2_name() == null || req.getPlayer_1_name() == null) {
                throw new BadRequestException("Player names cannot be null");
            } else {
                String configString = convertConfigToString(req.getConfig());
                Game game = GameCreation.getGame(req.getPlayer_1_name(), req.getPlayer_2_name(), configString);
                Plan.setGame(game); //set the game to plan use when submit plan
                return ResponseEntity.ok(game);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private String convertConfigToString(updateConfig config){
        String configString = "";
        configString += "m=" + config.getRows() + "\n";
        configString += "n=" + config.getCols() + "\n";
        configString += "init_plan_min=" + config.getInitPlanMin() + "\n";
        configString += "init_plan_sec=" + config.getInitPlanSec() + "\n";
        configString += "init_budget=" + config.getInitBudget() + "\n";
        configString += "init_center_dep=" + config.getInitCenterDep() + "\n";
        configString += "plan_rev_min=" + config.getPlanRevMin() + "\n";
        configString += "plan_rev_sec=" + config.getPlanRevSec() + "\n";
        configString += "rev_cost=" + config.getRevCost() + "\n";
        configString += "max_dep=" + config.getMaxDep() + "\n";
        configString += "interest_pct=" + config.getInterestPct() + "\n";
        return configString;
    }
}
