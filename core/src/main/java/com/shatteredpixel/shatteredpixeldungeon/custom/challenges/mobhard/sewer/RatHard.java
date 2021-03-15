package com.shatteredpixel.shatteredpixeldungeon.custom.challenges.mobhard.sewer;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Albino;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Rat;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.utils.Callback;

public class RatHard extends Rat {
    {
        EXP=2;
    }

    @Override
    public void die(Object cause){
        Albino white = new Albino();
        white.pos = Dungeon.level.randomRespawnCell(white);
        GameScene.add(white);
        white.beckon(pos);

        MagicMissile.boltFromChar(sprite.parent,
                MagicMissile.EARTH,
                sprite,
                new Ballistica(pos, white.pos, Ballistica.STOP_TARGET).collisionPos,
                new Callback() {
                    @Override
                    public void call() {
                        new Flare(7, 25).color(0xFF4488, true).show(white.sprite, 2f);
                    }
                });

        super.die(cause);
    }
}
