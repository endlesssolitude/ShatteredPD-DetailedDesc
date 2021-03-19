package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.prison;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BanditSprite;

public class BanditH extends ThiefH{
    {
        spriteClass = BanditSprite.class;
        EXP = 7;
        lootChance = 1f;
    }

    @Override
    public float speed(){
        return super.speed() * (item != null?1.2f:1f);
    }

    @Override
    protected boolean steal( Hero hero ) {
        if (super.steal( hero )) {

            Buff.prolong( hero, Blindness.class, Blindness.DURATION );
            Buff.prolong( hero, Cripple.class, Cripple.DURATION );
            Dungeon.observe();

            return true;
        } else {
            return false;
        }
    }

}
