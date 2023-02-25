package Region;

import Region.*;
import Game.Direction;

public class EuclidianPoint implements Point {
    private final int x, y;

    public EuclidianPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("x: %d, y: %d", x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point point))
            return false;
        return point.getX() == x && point.getY() == y;
    }
}
