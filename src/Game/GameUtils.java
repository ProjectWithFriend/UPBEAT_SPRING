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

    private static List<Region> territory;

    private static Map<String, Long> nodesEvaluation(ExecNode node) {
        Map<String, Long> map = new HashMap<>();
        while (node != null) {
            if (!(node instanceof AssignmentNode))
                throw new InvalidConfiguration();
            node = ((AssignmentNode) node).execute(map);
        }
        return map;
    }

    private static Configuration configuration;

    public static Configuration loadConfig(String config) {
        Parser parser = new GrammarParser(new IterateTokenizer(config));
        ExecNode node = parser.parse();
        Map<String, Long> map = nodesEvaluation(node);
        configuration = new Configuration() {
            @Override
            public long rows() {
                return map.getOrDefault("m", 20L);
            }

            @Override
            public long cols() {
                return map.getOrDefault("n", 15L);
            }

            @Override
            public long initialPlanMinutes() {
                return map.getOrDefault("init_plan_min", 5L);
            }

            @Override
            public long initialPlanSeconds() {
                return map.getOrDefault("init_plan_sec", 0L);
            }

            @Override
            public long initialBudget() {
                return map.getOrDefault("init_budget", 10000L);
            }

            @Override
            public long initialDeposit() {
                return map.getOrDefault("init_center_dep", 100L);
            }

            @Override
            public long revisionPlanMinutes() {
                return map.getOrDefault("plan_rev_min", 30L);
            }

            @Override
            public long revisionPlanSeconds() {
                return map.getOrDefault("plan_rev_sec", 0L);
            }

            @Override
            public long revisionCost() {
                return map.getOrDefault("rev_cost", 100L);
            }

            @Override
            public long maxDeposit() {
                return map.getOrDefault("max_dep", 1000000L);
            }

            @Override
            public long interestPercentage() {
                return map.getOrDefault("interest_pct", 5L);
            }
        };
        return configuration;
    }

    /**
     * create new territory from given configuration
     *
     * @return null if not territory else new player
     */
    public static List<Region> createTerritory() {
        territory = new ArrayList<>();
        for (int i = 0; i < configuration.rows(); i++) {
            for (int j = 0; j < configuration.cols(); j++) {
                territory.add(new RegionProps(Point.of(j, i)));
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

    private static int id = 1;
    /**
     * create new a player
     *
     * @return null if no territory else a new player
     */
    public static Player createPlayer(String name) {
        if (territory == null || territory.size() == 0)
            return null;

        Region region = pickUnoccupiedRegion();
        Player player = new PlayerProps(id++, name, configuration.initialBudget(), region);
        region.updateOwner(player);
        region.updateDeposit(configuration.initialDeposit());
        return player;
    }

    /**
     * create new game instance
     * @param namePlayer1 name of player 1
     * @param namePlayer2 name of player 2
     * @return instance of the game
     */
    public static Game createGame(String namePlayer1, String namePlayer2) {
        List<Region> territory = createTerritory();
        Player player1 = createPlayer(namePlayer1);
        Player player2 = createPlayer(namePlayer2);
        return new GameProps(configuration, territory, player1, player2);
    }
}
