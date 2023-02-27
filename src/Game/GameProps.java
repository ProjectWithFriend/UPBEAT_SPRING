package Game;

import AST.Node;
import Parser.GrammarParser;
import Parser.Parser;
import Player.Player;
import Region.Point;
import Region.Region;
import Tokenizer.IterateTokenizer;

import java.util.*;

public class GameProps implements Game {
    private final Player player1;
    private final Player player2;
    private final List<Region> territory;
    private final int actionCost = 1;
    private final int rows, cols;
    private Player currentPlayer;
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
        //check if the player has enough budget
        if (currentPlayer.getBudget() < 1)
            return;

        Point currentCityCrewLocation = cityCrew.getLocation();
        Point currentCityCenter = currentPlayer.getCityCenter().getLocation();
        int distance = (int) Math.floor(Math.sqrt(Math.pow(currentCityCrewLocation.getX() - currentCityCenter.getX(), 2) + Math.pow(currentCityCrewLocation.getY() - currentCityCenter.getY(), 2)));
        if (distance < 0) distance = 1;
        int cost = 5 * distance + 10;

        //validate if the player has enough budget
        if (currentPlayer.getBudget() < cost + actionCost) {
            return;
        } else {
            currentPlayer.updateBudget(-cost - actionCost);
            //update the city center location of current player
            currentPlayer.getCityCenter().updateOwner(null);
            cityCrew.updateOwner(currentPlayer);
            currentPlayer.relocate(cityCrew);
        }
    }

    @Override
    public long nearby(Direction direction) {
        Point currentLocation = cityCrew.getLocation();
        int distance = 0;
        Point newLocation = currentLocation.direction(direction);
        while (newLocation.isValidPoint(rows, cols)) {
            Region region = getRegion(newLocation);
            if (region.getOwner() != null && region.getOwner() != currentPlayer)
                return ((distance + 1L) * 100 + (long) (Math.log10(region.getDeposit() + 1)) + 1);
            distance++;
            newLocation = newLocation.direction(direction);
        }
        return 0L;
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
        cityCrew = getRegion(point);
    }

    @Override
    public boolean move(Direction direction) {
        if (currentPlayer.getBudget() < actionCost)
            return false;
        currentPlayer.updateBudget(-actionCost);
        Point newLocation = cityCrew.getLocation().direction(direction);
        if (newLocation.isValidPoint(rows, cols))
            cityCrew = getRegion(newLocation);
        return true;
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
        //validate if the player has enough budget
        if (value + actionCost > currentPlayer.getBudget() || value < 0) {
            currentPlayer.updateBudget(-actionCost);
            return;
        }

        //get vital information
        Point cityCrewLocation = cityCrew.getLocation();
        Point targetLocation = cityCrewLocation.direction(direction);

        //validate if the target location is valid
        if (!targetLocation.isValidPoint(rows, cols)) {
            return;
        } else {
            if (value < territory.get(targetLocation.getY() * cols + targetLocation.getX()).getDeposit()) {
                //update the budget of current player
                currentPlayer.updateBudget(-actionCost - value);
                //update the deposit of the target region
                territory.get(targetLocation.getY() * cols + targetLocation.getX()).updateDeposit(-value);
            } else if (value >= territory.get(targetLocation.getY() * cols + targetLocation.getX()).getDeposit()) {
                territory.get(targetLocation.getY() * cols + targetLocation.getX()).updateDeposit(-value);
                territory.get(targetLocation.getY() * cols + targetLocation.getX()).updateOwner(null);
                currentPlayer.updateBudget(-actionCost - value);
            }
        }
    }
}
