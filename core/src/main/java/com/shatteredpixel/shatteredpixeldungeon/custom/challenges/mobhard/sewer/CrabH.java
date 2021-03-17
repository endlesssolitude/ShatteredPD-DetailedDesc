package com.shatteredpixel.shatteredpixeldungeon.custom.challenges.mobhard.sewer;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Crab;
import com.watabou.utils.Random;

public class CrabH extends Crab {
    @Override
    public int attackProc(Char enemy, int damage){
        Hunger h = enemy.buff(Hunger.class);
        if(h!=null){
            float reduce = -Random.Float(5f, 12f);
            h.satisfy(reduce);
            enemy.sprite.showStatus(0x505050, "%d", (int)reduce);
        }
        return super.attackProc(enemy, damage);
    }
}
