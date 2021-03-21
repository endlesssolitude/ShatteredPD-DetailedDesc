package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.cave;

import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Web;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Spinner;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;

public class SpinnerH extends Spinner {
    {
        baseSpeed  = 1.25f;
        EXP = 11;
    }

    @Override
    public void move(int step){
        GameScene.add(Blob.seed(step, 5, Web.class));
        super.move(step);
    }

}
