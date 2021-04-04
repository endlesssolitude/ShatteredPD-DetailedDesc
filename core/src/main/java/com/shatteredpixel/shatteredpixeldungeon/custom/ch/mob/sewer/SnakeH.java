package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.sewer;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Snake;

public class SnakeH extends Snake {
    {
        immunities.add(Corruption.class);
    }
    {
        HT = HP = 6;
        EXP = 1;

        defenseSkill = 1000;
    }


}
