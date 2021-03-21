package com.shatteredpixel.shatteredpixeldungeon.custom.visuals;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.effects.Effects;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.utils.Callback;

//used to lock thread temporarily, to keep current status instantly
//so that we can stay at instant/fast visuals, or to custom freezing time.

/*****format:
Actor.addDelayed(new Actor{
    final Actor toRemove = this;

    //act
    DelayerEffect.delayTime( timeToDelay, new Callback(){

        //call
        what to do when end

        Actor.remove(toRemove);
        toRemove.next();
    }

    }, -1);
 */
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
