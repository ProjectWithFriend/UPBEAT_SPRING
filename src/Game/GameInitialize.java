package Game;

import Region.Region;

import java.util.List;

/*
 * Need to load configuration file but doesn't have it in this version
 */
public class GameInitialize {
    private static final int row = 5;
    private static final int col = 5;
    private static final int plan_min = 5; //number of time to initialize first construction plan
    private static final int plan_sec = 0;
    private static final double init_Budget = 10000; //initial budget
    private static final double init_center_dep = 100; //initial center deposit
    private static final int init_plan_rev_min = 30; //Number of time left to play or revision plan
    private static final int init_plan_rev_sec = 0;
    private static final double rev_cost = 100; //cost to revise plan
    private final double max_dep = 1000000; //maximum deposit for each region
    private final double interest_pct= 5; //interest percentage of budget in each region

    public List<Region> territory;


    public static void loadConfig(){
        //TODO: load configuration file
    }

    public static List<Region> CreateGame(){
        //TODO: - Create Territory
        //      - set each filed same as config file
        return null;
    }
}
