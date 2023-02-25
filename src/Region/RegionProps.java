package Region;

import Game.GameException;
import Player.*;
public class RegionProps implements Region {
    private final Point location;
    private long deposit;
    private Player owner;

    public RegionProps(Point location){
        this.location = location;
        this.deposit = 0;
        this.owner = null;
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public long getDeposit() {
        return deposit;
    }

    @Override
    public void updateDeposit(long amount) {
        this.deposit += amount;
    }

    @Override
    public void updateOwner(Player owner) {
        this.owner = owner;
    }

    @Override
    public Point getLocation() {
        return this.location;
    }
}
