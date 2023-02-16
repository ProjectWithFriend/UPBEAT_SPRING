package Game;

import Player.*;
import Region.*;

import java.util.List;

public class GameProps implements Game {
    private final Player player;
    private final Region regi0n;

    private List<Region> territory;

    public GameProps() {
        this.player = new PlayerProps("lungtu");
        this.regi0n = new RegionProps(0, 0);
        this.territory = GameInitialize.CreateGame();
    }

    @Override
    public Player currentPlayer() {
        return player;
    }

    @Override
    public Region getRegion() {
        return regi0n;
    }

    @Override
    public List<Region> getTerritory() {
        return territory;
    }
}
