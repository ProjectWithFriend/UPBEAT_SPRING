package Player;

import Region.*;

import java.util.Map;

public interface Player {
    long getID();

    String getName();

    long getBudget();

    void updateBudget(long amount);

    Map<String, Long> identifiers();

}
