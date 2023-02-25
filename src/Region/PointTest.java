package Region;

import Game.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PointTest {
    @Test
    void testDirection() {
        Point point = Point.of(2, 1);
        assertEquals(Point.of(2, 0), point.direction(Direction.Up));
        assertEquals(Point.of(3, 1), point.direction(Direction.UpRight));
        assertEquals(Point.of(3, 2), point.direction(Direction.DownRight));
        assertEquals(Point.of(2, 2), point.direction(Direction.Down));
        assertEquals(Point.of(1, 2), point.direction(Direction.DownLeft));
        assertEquals(Point.of(1, 1), point.direction(Direction.UpLeft));

        point = Point.of(1, 2);
        assertEquals(Point.of(1,1), point.direction(Direction.Up));
        assertEquals(Point.of(2,1), point.direction(Direction.UpRight));
        assertEquals(Point.of(2, 2), point.direction(Direction.DownRight));
        assertEquals(Point.of(1, 3), point.direction(Direction.Down));
        assertEquals(Point.of(0, 2), point.direction(Direction.DownLeft));
        assertEquals(Point.of(0, 1), point.direction(Direction.UpLeft));
    }
}
