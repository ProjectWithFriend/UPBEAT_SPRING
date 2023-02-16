package Game;

import Player.*;
import Region.*;

public class GameProps implements Game {
    private final Player player;
    private final Region regi0n;

    public GameProps() {
        this.player = new PlayerProps("lungtu");
        this.regi0n = new RegionProps(0, 0);
    }

    @Override
    public Player currentPlayer() {
        return player;
    }

    @Override
    public Region getRegion() {
        return regi0n;
    }
}
