package Game;

import Player.Player;

import Region.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public final class GameTest {
    private static abstract class TestRegion implements Region {
        public long deposit = 0;
        public Player owner = null;
    }

    private static List<TestRegion> mockTerritory(int rows, int cols) {
        List<TestRegion> regions = new ArrayList<>(rows * cols);
        for (int i = 0; i < rows * cols; i++) {
            int location = i;
            regions.add(new TestRegion() {

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
                }

                @Override
                public void updateOwner(Player owner) {
                    this.owner = owner;
                }

                @Override
                public int getLocation() {
                    return location;
                }
            });
        }
        return regions;
    }
    private static TestPlayer mockPlayer(List<TestRegion> territory, int initCenterLocation) {
        return new TestPlayer(territory, initCenterLocation) {

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
            public void moveCityCrew(Direction direction) {
                cityCrewLocation += GameUtils.deltaTable(cityCrewLocation).get(direction);
            }

            @Override
            public int getCityCenter() {
                return cityCenterLocation;
            }

            @Override
            public int getCityCrew() {
                return cityCrewLocation;
            }

            public void setCityCrew(int location) {
                cityCrewLocation = location;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public long getID() {
                return 0;
            }

            @Override
            public Map<String, Long> getIdentifiers() {
                return identifiers;
            }

            @Override
            public void relocate(int location) {
                cityCenterLocation = location;
            }

            @Override
            public void endTurn() {
                cityCrewLocation = cityCenterLocation;
            }
        };
    }

    private TestPlayer player1, player2;
    private List<TestRegion> territory;
    private Game game;

    @BeforeEach
    public void before() {
        territory = mockTerritory(4, 4);
        player1 = mockPlayer(territory, 4);
        player2 = mockPlayer(territory, 7);
        List<Region> territoryRegion = new ArrayList<>(territory.size());
        territoryRegion.addAll(territory);
        game = new GameProps(territoryRegion, player1, player2);
    }

    @Test
    public void testCollect() {
        for (int i = 0; i < 2; i++) {
            TestPlayer currentPlayer = i % 2 == 1 ? player2 : player1;
            assertFalse(game.collect(0));

            currentPlayer.budget = (1);
            assertTrue(game.collect(0));
            assertEquals(0, currentPlayer.getBudget());

            currentPlayer.budget = 1;
            assertFalse(game.collect(-1)); // TODO: clarification
            assertEquals(1, currentPlayer.getBudget());

            TestRegion region = territory.get(currentPlayer.getCityCrew());
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

    /*
     a bit for testing attack method
     TODO: test more cases
     */
    @Test
    public void attack() {
        player1.updateBudget(1000);
        player1.budget = 1000;
        territory.get(player2.getCityCenter()).updateDeposit(10);
        player1.cityCrewLocation = 6;
        game.attack(Direction.UpRight, 10);
        assertEquals(989, player1.getBudget()); //should be 990 - action cost(1) = 989
    }

    @Test
    public void nearby() {
        player1.setCityCrewLocation(0);
        game.getTerritory().get(0).updateOwner(player1);
        game.getTerritory().get(6).updateOwner(player2);
        game.getTerritory().get(6).updateDeposit(100);
        System.out.println(game.nearby(Direction.DownRight));
    }

    private static abstract class TestPlayer implements Player {
        public final Map<String, Long> identifiers = new HashMap<>();
        public long budget = 0;
        public int cityCenterLocation;
        public int cityCrewLocation;
        public TestPlayer(List<TestRegion> territory, int initCenterLocation) {
            cityCrewLocation = initCenterLocation;
            cityCenterLocation = initCenterLocation;
        }

        public void setCityCrewLocation(int location) {
            cityCrewLocation = location;
        }

    }

    @Test
    public void testInvest() {
        TestPlayer currentPlayer = player1;
        TestRegion crewRegion = territory.get(currentPlayer.getCityCrew());

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
        currentPlayer.cityCrewLocation = 15; // no owned adjacent with 2 players
        crewRegion = territory.get(15);
        currentPlayer.budget = 1;
        game.invest(0);
        assertEquals(0, currentPlayer.budget);
        assertEquals(0, crewRegion.deposit);
    }

    @Test
    public void testOpponent() {

    }
}