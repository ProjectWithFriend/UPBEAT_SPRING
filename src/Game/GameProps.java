package Game;

import AST.Node;
import Parser.*;
import Player.*;
import Region.*;
import Tokenizer.IterateTokenizer;

import java.util.*;

public class GameProps implements Game {
    private final Player player1;
    private final Player player2;
    private final List<Region> territory;
    private Player currentPlayer;

    public GameProps(String player1Name, String player2Name) {
        this.territory = GameUtils.createTerritory();
        this.player1 = GameUtils.createPlayer(player1Name);
        this.player2 = GameUtils.createPlayer(player2Name);
        this.currentPlayer = this.player1;
    }

    private Region cityCrewRegion() {
        return territory.get(currentPlayer.getCityCrew());
    }

    private Region cityCenterRegion() {
        return territory.get(currentPlayer.getCityCenter());
    }

    @Override
    public void collect(long value) {
        if (value <= 0)
            throw new GameException.InvalidValue(value);
        if (currentPlayer.getBudget() < 1)
            return;
        Region targetRegion = cityCrewRegion();
        if (value > targetRegion.getDeposit())
            return;
        targetRegion.updateDeposit(-value);
        currentPlayer.updateBudget(value);
        if (targetRegion.getDeposit() == 0)
            targetRegion.updateOwner(null);
    }

    private static Map<Direction, Integer> deltaTable() {
        Map<Direction, Integer> map = new HashMap<>();
        map.put(Direction.Up, -GameUtils.getColsInt());
        map.put(Direction.Down, GameUtils.getColsInt());
        map.put(Direction.UpLeft, -GameUtils.getColsInt() - 1);
        map.put(Direction.UpRight, -GameUtils.getColsInt() + 1);
        map.put(Direction.DownLeft, -1);
        map.put(Direction.DownRight, 1);
        return map;
    }

    private List<Region> getAdjacentRegions(Region region) {
        List<Region> adjacentRegions = new LinkedList<>();
        for (Integer delta : deltaTable().values()) {
            if (region.getLocation() + delta < 0)
                continue;
            adjacentRegions.add(getRegion(region.getLocation() + delta));
        }
        return adjacentRegions;
    }

    @Override
    public void invest(long value) {
        boolean atLeastOneAdjacent = false;
        Region crewRegion = cityCrewRegion();
        for (Region adjacent : getAdjacentRegions(crewRegion))
            atLeastOneAdjacent |= adjacent.getOwner() == currentPlayer;
        if (!atLeastOneAdjacent) // adjacency requirement
            return;
        if (crewRegion.getOwner() != currentPlayer) // no occupation requirement
            return;
        if (currentPlayer.getBudget() < value + 1) // budget requirement
            return;
        currentPlayer.updateBudget(-value - 1);
        crewRegion.updateOwner(currentPlayer);
        crewRegion.updateDeposit(value);
    }

    @Override
    public void relocate() {
        throw new GameException.NotImplemented();
    }

    @Override
    public long nearby(Direction direction) {
        throw new GameException.NotImplemented();
    }

    @Override
    public long opponent() {
        throw new GameException.NotImplemented();
    }

    @Override
    public long getCurrentPlayerID() {
        return currentPlayer.getID();
    }

    @Override
    public void submitPlan(String constructionPlan) {
        Parser parser = new GrammarParser(new IterateTokenizer(constructionPlan));
        Node.ExecNode node = parser.parse();
        while (node != null) {
            node = node.execute(this);
        }
        endTurn();
    }

    @Override
    public List<Region> getTerritory() {
        return territory;
    }

    @Override
    public Region getRegion(int index) {
        return territory.get(index);
    }

    @Override
    public long getBudget() {
        return currentPlayer.getBudget();
    }

    @Override
    public void endTurn() {
        if (currentPlayer == player1)
            currentPlayer = player2;
        else
            currentPlayer = player1;
    }

    @Override
    public boolean move(Direction direction) {
        throw new GameException.NotImplemented();
    }

    @Override
    public Map<String, Long> getIdentifiers() {
        return currentPlayer.getIdentifiers();
    }

    @Override
    public Map<String, Long> getSpecialIdentifiers() {
        Map<String, Long> map = new HashMap<>();
        map.put("rows", GameUtils.getRows());
        map.put("cols", GameUtils.getCols());
        map.put("currow", cityCrewRegion().getLocation() / GameUtils.getCols());
        map.put("curcol", cityCrewRegion().getLocation() % GameUtils.getCols());
        map.put("budget", currentPlayer.getBudget());
        map.put("deposit", cityCrewRegion().getDeposit());
        map.put("int", GameUtils.getInterestPercentage());
        map.put("maxdeposit", GameUtils.getMaxDeposit());
        map.put("random", new Random().nextLong(1000));
        return map;
    }

    @Override
    public void attack(Direction direction, long value) {
        throw new GameException.NotImplemented();
    }
}
