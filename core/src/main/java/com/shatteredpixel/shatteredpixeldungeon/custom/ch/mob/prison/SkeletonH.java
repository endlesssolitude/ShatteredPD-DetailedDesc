package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.prison;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Skeleton;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

public class SkeletonH extends Skeleton {
    {
        EXP = 7;
    }

    protected int revived = 0;
    @Override
    public void die(Object cause){
        revive();
        super.die(cause);
    }

    protected void revive(){
        revived++;
        if(revived>3) return;

        final int p = Dungeon.level.randomRespawnCell(this);
            MagicMissile.boltFromChar(sprite.parent,
                    MagicMissile.SHADOW,
                    sprite,
                    new Ballistica(pos, p, Ballistica.STOP_TARGET).collisionPos,
                    new Callback() {
                        @Override
                        public void call() {
                            CellEmitter.get(p).burst(Speck.factory(Speck.RATTLE), 13 - 3*revived);
                        }
                    });
            RevivedSkeleton rs = new RevivedSkeleton();
            rs.pos = p;
            rs.HP = (4-revived)*HT/4;
            rs.revived = revived;
            GameScene.add(rs);
            rs.beckon(pos);
    }

    @Override
    public void storeInBundle(Bundle b){
        super.storeInBundle(b);
        b.put("revived", revived);
    }

    @Override
    public void restoreFromBundle(Bundle b){
        super.restoreFromBundle(b);
        revived = b.getInt("revived");
    }


    public static class RevivedSkeleton extends SkeletonH{
        {
            maxLvl = -100;
        }
    }
}
