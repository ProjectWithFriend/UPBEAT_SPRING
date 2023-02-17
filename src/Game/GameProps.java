package Game;

import Player.*;
import Region.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameProps implements Game {
    private final Player player;
    private final Region regi0n;

    private final List<Region> territory;
    private final Map<String, Long> identifiers;

    public GameProps() {
        this.player = new PlayerProps("lungtu");
        this.regi0n = new RegionProps(0, 0);
        this.territory = GameInitialize.CreateGame();
        this.identifiers = new HashMap<>();
    }

    @Override
    public void collect(long value) {

    }

    @Override
    public void invest(long eval) {

    }

    @Override
    public void relocate() {

    }

    @Override
    public long nearby(Direction direction) {
        return switch (direction) {
            case Up -> 111;
            case Down -> 222;
            case Left -> 333;
            case Right -> 444;
            case UpLeft -> 555;
            case UpRight -> 666;
            case DownLeft -> 777;
            case DownRight -> 888;
        };
    }

    @Override
    public Map<String, Long> getIdentifiers() {
        return identifiers;
    }

    @Override
    public void attack(Direction direction, long value) {

    }
}
