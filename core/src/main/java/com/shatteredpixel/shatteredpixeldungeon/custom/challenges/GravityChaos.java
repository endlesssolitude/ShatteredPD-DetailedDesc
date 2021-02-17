package com.shatteredpixel.shatteredpixeldungeon.custom.challenges;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Levitation;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.Trap;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.utils.Bundle;

public class GravityChaos extends Buff {
    {
        announced = false;
        type = buffType.NEUTRAL;
        actPriority = BLOB_PRIO -1;
    }

    private static final float COOL_DOWN = 150f;

    public boolean flying = false;
    private float countdown = COOL_DOWN;
    private boolean warned = false;

    @Override
    public boolean act(){
        boolean fell = false;
        countdown -= TICK;
        if(!warned && countdown<6f){
            GLog.w(Messages.get(this, "warn"));
            warned = true;
            if(target instanceof Hero){
                ((Hero) target).interrupt();
            }
        }
        if(flying){
            Buff.prolong(target, Levitation.class, 3f);
        }else{
            if(target.buff(Levitation.class) != null){
                fell = true;
            }
            Buff.detach(target, Levitation.class);
        }

        if(countdown<0f){
            warned = false;
            countdown += COOL_DOWN;
            if(flying){
                flying = false;
                Buff.detach(target, Levitation.class);
                Buff.affect(target, Roots.class, 8f);
                land();
            }else{
                flying = true;
                Buff.prolong(target, Levitation.class, 3f);
                Buff.affect(target, Vertigo.class, 10f);
            }
            if(target instanceof Hero){
                ((Hero) target).interrupt();
            }
        }else if(fell){
            land();
        }

        spend(TICK);
        return true;
    }

    private void land(){
        Dungeon.level.occupyCell(target);
        Camera.main.shake( 3, 1.0f );
        //shake
        int size = Dungeon.level.length();
        for(int i=0;i<size;++i){
            if(Dungeon.level.traps.get(i)!=null){
                Trap t = Dungeon.level.traps.get(i);
                if(t.active){
                    t.activate();
                }
            }
        }
        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (mob.alignment == Char.Alignment.ENEMY) {
                float distance = Dungeon.level.trueDistance(target.pos, mob.pos);
                if(distance < 12f)
                Buff.prolong(mob, Paralysis.class, 12f-distance);
            }
            mob.beckon(target.pos);
        }
    }

    private static final String COUNTDOWN = "countdown";
    private static final String FLYING = "flying";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( COUNTDOWN, countdown );
        bundle.put(FLYING, flying);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        countdown = bundle.getFloat( COUNTDOWN );
        flying = bundle.getBoolean(FLYING);
    }


}
