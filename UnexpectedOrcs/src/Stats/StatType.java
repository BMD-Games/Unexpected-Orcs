package Stats;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static Utility.Constants.game;

public enum StatType {
    HEALTH,
    MANA,
    VITALITY ,
    ATTACK ,
    WISDOM ,
    DEFENCE,
    SPEED;

    private static final List<StatType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));

    public static StatType randomStat() {
        return VALUES.get((int)game.random(VALUES.size()));
    }

}

