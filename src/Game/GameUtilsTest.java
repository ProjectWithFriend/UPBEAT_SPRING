package Game;

import Game.GameException.*;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public final class GameUtilsTest {
    @Test
    public void testLoadConfig() {
        assertDoesNotThrow(() -> GameUtils.loadConfig("""
                m=1
                n=1
                init_plan_min=1
                init_plan_sec=1
                init_budget=1
                init_center_dep=1
                plan_rev_min=1
                plan_rev_sec=1
                rev_cost=1
                max_dep=1
                interest_pct=1
                """));
        assertDoesNotThrow(() -> GameUtils.loadConfig("""
                m=1
                n=1
                """));
        assertThrows(InvalidConfiguration.class, () -> GameUtils.loadConfig("""
                plan_rev_sec=60
                """));
    }

    @Test
    public void testCreateTerritory() {
        GameUtils.loadConfig("m=1 n=1");
        assertEquals(1, GameUtils.createTerritory().size());

        GameUtils.loadConfig("m=10 n=10");
        assertEquals(100, GameUtils.createTerritory().size());
    }

    @Test
    public void testCreatePlayer() {
        assertNull(GameUtils.createPlayer(""));

        GameUtils.loadConfig("m=0 n=0");
        GameUtils.createTerritory();
        assertNull(GameUtils.createPlayer(""));

        GameUtils.loadConfig("m=1 n=1");
        GameUtils.createTerritory();
        assertNotNull(GameUtils.createPlayer(""));

        GameUtils.loadConfig("m=10 n=10");
        GameUtils.createTerritory();
        assertNotNull(GameUtils.createPlayer(""));
        assertNotEquals(
                Objects.requireNonNull(GameUtils.createPlayer("")).getCityCenter(),
                Objects.requireNonNull(GameUtils.createPlayer("")).getCityCenter()
        );
    }
}
