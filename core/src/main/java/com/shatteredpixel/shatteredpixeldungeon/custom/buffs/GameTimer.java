package com.shatteredpixel.shatteredpixeldungeon.custom.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.watabou.noosa.Game;
import com.watabou.noosa.Visual;

public class GameTimer extends Buff {
    {
        actPriority = VFX_PRIO - 1;
        revivePersists = true;
    }
    private VirtualVisualTimer vvt;

    @Override
    public boolean act() {
        spend(TICK);
        if(vvt==null) {
            vvt = new VirtualVisualTimer();
            Dungeon.hero.sprite.parent.add(vvt);
        }else if(!vvt.alive || !vvt.active || vvt.parent == null){
            vvt.revive();
            vvt.active = true;
            Dungeon.hero.sprite.parent.add(vvt);
            vvt.parent = Dungeon.hero.sprite.parent;
        }
        return true;
    }

    public static class VirtualVisualTimer extends Visual {
        public VirtualVisualTimer(){
            super(0, 0, 0, 0);
        }

        @Override
        public void update() {
            super.update();
            Statistics.second_elapsed += Game.elapsed;
            if(Statistics.second_elapsed > 1f){
                Statistics.real_seconds += Math.floor(Statistics.second_elapsed);
                Statistics.second_elapsed -= Math.floor(Statistics.second_elapsed);
            }
        }
    }
}
