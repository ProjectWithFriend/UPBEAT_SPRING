package Game;

import AST.Node;
import Parser.GrammarParser;
import Parser.Parser;
import Player.Player;
import Region.*;
import Tokenizer.IterateTokenizer;

import java.util.*;

public class GameProps implements Game {
    private final Player player1;
    private final Player player2;
    private final List<Region> territory;
    private Player currentPlayer;
    private final int actionCost = 1;
    private final int rows, cols;
    private Region cityCrew;

    public GameProps(List<Region> territory, Player player1, Player player2, int rows, int cols) {
        this.territory = territory;
        this.player1 = player1;
        this.player2 = player2;
        this.rows = rows;
        this.cols = cols;
        this.currentPlayer = this.player1;
    }

    @Override
    public boolean collect(long value) {
        if (currentPlayer.getBudget() < 1 || value < 0)
            return false;
        currentPlayer.updateBudget(-1);
        Region targetRegion = cityCrew;
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
        Point currentLocation = region.getLocation();
        for (Direction direction : Direction.values()) {
            Point newLocation = currentLocation.direction(direction);
            if (!newLocation.isValidPoint(rows, cols))
                continue;
            adjacentRegions.add(getRegion(newLocation));
        }
        return adjacentRegions;
    }

    @Override
    public void invest(long value) {
        currentPlayer.updateBudget(-1);
        boolean atLeastOneAdjacent = cityCrew.getOwner() == currentPlayer;
        for (Region adjacent : getAdjacentRegions(cityCrew)) {
            if (atLeastOneAdjacent) break;
            atLeastOneAdjacent = adjacent.getOwner() == currentPlayer;
        }
        if (!atLeastOneAdjacent) // adjacency requirement
            return;
        if (cityCrew.getOwner() != currentPlayer) // no occupation requirement
            return;
        if (currentPlayer.getBudget() < value) // budget requirement
            return;
        currentPlayer.updateBudget(-value);
        cityCrew.updateOwner(currentPlayer);
        cityCrew.updateDeposit(value);
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
        Point[] spreads = new Point[6];
        int distance = 0;
        boolean stop;
        for (int i = 0; i < 6; i++)
            spreads[i] = cityCrew.getLocation();
        do {
            for (int i = 0; i < 6; i++) {
                if (spreads[i] == null)
                    continue;
                int index = spreads[i].getY() * cols + spreads[i].getX();
                Player owner = territory.get(index).getOwner();
                if (owner != null && owner != currentPlayer)
                    return i + 1L + distance * 10L;
                spreads[i] = spreads[i].direction(Direction.values()[i]);
            }
            for (int i = 0; i < 6; i++) {
                if (spreads[i] == null)
                    continue;
                spreads[i] = spreads[i].isValidPoint(rows, cols) ? spreads[i] : null;
            }
            stop = true;
            for (Point point : spreads)
                stop &= point == null;
            distance++;
        } while (!stop);
        return 0;
    }

    @Override
    public long getCurrentPlayerID() {
        return currentPlayer.getID();
    }

    @Override
    public void submitPlan(String constructionPlan) {
        Parser parser = new GrammarParser(new IterateTokenizer(constructionPlan));
        Node.ExecNode node = parser.parse();
        beginTurn();
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
    public Region getRegion(Point point) {
        return territory.get(point.getY() * cols + point.getX());
    }

    @Override
    public long getBudget() {
        return currentPlayer.getBudget();
    }

    public void beginTurn() {
        cityCrew = currentPlayer.getCityCenter();
    }

    public void endTurn() {
        if (currentPlayer == player1)
            currentPlayer = player2;
        else
            currentPlayer = player1;
    }

    @Override
    public Region getCityCrew() {
        return cityCrew;
    }

    public void moveCityCrew(Point point) {
        if (!point.isValidPoint(rows, cols))
            return;
        cityCrew = territory.get(point.getY() * cols + point.getX());
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
        map.put("currow", (long) cityCrew.getLocation().getX());
        map.put("curcol", (long) cityCrew.getLocation().getY());
        map.put("budget", currentPlayer.getBudget());
        map.put("deposit", cityCrew.getDeposit());
        map.put("int", GameUtils.getInterestPercentage());
        map.put("maxdeposit", GameUtils.getMaxDeposit());
        map.put("random", new Random().nextLong(1000));
        return map;
    }

    @Override
    public void attack(Direction direction, long value) {
        throw new GameException.NotImplemented();
//        //validate caller budget
//        if (value + actionCost < currentPlayer.getBudget() || value < 0) {
//            return;
//        }
//
//        //get nessary data
//        int cityCrewLocation = cityCrewRegion().getLocation();
//        int targetLocation = cityCrewLocation + GameUtils.deltaTable(cityCrewLocation).get(direction);
//
//        //check if target location is valid
//        if (!isValidLocation(targetLocation)) {
//            return;
//        } else {
//            if (value < territory.get(targetLocation).getDeposit()) {
//                territory.get(targetLocation).updateDeposit(-value);
//                currentPlayer.updateBudget(-value - actionCost);
//            } else if (value >= territory.get(targetLocation).getDeposit()) {
//                territory.get(targetLocation).updateDeposit(0);
//                territory.get(targetLocation).updateOwner(null);
//                currentPlayer.updateBudget(-value - actionCost);
//            }
//        }
    }
}
