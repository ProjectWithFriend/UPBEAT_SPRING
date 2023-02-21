package Region;

import Player.*;

public interface Region {
    Player getOwner();

    long getDeposit();

    void updateDeposit(long amount);

    void updateOwner(Player owner);

    int getLocation();
}
