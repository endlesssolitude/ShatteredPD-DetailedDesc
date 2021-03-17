package com.shatteredpixel.shatteredpixeldungeon.custom.challenges.mobhard.sewer;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Snake;

public class SnakeH extends Snake {
    {
        HT = HP = 6;
        EXP = 1;
        immunities.add(Corruption.class);

        defenseSkill = 1000;
    }


}
