package com.example.springtest.RequestBody;

import lombok.Data;

@Data
public class updateConfig {
    private long rows;
    private long cols;
    private long initPlanMin;
    private long initPlanSec;
    private long initBudget;
    private long initCenterDep;
    private long planRevMin;
    private long planRevSec;
    private long revCost;
    private long maxDep;
    private long interestPct;
}
