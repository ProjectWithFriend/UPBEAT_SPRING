package Region;

import Game.GameException;
import Player.*;
public class RegionProps implements Region {
    private int location;
    private long deposit;
    private Player owner;

    public RegionProps(int location){
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
    public int getLocation() {
        return this.location;
    }

    @Override
    public long getNearby() {
        throw new GameException.NotImplemented();
    }
}
