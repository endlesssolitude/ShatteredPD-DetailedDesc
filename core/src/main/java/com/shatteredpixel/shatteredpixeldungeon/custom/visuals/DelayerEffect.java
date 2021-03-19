package com.shatteredpixel.shatteredpixeldungeon.custom.visuals;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.effects.Effects;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.utils.Callback;

public class DelayerEffect extends Image {

    private float lasting = 0.5f;
    private Callback callback;

    public DelayerEffect() {
        super(Effects.get(Effects.Type.RIPPLE));
    }

    public DelayerEffect setDelay(float delay){
        lasting = delay;
        return this;
    }

    public static void delayTime(float delay, Callback callback){
        Group parent = Dungeon.hero.sprite.parent;
        DelayerEffect d = (DelayerEffect) parent.recycle(DelayerEffect.class);
        parent.bringToFront(d);
        d.reset(delay, callback);
    }

    public void reset(float delay, Callback callback){
        revive();
        lasting = delay;
        this.callback = callback;
    }

    @Override
    public void update() {
        super.update();
        lasting -= Game.elapsed;
        if(lasting  <= 0){
            if(callback!=null) callback.call();
            kill();
        }
    }
}
