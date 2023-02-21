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
    private static List<Region> mockTerritory(int rows, int cols) {
        List<Region> regions = new ArrayList<>(rows * cols);
        for (int i = 0; i < rows * cols; i++) {
            int location = i;
            regions.add(new Region() {
                private long deposit = 0;
                private Player owner = null;

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

    private static abstract class TestPlayer implements Player{

        public TestPlayer(int initCenterLocation) {
            cityCrewLocation = initCenterLocation;
            cityCenterLocation = initCenterLocation;
        }
        public long budget = 0;
        public int cityCenterLocation;
        public int cityCrewLocation;
        public final Map<String, Long> identifiers = new HashMap<>();
        public void setCityCrewLocation(int location) {
            cityCrewLocation = location;
        }

    }

    private static TestPlayer mockPlayer(int initCenterLocation) {
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
                budget += amount;
            }

            @Override
            public void moveCityCrew(Direction direction) {
                cityCrewLocation += GameUtils.deltaTable().get(direction);
            }

            public void setCityCrew(int location) {
                cityCrewLocation = location;
            }

            @Override
            public int getCityCenter() {
                return cityCenterLocation;
            }

            @Override
            public int getCityCrew() {
                return cityCrewLocation;
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
        };
    }

    private TestPlayer player1, player2;
    private List<Region> territory;
    private Game game;

    @BeforeEach
    public void before() {
        territory = mockTerritory(4, 4);
        player1 = mockPlayer(4);
        player2 = mockPlayer(7);
        game = new GameProps(territory, player1, player2);
    }

    @Test
    public void testCollect() {
        assertFalse(game.collect(0));

        player1.updateBudget(1);
        assertTrue(game.collect(0));
        assertEquals(0, player1.getBudget());

        player1.updateBudget(1);
        assertFalse(game.collect(-1)); // TODO: clarification
        assertEquals(1, player1.getBudget());

        Region region = territory.get(player1.getCityCrew());
        region.updateDeposit(100);

        player1.updateBudget(1);
        assertTrue(game.collect(0));
        assertEquals(1, player1.getBudget());
        assertEquals(100, region.getDeposit());

        assertTrue(game.collect(1));
        assertEquals(1, player1.getBudget());
        assertEquals(99, region.getDeposit());

        assertTrue(game.collect(2));
        assertEquals(2, player1.getBudget());
        assertEquals(97, region.getDeposit());

        assertTrue(game.collect(98));
        assertEquals(1, player1.getBudget());
        assertEquals(97, region.getDeposit());

        assertTrue(game.collect(97));
        assertEquals(97, player1.getBudget());
        assertEquals(0, region.getDeposit());
    }

    @Test
    public void attack(){
        player1.updateBudget(1000);
        territory.get(player2.getCityCenter()).updateDeposit(10);
        player1.setCityCrewLocation(6);
        game.attack(Direction.UpRight, 10);
        assertEquals(989, player1.getBudget()); //should be 990 - action cost(1) = 989
        assertDoesNotThrow(() -> game.attack(Direction.UpRight, 10));
    }
}