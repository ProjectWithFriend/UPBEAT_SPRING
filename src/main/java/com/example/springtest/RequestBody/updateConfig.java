package com.example.springtest.RequestBody;

import lombok.Data;

@Data
public class updateConfig {
    private int rows;
    private int cols;
    private int initPlanMin;
    private int initPlanSec;
    private double initBudget;
    private double initCenterDep;
    private int planRevMin;
    private int planRevSec;
    private double revCost;
    private double maxDep;
    private double interestPct;
}
