package Game;

import AST.AssignmentNode;
import Parser.*;
import Player.*;
import Region.*;
import Tokenizer.*;
import AST.Node.*;
import Game.GameException.*;

import java.util.*;

public final class GameUtils {
    private static long rows = 20;
    private static long cols = 15;
    private static long initialPlanMinutes = 5;
    private static long initialPlanSeconds = 0;
    private static long initialBudget = 10000;
    private static long initialDeposit = 100;
    private static long revisionPlanMinutes = 30;
    private static long revisionPlanSeconds = 0;
    private static long revisionCost = 100;
    private static long maxDeposit = 1000000;
    private static long interestPercentage = 5;
    private static long id = 1;
    private static List<Region> territory;

    private static Map<String, Long> nodeExecution(ExecNode node) {
        Map<String, Long> map = new HashMap<>();
        while (node != null) {
            if (!(node instanceof AssignmentNode))
                throw new InvalidConfiguration();
            node = ((AssignmentNode) node).execute(map);
        }
        return map;
    }

    private static void mapToVariables(Map<String, Long> map) {
        for (String key : map.keySet()) {
            switch (key) {
                case "m" -> rows = map.get(key);
                case "n" -> cols = map.get(key);
                case "init_plan_min" -> initialPlanMinutes = map.get(key);
                case "init_plan_sec" -> {
                    if (map.get(key) < 0 || map.get(key) > 59)
                        throw new InvalidConfiguration("seconds must be within 0-59");
                    initialPlanSeconds = map.get(key);
                }
                case "init_budget" -> initialBudget = map.get(key);
                case "init_center_dep" -> initialDeposit = map.get(key);
                case "plan_rev_min" -> revisionPlanMinutes = map.get(key);
                case "plan_rev_sec" -> {
                    if (map.get(key) < 0 || map.get(key) > 59)
                        throw new InvalidConfiguration("seconds must be within 0-59");
                    revisionPlanSeconds = map.get(key);
                }
                case "max_dep" -> maxDeposit = map.get(key);
                case "rev_cost" -> revisionCost = map.get(key);
                case "interest_pct" -> interestPercentage = map.get(key);
                default -> throw new InvalidConfiguration(String.format("invalid configuration key '%s'", key));
            }
        }
    }

    public static void loadConfig(String config) {
        Parser parser = new GrammarParser(new IterateTokenizer(config));
        ExecNode node = parser.parse();
        Map<String, Long> map = nodeExecution(node);
        mapToVariables(map);
    }

    /**
     * create new territory from given configuration
     *
     * @return null if not territory else new player
     */
    public static List<Region> createTerritory() {
        territory = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                territory.add(new RegionProps(Point.of(i, j)));
            }
        }
        return territory;
    }

    private static Region pickUnoccupiedRegion() {
        Region region;
        Random random = new Random();
        do {
            int regionIndex = random.nextInt(territory.size());
            region = territory.get(regionIndex);
        } while (region.getOwner() != null);
        return region;
    }

    /**
     * create new a player
     *
     * @return null if no territory else a new player
     */
    public static Player createPlayer(String name) {
        if (territory == null || territory.size() == 0)
            return null;

        Region region = pickUnoccupiedRegion();
        Player player = new PlayerProps(id++, name, initialBudget, region);
        region.updateOwner(player);
        region.updateDeposit(initialDeposit);
        return player;
    }

    public static long getRows() {
        return rows;
    }

    public static long getCols() {
        return cols;
    }

    public static long getInterestPercentage() {
        return interestPercentage;
    }

    public static long getMaxDeposit() {
        return maxDeposit;
    }

    public static long getInitialBudget() {
        return initialBudget;
    }

}
