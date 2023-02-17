package Game;

import java.util.Map;

public interface Game {
    Map<String, Long> getIdentifiers();

    void attack(Direction direction, long value);

    void collect(long value);

    void invest(long eval);

    void relocate();

    long nearby(Direction direction);

    void move(Direction direction);

    long opponent();
}
