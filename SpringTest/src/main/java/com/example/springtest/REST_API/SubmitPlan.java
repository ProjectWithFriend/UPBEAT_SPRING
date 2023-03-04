package com.example.springtest.REST_API;

import Game.*;
import com.example.springtest.BusinessLogic.Plan;
import com.example.springtest.Exception.BadSubmitPlanException;
import com.example.springtest.RequestBody.SubmitPlanReq;
import com.example.springtest.websocket_route.GamePage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class SubmitPlan {

    @PostMapping("/submit_plan")
    public ResponseEntity<Object> submitPlan(@RequestBody SubmitPlanReq submitPlanReq) {
        try {
            //validate the request
            if(submitPlanReq.getConstruction_plan() == null || submitPlanReq.getConstruction_plan().isEmpty()){
                return ResponseEntity.badRequest().body("Construction plan is empty");
            }
            if(submitPlanReq.getPlayer_id() == 0){
                return ResponseEntity.badRequest().body("Player id is empty");
            }
            Game updateGame = Plan.submitPlan(submitPlanReq.getConstruction_plan(), submitPlanReq.getPlayer_id());
            GamePage.broadcastGameUpdate(updateGame); //TODO : discuss with team about this
            return ResponseEntity.ok(updateGame);
        }catch (RuntimeException e){ //TODO: catch specific exceptions from parsing
            return null;
        }
//        return null;
    }


    //TODO : mock test for this class
    @PostMapping("/submit_plan_mock")
    public ResponseEntity<Object> submitPlanMock(@RequestBody SubmitPlanReq submitPlanReq) {
        try {
            //validate the request
            if(submitPlanReq.getConstruction_plan() == null || submitPlanReq.getConstruction_plan().isEmpty()){
                throw new BadSubmitPlanException("Construction plan is empty");
            }else if(submitPlanReq.getPlayer_id() == 0){
                throw new BadSubmitPlanException("Player id is empty");
            }
            Game updategame = Plan.submitPlanMock(submitPlanReq.getConstruction_plan(), submitPlanReq.getPlayer_id());
            GamePage.broadcastGameUpdate(updategame); //TODO : discuss with team about this
            return ResponseEntity.ok(updategame);
        }catch (RuntimeException e){ //TODO: catch specific exceptions from parsing
            throw new BadSubmitPlanException(e.getMessage());
        }
//        return null;
    }
}
