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
        this.regi0n = new RegionProps(0);
        this.territory = GameInitialize.CreateGame();
        this.identifiers = new HashMap<>();
    }

    @Override
    public void collect(long value) {
        throw new GameException.NotImplemented();
    }

    @Override
    public void invest(long eval) {
        throw new GameException.NotImplemented();
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
    public void move(Direction direction) {
        throw new GameException.NotImplemented();
    }

    @Override
    public Map<String, Long> getIdentifiers() {
        return identifiers;
    }

    @Override
    public void attack(Direction direction, long value) {
        throw new GameException.NotImplemented();
    }
}
