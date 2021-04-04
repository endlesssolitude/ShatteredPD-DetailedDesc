package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.sewer;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Crab;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class CrabH extends Crab {
    {
        immunities.add(Corruption.class);
    }

    private float deduced = 0;
    @Override
    public int attackProc(Char enemy, int damage){
        Hunger h = enemy.buff(Hunger.class);
        if(h!=null){
            float reduce = -Random.Float(5f, 12f);
            h.satisfy(reduce);
            deduced += -reduce;
            if(deduced > 30f){
                Buff.affect(enemy, Weakness.class, (deduced-25f)/5f);
            }
            enemy.sprite.showStatus(0x505050, "%d", (int)reduce);
        }
        return super.attackProc(enemy, damage);
    }

    @Override
    public void storeInBundle(Bundle b){
        super.storeInBundle(b);
        b.put("hungerDrain", deduced);
    }

    @Override
    public void restoreFromBundle(Bundle b){
        super.restoreFromBundle(b);
        deduced = b.getFloat("hungerDrain");
    }
}
