package com.example.springtest.REST_API;

import com.example.springtest.RequestBody.SubmitPlanReq;
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

            //TODO: get the plan from the request then parse it and return the result
        }catch (Exception e){ //TODO: catch specific exceptions from parsing
            return null;
        }
        return null;
    }
}
