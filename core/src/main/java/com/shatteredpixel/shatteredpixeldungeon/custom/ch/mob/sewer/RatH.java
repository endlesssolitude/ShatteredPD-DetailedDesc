package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.sewer;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Albino;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Rat;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class RatH extends Rat {

    @Override
    public void die(Object cause) {

        int toSummon = Random.chances(new float[]{0.75f, 0.25f}) + 1;

        while (toSummon-- > 0) {
            Albino white = new Albino();
            white.pos = Dungeon.level.randomRespawnCell(white);
            GameScene.add(white);
            white.beckon(pos);

            MagicMissile.boltFromChar(sprite.parent,
                    MagicMissile.MAGIC_MISSILE,
                    sprite,
                    new Ballistica(pos, white.pos, Ballistica.STOP_TARGET).collisionPos,
                    new Callback() {
                        @Override
                        public void call() {
                            new Flare(5, 25).color(0xFF4488, true).show(white.sprite, 2f);
                        }
                    });
        }

        super.die(cause);
    }
}
