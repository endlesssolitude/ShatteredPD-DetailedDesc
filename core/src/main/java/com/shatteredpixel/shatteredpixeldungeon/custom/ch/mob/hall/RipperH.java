package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.hall;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.RipperDemon;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.ConsistBleeding;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.RangeMap;
import com.shatteredpixel.shatteredpixeldungeon.sprites.RipperSprite;
import com.watabou.utils.Random;

public class RipperH extends RipperDemon {
    {
        spriteClass = RipperHSprite.class;

        HP = HT = 84;
        defenseSkill = 22;
        viewDistance = Light.DISTANCE;

        EXP = 6;
        maxLvl = 27;

        baseSpeed = 2f;
    }

    {
        immunities.add(Corruption.class);
    }

    @Override
    public float attackDelay(){
        return super.attackDelay()*1.34f;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 20, 30 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 34;
    }

    @Override
    public int attackProc(Char enemy, int damage){
            int dist = RangeMap.manhattanDist(pos, enemy.pos);
            ConsistBleeding bleeding = Buff.affect(enemy, ConsistBleeding.class);
            if(bleeding != null) {
                boolean notFull = bleeding.newLayer(Random.Float(2.3f, 3.3f) * (dist <= 1 ? 1.5f : 1f), Random.Float(1.2f, 1.5f) * (dist <= 1 ? 1.5f : 1f));
                if (!notFull) {
                    bleeding.burst();
                    bleeding.detach();
                }
            }
        return super.attackProc(enemy, damage);
    }

    public static class RipperHSprite extends RipperSprite{
        public RipperHSprite(){
            super();
            brightness(0.75f);
        }

        @Override
        public void resetColor(){
            super.resetColor();
            brightness(0.75f);
        }
    }
}
