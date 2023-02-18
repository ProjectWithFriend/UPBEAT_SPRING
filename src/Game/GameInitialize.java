package Game;

import Player.*;
import Region.*;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * Need to load configuration file but doesn't have it in this version
 */
public class GameInitialize {
    private static  double row;
    private static  double col;
    private static  double init_plan_min; //number of time to initialize first construction plan
    private static  double init_plan_sec;
    private static  double init_Budget; //initial budget
    private static  double init_center_dep; //initial center deposit
    private static  double plan_rev_min; //Number of time left to play or revision plan
    private static  double plan_rev_sec;
    private static  double rev_cost; //cost to revise plan
    private  static double max_dep; //maximum deposit for each region
    private  static double interest_pct;//interest percentage of budget in each region

    public static List<Region> territory;


    public static void loadConfig(){ //TODO : need to implement this method successfully
        Gson gson = new Gson();
        try(BufferedReader reader = Files.newBufferedReader(Paths.get("D:\\coding\\OOP\\Project\\UPBEAT\\src\\Game\\config.json"))){
            Map game = gson.fromJson(reader, Map.class);
            row = (double) game.get("row");
            col = (double) game.get("col");
            init_plan_min = (double) game.get("init_plan_min");
            init_plan_sec = (double) game.get("init_plan_sec");
            init_Budget = (double) game.get("init_budget");
            init_center_dep = (double) game.get("init_center_dep");
            plan_rev_min = (double) game.get("plan_rev_min");
            plan_rev_sec = (double) game.get("plan_rev_sec");
            rev_cost = (double) game.get("rev_cost");
            max_dep = (double) game.get("max_dep");
            interest_pct = (double) game.get("interest_pct");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Region> CreateTerritory(){
        territory = new ArrayList<Region>();
        for(int i = 0 ; i < row*col ; i++){
            territory.add(new RegionProps(i));
        }
        return territory;
    }

    public static Player CreatePlayer(String name){
        return new PlayerProps(name,init_Budget,init_center_dep);
    }
}
