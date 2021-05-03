package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enhancedPotion;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.FilterUtil;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.RangeMap;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.timing.RepeatedCallback;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.timing.VirtualActor;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.timing.VirtualTimer;
import com.shatteredpixel.shatteredpixeldungeon.custom.visuals.CellColorBlock;
import com.shatteredpixel.shatteredpixeldungeon.custom.visuals.effects.BeamCustom;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Effects;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.buffs.AttackBuff;
import com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.buffs.DefendBuff;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Halo;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class PotionFireEX extends EnhancedPotion{
    {
        image = ItemSpriteSheet.POTION_AMBER;
    }

    @Override
    public void shatter(int cell) {
        if(enhanceLevel == 1){
            p1(cell);
        } else if(enhanceLevel == 2){
            p2(cell);
        } else if(enhanceLevel == 3){
            p3t(cell);
        }else{
            new PotionOfLiquidFlame().shatter(cell);
        }
    }

    @Override
    public void apply(Hero hero) {
        if(enhanceLevel == 3){
            p3d(hero);
        }else if(enhanceLevel == -1){
            n1(hero);
        }else if(enhanceLevel == -2){
            n2(hero);
        }else if(enhanceLevel == -3){
            n3(hero);
        }else{
            new PotionOfLiquidFlame().apply(hero);
        }
    }

    @Override
    protected void setCategory() {
        if(enhanceLevel == 1 || enhanceLevel == 2) drinkOrThrow = THROW_ONLY;
        else if(enhanceLevel == 3){
            drinkOrThrow = NEUTRAL;
        }else{
            drinkOrThrow = DRINK_PREFER;
        }
    }

    protected void p1(int cell){
        Sample.INSTANCE.play( Assets.Sounds.BLAST );
        if (Dungeon.level.heroFOV[cell]) {
            CellEmitter.center(cell).burst(BlastParticle.FACTORY, 25);
        }
        int[] N2 = RangeMap.manhattanRing(cell, 0, 2);

        for(int i: N2){
            Dungeon.hero.sprite.parent.add(new CellColorBlock(i, 0.6f, 0.1f, 0.2f, 0x80FFD467, true));
            Char c = Actor.findChar(i);
            if(c!=null){
                int damage = Random.NormalIntRange(7 + Dungeon.depth, 12 + Dungeon.depth*5/2);
                damage -= c.drRoll();
                c.damage(damage, PotionFireEX.class);
                if(c instanceof Hero){
                    if(!c.isAlive()){
                        Dungeon.fail(getClass());
                    }
                }
            }
            if(Dungeon.level.flamable[i]){
                Dungeon.level.destroy(i);
                GameScene.updateMap(i);
            }
        }
    }

    protected void p2(int cell){
        Sample.INSTANCE.play( Assets.Sounds.BLAST );
        if (Dungeon.level.heroFOV[cell]) {
            CellEmitter.center(cell).burst(BlastParticle.FACTORY, 35);
        }
        int[] N2 = RangeMap.manhattanRing(cell, 0, 2);
        ArrayList<Integer> toExplode = new ArrayList<>();
        for(int i: N2){
            Dungeon.hero.sprite.parent.add(new CellColorBlock(i, 0.6f, 0.15f, 0.2f, 0x80FF9762, true));
            Char c = Actor.findChar(i);
            if(c!=null){
                int damage = Random.NormalIntRange(10 + Dungeon.depth * 3 / 2, 15 + Dungeon.depth * 3);
                damage -= c.drRoll();
                c.damage(damage, PotionFireEX.class);
                if(c instanceof Hero){
                    if(!c.isAlive()){
                        Dungeon.fail(getClass());
                    }
                }
                if(!c.isAlive()) {
                    toExplode.add(i);
                }
            }

            if(Dungeon.level.flamable[i]){
                Dungeon.level.destroy(i);
                GameScene.updateMap(i);
            }
        }

        for(int i: toExplode){
            p1(i);
        }
    }
    protected void p3d(Hero h){
        Buff.affect(h, FireRain.class).addLife(7);
    }
    protected void n1(Hero h){
        Buff.affect(h, FireAura.class).set(FireAura.DURATION);
    }
    protected void n2(Hero h){
        Buff.affect(h, FireBeam.class).addHits(14);
    }
    protected void n3(Hero h){
        Buff.affect(h, FireArmor.class).addHits(7);
    }

    protected void p3t(int cell) {
        int[] map = RangeMap.centeredRect(cell, 4, 4);
        RepeatedCallback.executeChain(0.25f, 16, () -> {
            final int targetCell = map[Random.Int(map.length)];
            final float radius = 3.2f;
            skyFire(targetCell, radius, () -> {
                for (Char ch : Actor.chars()) {
                    if (ch.alignment != Char.Alignment.ALLY) {
                        if (Dungeon.level.trueDistance(ch.pos, targetCell) <= radius) {
                            ch.damage(Random.IntRange(4+Dungeon.depth/2, 8+Dungeon.depth), this);
                        }
                    }
                }
            });
        });
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

    public static class FireRain extends Buff {
        {announced = true;}
        private int life;
        public FireRain setLife(int lf){this.life = lf; return this;}
        public FireRain addLife(int lf){this.life += lf; return this;}

        @Override
        public int icon() {
            return BuffIndicator.FIRE;
        }

        @Override
        public boolean act() {
            if(life>0) {
                if(fire()) {
                    --life;
                }
                spend(TICK*2);
            }else{
                detach();
            }
            return true;
        }

        private boolean fire(){
            Char[] suitable = FilterUtil.filter(Actor.chars(), (ch)->
                    ch.alignment == Char.Alignment.ENEMY && Dungeon.hero.fieldOfView[ch.pos]).toArray(new Char[0]);
            if(suitable.length == 0) return false;
            Char aimedChar = Random.oneOf(suitable);
            skyFire(aimedChar.pos, 4.05f, ()-> {
                for(Char ch: Actor.chars()){
                    if(ch.alignment != Char.Alignment.ALLY) {
                        if (Dungeon.level.trueDistance(ch.pos, aimedChar.pos) <= 4.05f) {
                            Buff.affect(ch, Burning.class).reignite(ch);
                            ch.damage(Random.IntRange(5 + Dungeon.depth * 3 / 2, 8 + Dungeon.depth * 5 / 2), this);
                        }
                    }
                }
            });
            return true;
        }

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put("leftShoots", life);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            life = bundle.getInt("leftShoots");
        }

        @Override
        public String heroMessage() {
            return "";
        }

        @Override
        public String desc() {
            return M.L(this, "desc", life);
        }
    }

    public static class FireAura extends FlavourBuff{
        {
            immunities.add(Burning.class);
        }

        {
            type = buffType.POSITIVE;
            announced = true;
        }

        public static final float DURATION	= 24f;

        protected float left;

        private static final String LEFT	= "left";

        @Override
        public void storeInBundle( Bundle bundle ) {
            super.storeInBundle( bundle );
            bundle.put( LEFT, left );
        }

        @Override
        public void restoreFromBundle( Bundle bundle ) {
            super.restoreFromBundle( bundle );
            left = bundle.getFloat( LEFT );
        }

        public void set( float duration ) {
            this.left = duration;
        }

        @Override
        public boolean act() {
            if(left > 4f) {
                for(int i: PathFinder.NEIGHBOURS9) {
                    if(Random.Int(3)==0) GameScene.add(Blob.seed(target.pos + i, 5, Fire.class));
                }
            }

            spend(TICK);
            left -= TICK;
            if (left <= 0){
                detach();
            }

            return true;
        }

        @Override
        public int icon() {
            return BuffIndicator.LEVITATION;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0xFF6419);
        }

        @Override
        public float iconFadePercent() {
            return Math.max(0, (DURATION - left) / DURATION);
        }

        @Override
        public String toString() {
            return M.L(this, "name");
        }

        @Override
        public String desc() {
            return M.L(this, "desc", dispTurns(left));
        }

    }

    public static class FireArmor extends DefendBuff{
        {
            immunities.add(Fire.class);
            announced = true;
        }

        @Override
        public int icon() {
            return BuffIndicator.ARMOR;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0xFF8505);
        }

        @Override
        protected int proc(Armor a, Char attacker, Char defender, int damage) {
            skyFire(attacker.pos, 4.05f, ()->{
                for(Char ch: Actor.chars()){
                    if(ch.alignment != Char.Alignment.ALLY) {
                        if (Dungeon.level.trueDistance(ch.pos, attacker.pos) <= 4.05f) {
                            Buff.affect(ch, Burning.class).reignite(ch);
                            ch.damage(Random.IntRange(7 + Dungeon.depth * 3 / 2, 11 + Dungeon.depth * 5 / 2), this);
                        }
                    }
                }
            });

            return damage/2;
        }
    }

    public static class FireBeam extends AttackBuff{
        {
            immunities.add(Burning.class);
            announced = true;
        }

        @Override
        public int icon() {
            return BuffIndicator.WEAPON;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0xFF8607);
        }

        @Override
        protected int proc(Weapon w, Char attacker, Char defender, int damage) {
            Ballistica ba = new Ballistica(attacker.pos, defender.pos, Ballistica.STOP_SOLID);
            attacker.sprite.parent.add(new BeamCustom(attacker.sprite.center(), DungeonTilemap.tileCenterToWorld(ba.collisionPos),
                    Effects.Type.LIGHT_RAY,null).setColor(0xFFA500).setDiameter(1.6f));
            for(int cell: ba.subPath(1, ba.dist)){
                Char ch = findChar(cell);
                if(ch!=null){
                    Buff.affect(ch, Burning.class).reignite(ch);
                    ch.damage(w.damageRoll(attacker)/2, this);
                    if(ch == Dungeon.hero){
                        if(!ch.isAlive()){
                            Dungeon.fail(getClass());
                        }
                    }
                }
                if(Dungeon.level.flamable[cell]){
                     Dungeon.level.destroy(cell);
                     GameScene.updateMap(cell);
                }
            }
            Sample.INSTANCE.play(Assets.Sounds.RAY);

            return damage;
        }

    }

}