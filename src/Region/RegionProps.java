package Region;

import Player.*;
public class RegionProps implements Region {
    private boolean isCityCenter;
    private final Point location;
    private long deposit;
    private Player owner;

    public RegionProps(Point location) {
        this.isCityCenter = false;
        this.location = location;
        this.deposit = 0;
        this.owner = null;
    }

    @Override
    public boolean getIsCityCenter() {
        return isCityCenter;
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
        if (this.deposit < 0) {
            this.deposit = 0;
        }
    }

    @Override
    public void updateOwner(Player owner) {
        this.owner = owner;
    }

    @Override
    public void setCityCenter(Player owner) {
        isCityCenter = true;
        updateOwner(owner);
    }

    @Override
    public Point getLocation() {
        return this.location;
    }
}
