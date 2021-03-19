package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.prison;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CounterBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DM100;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.custom.visuals.DelayerEffect;
import com.shatteredpixel.shatteredpixeldungeon.effects.Effects;
import com.shatteredpixel.shatteredpixeldungeon.effects.Lightning;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.GameMath;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class DM100H extends DM100 {
    {
        EXP = 7;
        HT = HP = 23;
    }

    private float cd = 4f;

    @Override
    public boolean act() {
        boolean prepare = buff(LightningPrediction.class) != null;
        if(paralysed<=0) {
            if (cd <= 0) {
                if (enemy != null && enemySeen) {
                    if (!prepare) {
                        Buff.affect(this, LightningPrediction.class).set(enemy).countUp(5f);
                    }
                    cd = 8f;
                    spend(TICK);
                    return true;
                }
            }
        }
        if(prepare){
            if (fieldOfView == null || fieldOfView.length != Dungeon.level.length()){
                fieldOfView = new boolean[Dungeon.level.length()];
            }
            Dungeon.level.updateFieldOfView( this, fieldOfView );
            spend(TICK);
            return true;
        }else if(state!=SLEEPING){
            cd -= TICK;
        }

        return super.act();

    }

    @Override
    public void damage(int damage, Object src){
        if(buff(LightningPrediction.class)!=null){
            damage = 1;
        }
        super.damage(damage, src);
    }

    @Override
    protected boolean canAttack(Char enemy){
        boolean can = super.canAttack(enemy);
        if(!can && buff(LightningPrediction.class)==null){
            cd -= 1f;
        }
        return can;

    }

    @Override
    public void storeInBundle(Bundle b){
        super.storeInBundle(b);
        b.put("skillCD", cd);
    }

    @Override
    public void restoreFromBundle(Bundle b){
        super.restoreFromBundle(b);
        cd = b.getFloat("skillCD");
    }

    public static class SkyLightning{}

    public static class LightningPrediction extends CounterBuff{
        //by default
        Char aim = Dungeon.hero;
        WarnWave wave;

        Emitter charge;

        @Override
        public boolean act(){
            countDown(TICK);
            if(aim==null || !aim.isAlive()) detach();
            else if(!target.fieldOfView[aim.pos]) detach();
            else if(count()<=0f) {
                lightningStrike();
                detach();
            }else{
                updateWave();
            }
            spend(TICK);
            return true;
        }

        protected void lightningStrike(){

            Actor.addDelayed(new Actor() {
                @Override
                public boolean act(){
                    final Actor toRemove = this;

                    DelayerEffect.delayTime(0.4f,
                            new Callback() {
                        @Override
                        public void call() {
                            float x = aim.sprite.center().x;
                            float y = aim.sprite.center().y;
                            aim.sprite.parent.add(new Lightning(aim.sprite.center(), new PointF( x, y-300f),null));
                            aim.sprite.parent.add(new Lightning(new PointF(x-5f, y), new PointF( x-5f, y-300f),null));
                            aim.sprite.parent.add(new Lightning(new PointF(x+5f, y), new PointF( x+5f, y-300f),null));
                            Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
                            boolean alive = aim.isAlive();

                            aim.damage(Random.Int(28, 42), new SkyLightning());
                            aim.sprite.centerEmitter().burst( SparkParticle.FACTORY, 32 );
                            aim.sprite.flash();
                            Camera.main.shake(5f, 0.8f);

                            if(aim == Dungeon.hero){
                                if(alive && !aim.isAlive()){
                                    Dungeon.fail(SkyLightning.class);
                                    GLog.n(M.L(SkyLightning.class, "ondeath"));
                                }
                            }
                            Actor.remove(toRemove);
                            toRemove.next();
                        }
                    }
                    );
                    return false;
                }
                },
                    -1);


        }

        public LightningPrediction set(Char ch){
            aim = ch;
            wave = new WarnWave(ch.sprite).setScale(2.5f, -2.3f).setSpeed(1f);
            ch.sprite.parent.add(wave);
            return this;
        }

        protected void updateWave(){
            if(wave == null){
                wave = new WarnWave(aim.sprite);
                aim.sprite.parent.add(wave);
            }
            wave.setSpeed(GameMath.gate(1f, 2f - count() / 4f, 2.25f)).setScale(GameMath.gate(1f, 1.2f + count() * 0.3f, 3f), -GameMath.gate(1f, 1.2f + count() * 0.3f, 3f)+0.1f);
        }

        @Override
        public void detach(){
            if(wave != null){
                wave.killWave();
            }
            target.sprite.centerEmitter().burst( SparkParticle.FACTORY, count()<=0?25:10);
            super.detach();
        }

        @Override
        public void fx(boolean on){
            if(on) {
                charge = target.sprite.emitter();
                charge.autoKill = false;
                charge.pour(SparkParticle.STATIC, 0.05f);
                //charge.on = false;
            }else{
                if(charge != null) {
                    charge.on = false;
                    charge = null;
                }

            }
        }

    }

    public static class WarnWave extends Image {

        private static final float TIME_TO_FADE = 1f;

        private float time = 0f;

        private float speed = 1f;

        private CharSprite sprite;

        private float baseScale = 1f;
        private float alterScale = 1.5f;

        public WarnWave(CharSprite sp){
            super(Effects.get(Effects.Type.RIPPLE));
            origin.set(width / 2, height / 2);
            sprite = sp;
        }

        public WarnWave setPhase(float phase){
            time = TIME_TO_FADE * (1f - phase + (int) phase);
            return this;
        }

        public WarnWave setSpeed(float speedScale){
            speed = speedScale;
            return this;
        }

        public WarnWave setScale(float base, float scaling){
            baseScale = base;
            alterScale = scaling;
            return this;
        }

        public void reset(int pos) {
            revive();

            x = (pos % Dungeon.level.width()) * DungeonTilemap.SIZE + (DungeonTilemap.SIZE - width) / 2;
            y = (pos / Dungeon.level.width()) * DungeonTilemap.SIZE + (DungeonTilemap.SIZE - height) / 2;

            time = TIME_TO_FADE/speed;
        }

        @Override
        public void update() {
            super.update();

            x = sprite.center().x-this.width/2f;
            y = sprite.center().y-this.height/2f;

            if ((time -= Game.elapsed) <= 0) {
                time += TIME_TO_FADE/speed;
            }

            float p = time / TIME_TO_FADE;
            alpha(1f);
            //grow large by default. alterScale < 0 means shrinking.
            scale.y = scale.x = (1-p)*(1-p)*alterScale + baseScale;

        }
/*
        public static void blast(int pos) {
            Group parent = Dungeon.hero.sprite.parent;
            WarnWave b = (WarnWave) parent.recycle(WarnWave.class);
            parent.bringToFront(b);
            b.reset(pos);
        }
*/
        public void killWave(){
            kill();
        }
    }


}
