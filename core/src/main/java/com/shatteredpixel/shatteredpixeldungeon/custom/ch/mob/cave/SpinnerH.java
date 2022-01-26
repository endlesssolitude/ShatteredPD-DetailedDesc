package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.cave;

import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Web;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Spinner;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;

public class SpinnerH extends Spinner {
    {
        baseSpeed  = 1.35f;
        EXP = 11;
    }
    {
        immunities.add(Corruption.class);
    }

    @Override
    public void move(int step, boolean traveling){
        GameScene.add(Blob.seed(step, 8, Web.class));
        super.move(step, traveling);
    }




}
