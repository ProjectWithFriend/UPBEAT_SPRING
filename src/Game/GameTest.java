package Game;

import Game.GameException.NotImplemented;
import Player.Player;
import Region.EuclidianPoint;
import Region.Point;
import Region.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public final class GameTest {
    private TestPlayer player1, player2;
    private List<TestRegion> territory;
    private GameProps game;

    private static TestRegion mockRegion(Point location) {
        return new TestRegion() {

            @Override
            public Player getOwner() {
                return owner;
            }

            @Override
            public long getDeposit() {
                return deposit;
            }

            @Override
            public void updateDeposit(long amount) {
                deposit += amount;
                if(deposit < 0)
                    deposit = 0;
            }

            @Override
            public void updateOwner(Player owner) {
                this.owner = owner;
            }

            @Override
            public Point getLocation() {
                return location;
            }
        };
    }

    private static List<TestRegion> mockTerritory(int rows, int cols) {
        List<TestRegion> regions = new ArrayList<>(rows * cols);
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                Point location = new EuclidianPoint(i, j);
                regions.add(mockRegion(location));
            }
        }
        return regions;
    }

    private static TestPlayer mockPlayer(TestRegion initCenterLocation) {
        return new TestPlayer(initCenterLocation) {

            @Override
            public boolean isAlive() {
                return true;
            }

            @Override
            public long getBudget() {
                return budget;
            }

            @Override
            public void updateBudget(long amount) {
                budget = Math.max(0, budget + amount);
            }

            @Override
            public String getName() {
                throw new NotImplemented();
            }

            @Override
            public long getID() {
                throw new NotImplemented();
            }

            @Override
            public Map<String, Long> getIdentifiers() {
                return identifiers;
            }

            @Override
            public void relocate(Region location) {
                cityCenter = (TestRegion) location;
            }

            @Override
            public Region getCityCenter() {
                return cityCenter;
            }
        };
    }

    @BeforeEach
    public void before() {
        territory = mockTerritory(4, 4);
        player1 = mockPlayer(territory.get(4));
        player2 = mockPlayer(territory.get(7));
        List<Region> territoryRegion = new ArrayList<>(territory.size());
        territoryRegion.addAll(territory);
        game = new GameProps(territoryRegion, player1, player2, 4, 4);
    }

    @Test
    public void testCollect() {
        for (int i = 0; i < 2; i++) {
            game.beginTurn();
            TestPlayer currentPlayer = i % 2 == 1 ? player2 : player1;
            assertFalse(game.collect(0));

            currentPlayer.budget = (1);
            assertTrue(game.collect(0));
            assertEquals(0, currentPlayer.getBudget());

            currentPlayer.budget = 1;
            assertFalse(game.collect(-1)); // TODO: clarification
            assertEquals(1, currentPlayer.getBudget());

            TestRegion region = (TestRegion) game.getCityCrew();
            region.updateDeposit(100);

            currentPlayer.budget = 2;
            assertTrue(game.collect(0));
            assertEquals(1, currentPlayer.budget);
            assertEquals(100, region.deposit);

            assertTrue(game.collect(1));
            assertEquals(1, currentPlayer.budget);
            assertEquals(99, region.deposit);

            assertTrue(game.collect(2));
            assertEquals(2, currentPlayer.budget);
            assertEquals(97, region.deposit);

            assertTrue(game.collect(98));
            assertEquals(1, currentPlayer.budget);
            assertEquals(97, region.deposit);

            assertTrue(game.collect(97));
            assertEquals(97, currentPlayer.budget);
            assertEquals(0, region.deposit);

            game.endTurn();
        }
    }

    @Test
    public void nearby() {
        player1.updateBudget(1000);
        game.moveCityCrew(Point.of(0, 0));
        assertEquals(0, game.nearby(Direction.Up));
        assertEquals(0, game.nearby(Direction.UpLeft));
        assertEquals(0, game.nearby(Direction.UpRight));
        assertEquals(0, game.nearby(Direction.Down));
        assertEquals(0, game.nearby(Direction.DownLeft));
        assertEquals(0, game.nearby(Direction.DownRight));
        territory.get(11).updateOwner(player2);
        assertEquals(301, game.nearby(Direction.DownRight));
        territory.get(11).updateDeposit(100);
        assertEquals(303, game.nearby(Direction.DownRight));
    }

    @Test
    public void relocate() {
        game.beginTurn();
        player1.updateBudget(1000);
        game.moveCityCrew(Point.of(0, 0));
        game.moveCityCrew(Point.of(3, 2));
        game.relocate();
        assertEquals(974, game.getBudget());
        game.moveCityCrew(Point.of(0, 0));
        game.relocate();
        assertEquals(948, game.getBudget());

        player1.updateBudget(-948);
        game.moveCityCrew(Point.of(3, 2));
        game.relocate();
        assertEquals(0, game.getBudget());
        return;
    }

    @Test
    public void attack() {
        game.beginTurn();
        player1.updateBudget(1000);
        game.moveCityCrew(Point.of(0, 0));
        territory.get(6).updateOwner(player2);
        territory.get(6).updateDeposit(100);
        game.moveCityCrew(Point.of(1, 1));
        game.attack(Direction.DownRight,100);
        assertEquals(899, game.getBudget());
        assertEquals(null, territory.get(6).getOwner());

        //update budget to 0
        player1.updateBudget(-899);
        territory.get(6).updateOwner(player2);
        game.attack(Direction.DownRight,100);
        assertEquals(0, game.getBudget());
        assertEquals(player2, territory.get(6).getOwner());

//        //update budget to 1000
        player1.updateBudget(1000);
        territory.get(1).updateOwner(player2);
        territory.get(1).updateDeposit(10000);
        game.attack(Direction.Up,10000);
        assertEquals(999, game.getBudget());
        assertEquals(player2, territory.get(1).getOwner());
        game.attack(Direction.Up,999);
        assertEquals(998, game.getBudget());
        game.attack(Direction.Up,997);
        assertEquals(0, game.getBudget());
        assertEquals(player2, territory.get(1).getOwner());
        assertEquals(9003, territory.get(1).getDeposit());
    }

    @Test
    public void testInvest() {
        for (int i = 0; i < 2; i++) {
            game.beginTurn();

            TestPlayer currentPlayer = i == 0 ? player1 : player2;
            TestRegion crewRegion = (TestRegion) game.getCityCrew();

            // invest always cost a unit
            currentPlayer.budget = 1;
            game.invest(0);
            assertEquals(0, currentPlayer.budget);
            assertEquals(0, crewRegion.deposit);

            // invest cost x+1 where x amount of invest
            currentPlayer.budget = 2;
            game.invest(1);
            assertEquals(0, currentPlayer.budget);
            assertEquals(1, crewRegion.deposit);

            // invest only allowed when target region have adjacent owned player region
            game.moveCityCrew(Point.of(3, 3)); // no owned adjacent with 2 players
            crewRegion = territory.get(15);
            currentPlayer.budget = 1;
            game.invest(0);
            assertEquals(0, currentPlayer.budget);
            assertEquals(0, crewRegion.deposit);
            game.endTurn();
        }
    }

    @Test
    public void testOpponent() {
        game.beginTurn();
        assertEquals(0, game.opponent());
        game.moveCityCrew(Point.of(3, 2));
        assertEquals(11, game.opponent());
        game.moveCityCrew(Point.of(3, 0));
        assertEquals(14, game.opponent());
        game.moveCityCrew(Point.of(2, 0));
        assertEquals(13, game.opponent());
        game.moveCityCrew(Point.of(1, 0));
        assertEquals(23, game.opponent());
        game.moveCityCrew(Point.of(2, 1));
        assertEquals(12, game.opponent());
        game.moveCityCrew(Point.of(1, 2));
        assertEquals(22, game.opponent());

    }

    private static abstract class TestRegion implements Region {
        public long deposit = 0;
        public Player owner = null;
    }

    private static abstract class TestPlayer implements Player {
        public final Map<String, Long> identifiers = new HashMap<>();
        public TestRegion cityCenter;
        public long budget = 0;

        public TestPlayer(TestRegion cityCenter) {
            cityCenter.updateOwner(this);
            this.cityCenter = cityCenter;
        }
    }
}