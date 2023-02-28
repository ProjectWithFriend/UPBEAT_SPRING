package Game;

import AST.Node;
import Parser.GrammarParser;
import Parser.Parser;
import Player.Player;
import Region.Point;
import Region.*;
import Tokenizer.IterateTokenizer;

import java.util.*;

public class GameProps implements Game {
    private final Player player1;
    private final Player player2;
    private final List<Region> territory;
    private final int actionCost = 1;
    private Player currentPlayer;
    private Region cityCrew;
    private Map<Player, Region> cityCenterRegion;
    private Region player2CityCenter;
    private final Configuration config;

    public GameProps(Configuration config, List<Region> territory, Player player1, Player player2) {
        this.config = config;
        this.territory = territory;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = this.player1;
        this.cityCenterRegion = new HashMap<>();
    }

    private void getCityCenters() {
        for (Region region : territory) {
            if (region.getIsCityCenter())
                cityCenterRegion.put(region.getOwner(), region);
        }
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
            if (!newLocation.isValidPoint(config.rows(), config.cols()))
                continue;
            adjacentRegions.add(regionAt(newLocation));
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
        Point currentCityCenter = cityCenterRegion.get(currentPlayer).getLocation();
        int distance = (int) Math.floor(Math.sqrt(Math.pow(currentCityCrewLocation.getX() - currentCityCenter.getX(), 2) + Math.pow(currentCityCrewLocation.getY() - currentCityCenter.getY(), 2)));
        if (distance < 0) distance = 1;
        int cost = 5 * distance + 10;

        //validate if the player has enough budget
        if (currentPlayer.getBudget() >= cost + actionCost) {
            currentPlayer.updateBudget(-cost - actionCost);
            //update the city center location of current player
            cityCenterRegion.get(currentPlayer).updateOwner(null);
            cityCrew.updateOwner(currentPlayer);
            cityCenterRegion.put(currentPlayer, cityCrew);
            cityCenterRegion.get(currentPlayer).setCityCenter(currentPlayer);
        }
    }

    @Override
    public long nearby(Direction direction) {
        Point currentLocation = cityCrew.getLocation();
        int distance = 0;
        Point newLocation = currentLocation.direction(direction);
        while (newLocation.isValidPoint(config.rows(), config.cols())) {
            Region region = regionAt(newLocation);
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
                long index = spreads[i].getY() * config.cols() + spreads[i].getX();
                Player owner = territory.get((int) index).getOwner();
                if (owner != null && owner != currentPlayer)
                    return i + 1L + distance * 10L;
                spreads[i] = spreads[i].direction(Direction.values()[i]);
            }
            for (int i = 0; i < 6; i++) {
                if (spreads[i] == null)
                    continue;
                spreads[i] = spreads[i].isValidPoint(config.rows(), config.cols()) ? spreads[i] : null;
            }
            stop = true;
            for (Point point : spreads)
                stop &= point == null;
            distance++;
        } while (!stop);
        return 0;
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
    public Player getPlayer1() {
        return player1;
    }

    @Override
    public Player getPlayer2() {
        return player2;
    }

    @Override
    public Region regionAt(Point point) {
        long index = point.getY() * config.cols() + point.getX();
        return territory.get((int) index);
    }

    @Override
    public long budget() {
        return currentPlayer.getBudget();
    }

    public void beginTurn() {
        getCityCenters();
        cityCrew = cityCenterRegion.get(currentPlayer);
    }

    public void endTurn() {
        if (currentPlayer == player1)
            currentPlayer = player2;
        else
            currentPlayer = player1;
    }

    @Override
    public Region cityCrewRegion() {
        return cityCrew;
    }

    public void moveCityCrew(Point point) {
        if (!point.isValidPoint(config.rows(), config.cols()))
            return;
        cityCrew = regionAt(point);
    }

    @Override
    public boolean move(Direction direction) {
        if (currentPlayer.getBudget() < actionCost)
            return false;
        currentPlayer.updateBudget(-actionCost);
        Point newLocation = cityCrew.getLocation().direction(direction);
        if (newLocation.isValidPoint(config.rows(), config.cols())) {
            Region newRegion = regionAt(newLocation);
            if (newRegion.getOwner() == null || newRegion.getOwner() == currentPlayer)
                cityCrew = newRegion;
        }
        return true;
    }

    @Override
    public Map<String, Long> identifiers() {
        return currentPlayer.identifiers();
    }

    @Override
    public Map<String, Long> specialIdentifiers() {
        Map<String, Long> map = new HashMap<>();
        map.put("rows", config.cols());
        map.put("cols", config.cols());
        map.put("currow", cityCrew.getLocation().getX());
        map.put("curcol", cityCrew.getLocation().getY());
        map.put("budget", currentPlayer.getBudget());
        map.put("deposit", cityCrew.getDeposit());
        map.put("int", config.interestPercentage());
        map.put("maxdeposit", config.maxDeposit());
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
        if (targetLocation.isValidPoint(config.rows(), config.cols())) {
            Region targetRegion = regionAt(targetLocation);
            if (value < targetRegion.getDeposit()) {
                //update the budget of current player
                currentPlayer.updateBudget(-actionCost - value);
                //update the deposit of the target region
                targetRegion.updateDeposit(-value);
            } else if (value >= targetRegion.getDeposit()) {
                targetRegion.updateDeposit(-value);
                targetRegion.updateOwner(null);
                currentPlayer.updateBudget(-actionCost - value);
            }
        }
    }
}
