package com.shatteredpixel.shatteredpixeldungeon.custom.testmode;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.BallisticaReal;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.GME;
import com.shatteredpixel.shatteredpixeldungeon.custom.visuals.DelayerEffect;
import com.shatteredpixel.shatteredpixeldungeon.custom.visuals.effects.RotateBeam;
import com.shatteredpixel.shatteredpixeldungeon.effects.Effects;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PurpleParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfDisintegration;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class WandOfScanningBeam extends WandOfDisintegration implements RotateBeam.OnCollide {
    @Override
    protected void onZap(Ballistica attack) {
        RotateBeam.setCollide(this);
        float angle = GME.angle(curUser.pos, attack.collisionPos);
        curUser.sprite.parent.add(new RotateBeam(Effects.Type.DEATH_RAY, curUser.sprite.center().invScale(DungeonTilemap.SIZE),
                angle, 120f, 80f, 8f, 0.4f, 0.4f).setProperty(BallisticaReal.STOP_SOLID));

        Actor.addDelayed(new Actor() {
                             @Override
                             protected boolean act() {
                                 final Actor toRemove = this;
                                 DelayerEffect.delayTime(2.3f, new Callback() {
                                     @Override
                                     public void call() {
                                         Actor.remove(toRemove);
                                         toRemove.next();
                                     }
                                 });
                                 return false;
                             }
                         }, -1

        );
    }

    @Override
    public void fx(Ballistica attack, Callback callback){
        callback.call();
    }

    @Override
    public int onHitProc(Char ch) {
        if(ch.pos == curUser.pos){
            return 0;
        }
        processSoulMark(ch, chargesPerCast());
        ch.damage( damageRoll(buffedLvl()), this );
        ch.sprite.centerEmitter().burst( PurpleParticle.BURST, Random.IntRange( 1, 2 ) );
        ch.sprite.flash();
        return 0;
    }

    @Override
    public int cellProc(int i) {
        if(i == curUser.pos){
            return 0;
        }
        if(Dungeon.level.flamable[i]){
            Dungeon.level.destroy(i);
            GameScene.updateMap( i );
        }
        return 0;
    }
}
