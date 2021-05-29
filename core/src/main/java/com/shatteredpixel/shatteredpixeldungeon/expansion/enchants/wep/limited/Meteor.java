package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.limited;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.RangeMap;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.timing.RepeatedCallback;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.timing.VirtualActor;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.timing.VirtualTimer;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enhancedPotion.PotionFireEX;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses.CountInscription;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Halo;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class Meteor extends CountInscription {
    {
        defaultTriggers = 50;
    }

    private int count = 4;

    @Override
    public int proc(Weapon w, Char attacker, Char defender, int damage) {
        if(--count<=0){
            float radius =  Math.min(2f + 0.2f* w.buffedLvl(), 3.6f);
            skyFire(defender.pos, radius, ()->{
                for(Char ch: Actor.chars()){
                    if(ch.alignment != Char.Alignment.ALLY) {
                        if (Dungeon.level.trueDistance(ch.pos, defender.pos) <= radius) {
                            Buff.affect(ch, Burning.class).reignite(ch);
                            ch.damage(Random.IntRange(4 + Dungeon.depth, 8 + Dungeon.depth * 7 / 3), this);
                        }
                    }
                }
            });
            count = 4;
        }
        consume(weapon, attacker);
        return damage;
    }

    @Override
    public void useUp(Weapon w, Char attacker) {
        super.useUp(w, attacker);
        int[] map = RangeMap.centeredRect(attacker.pos, 4, 4);
        RepeatedCallback.executeChain(0.2f, Math.min(10 + w.buffedLvl()/2, 20), () -> {
            final int targetCell = map[Random.Int(map.length)];
            final float radius = Math.min(3.2f + w.buffedLvl() * 0.04f, 3.6f);
            skyFire(targetCell, radius, () -> {
                for (Char ch : Actor.chars()) {
                    if (ch.alignment != Char.Alignment.ALLY) {
                        if (Dungeon.level.trueDistance(ch.pos, targetCell) <= radius) {
                            ch.damage(Random.IntRange(3+Dungeon.depth/2, 6+Dungeon.depth), this);
                        }
                    }
                }
            });
        });
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put("countToMeteor", count);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        count = bundle.getInt("countToMeteor");
    }

    public static void skyFire(int pos, float rad, Callback callback){
        float radius = Math.min(rad, 3);
        PointF center = new PointF(DungeonTilemap.tileCenterToWorld(pos));

        Dungeon.hero.sprite.parent.add(new Halo(radius * DungeonTilemap.SIZE, 0xFF4427, 1f){
            private final float time = 0.5f;
            private float life = time;
            @Override
            public void update(){
                if((life -= Game.elapsed) < -time){
                    killAndErase();
                    remove();
                }else if(life>0){
                    am = (life-time)/time;
                    aa = (time-life)/time;
                }else{
                    am = (-life-time)/time;
                    aa = (time+life)/time;
                }
                super.update();
            }
            @Override
            public void draw() {
                Blending.setLightMode();
                super.draw();
                Blending.setNormalMode();
            }
        }.point(center.x, center.y));

        final MagicMissile mm = ((MagicMissile)Dungeon.hero.sprite.parent.recycle( MagicMissile.class ));
        final float ang = Random.Float(60f, 120f)*3.1416f/180f;
        mm.reset(MagicMissile.FIRE, center.clone().offset((float) (200f * Math.cos(ang)), (float) (200f * Math.sin(-ang))), center, ()->{
            CellEmitter.center(pos).burst(BlastParticle.FACTORY, 50);
            Sample.INSTANCE.play(Assets.Sounds.BLAST, Random.Float(1.1f, 1.35f));
            if(callback != null) callback.call();
        });
        mm.setSpeed(400f);

        VirtualActor.delay(0.55f, true, PotionFireEX.class, null);
        VirtualTimer.countTime(1f, mm::destroy);
        //DelayerEffect.delay(0.5f, null);

        Camera.main.shake(3f, 0.5f);
    }
}