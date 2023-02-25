package Region;

import Game.*;

public interface Point {
    int getX();

    int getY();

    default Point direction(Direction direction) {
        boolean isEven = getX() % 2 == 0;
        int x = getX();
        int y = getY();
        return switch (direction) {
            case Up -> Point.of(x, y - 1);
            case UpRight -> isEven ? Point.of(x + 1, y) : Point.of(x + 1, y - 1);
            case DownRight -> isEven ? Point.of(x + 1, y + 1) : Point.of(x + 1, y);
            case Down -> Point.of(x, y + 1);
            case DownLeft -> isEven ? Point.of(x - 1, y + 1) : Point.of(x - 1, y);
            case UpLeft -> isEven ? Point.of(x - 1, y) : Point.of(x - 1, y - 1);
        };
    }

    default boolean isValidPoint(int rows, int cols) {
        return getX() >= 0 && getY() >= 0 && getX() < rows && getY() < cols;
    }

    static Point of(int x, int y) {
        return new EuclidianPoint(x, y);
    }
}
