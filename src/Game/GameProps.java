package Game;

import AST.Node;
import Parser.GrammarParser;
import Parser.Parser;
import Player.Player;
import Region.Region;
import Tokenizer.IterateTokenizer;

import java.util.*;

public class GameProps implements Game {
    private final Player player1;
    private final Player player2;
    private final List<Region> territory;
    private Player currentPlayer;
    private final int actionCost = 1;

    public GameProps(List<Region> territory, Player player1, Player player2) {
        this.territory = territory;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = this.player1;
    }

    private Region cityCrewRegion() {
        return territory.get(currentPlayer.getCityCrew());
    }

    private Region cityCenterRegion() {
        return territory.get(currentPlayer.getCityCenter());
    }

    @Override
    public boolean collect(long value) {
        if (currentPlayer.getBudget() < 1 || value < 0)
            return false;
        currentPlayer.updateBudget(-1);
        Region targetRegion = cityCrewRegion();
        if (value > targetRegion.getDeposit())
            return true;
        targetRegion.updateDeposit(-value);
        currentPlayer.updateBudget(value);
        if (targetRegion.getDeposit() == 0)
            targetRegion.updateOwner(null);
        return true;
    }

    private List<Region> getAdjacentRegions(Region region) {
        List<Region> adjacentRegions = new LinkedList<>();
        for (Integer delta : GameUtils.deltaTable().values()) {
            int targetLocation = region.getLocation() + delta;
            if (!isValidLocation(targetLocation))
                continue;
            adjacentRegions.add(getRegion(targetLocation));
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
        //validate caller budget
        if(value + actionCost < currentPlayer.getBudget() || value < 0){
            return;
        }

        //get nessary data
        int cityCrewLocation = cityCrewRegion().getLocation();
        int targetLocation = cityCrewLocation + GameUtils.deltaTable().get(direction);

        //check if target location is valid
        if(!isValidLocation(targetLocation)){
            return;
        }else{
            if(value < territory.get(targetLocation).getDeposit()) {
                territory.get(targetLocation).updateDeposit(-value);
                currentPlayer.updateBudget(-value - actionCost);
            }else if(value >= territory.get(targetLocation).getDeposit()){
                territory.get(targetLocation).updateDeposit(0);
                territory.get(targetLocation).updateOwner(null);
                currentPlayer.updateBudget(-value - actionCost);
            }
        }
    }

    /*
     added slome method
     */
    public boolean isValidLocation(int location) {
        return location >= 0 && location < territory.size();
    }
}
