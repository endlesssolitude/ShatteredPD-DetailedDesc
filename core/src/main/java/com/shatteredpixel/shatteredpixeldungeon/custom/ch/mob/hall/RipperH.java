package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.hall;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.RipperDemon;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.RangeMap;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.effects.Wound;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.RipperSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class RipperH extends RipperDemon {
    {
        spriteClass = RipperHSprite.class;

        HP = HT = 84;
        defenseSkill = 22;
        viewDistance = Light.DISTANCE;

        EXP = 6;
        maxLvl = 27;

        baseSpeed = 2f;
    }

    {
        immunities.add(Corruption.class);
    }

    @Override
    public float attackDelay(){
        return super.attackDelay()*1.34f;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 20, 30 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 34;
    }

    @Override
    public int attackProc(Char enemy, int damage){
            int dist = RangeMap.manhattanDist(pos, enemy.pos);
            ConsistBleeding bleeding = Buff.affect(enemy, ConsistBleeding.class);
            if(bleeding != null) {
                boolean notFull = bleeding.newLayer(Random.Float(2.3f, 3.3f) * (dist <= 1 ? 1.5f : 1f), Random.Float(1.2f, 1.5f) * (dist <= 1 ? 1.5f : 1f));
                if (!notFull) {
                    bleeding.burst();
                    bleeding.detach();
                }
            }
        return super.attackProc(enemy, damage);
    }

    public static class ConsistBleeding extends Buff{
        protected float[] lasting;
        protected float[] dmg;
        protected float percentDamage=0;

        public ConsistBleeding(){
            lasting = new float[5];
            dmg = new float[5];
        }

        public boolean newLayer(float time, float damagePerAct){
            for(int i=0;i<5;++i){
                if(lasting[i]<=0f){
                    lasting[i]=time;
                    dmg[i]=damagePerAct;
                    return true;
                }
            }
            return false;
        }

        public void burst(){
            float damage = 0;
            for(int i = 0; i<5; ++i){
                damage += Math.max(0, dmg[i])*lasting[i];
            }
            target.sprite.showStatus(CharSprite.NEGATIVE, "!!!");
            GLog.n(M.L(this, "burst"));
            if (target.sprite.visible) {
                Splash.at( target.sprite.center(), -PointF.PI / 2, PointF.PI / 6,
                        target.sprite.blood(), 30 );
            }
            Wound.hit(target);

            damage *= 1.6f;
            target.damage((int)damage, this);
            if(target == Dungeon.hero && !target.isAlive()){
                Dungeon.fail(getClass());
                GLog.n(M.L(this, "burst_die"));
            }
            detach();
        }
        
        public void resetList(){
            for(int i=0; i<5; ++i){
                lasting[i]=0;
                dmg[i]=0;
            }
        }

        public void decreaseTime(){
            for(int i=0; i<5; ++i){
                lasting[i]-=1f;
            }
        }
        
        public float oneDamage(){
            float damage = 0;
            for(int i=0; i<5; ++i){
                damage += dmg[i];
            }
            return damage;
        }
        
        public int getLayer(){
            int layer = 0;
            for(int i=0;i<5;++i){
                layer += lasting[i]>0f?1:0;
            }
            return layer;
        }

        @Override
        public boolean act(){
            spend(TICK);
            percentDamage += oneDamage();
            target.damage((int)percentDamage, this);

            if (target.sprite.visible) {
                Splash.at( target.sprite.center(), -PointF.PI / 2, PointF.PI / 6,
                        target.sprite.blood(), Math.min( 10 *(int)percentDamage / target.HT, 10 ) );
            }
            if(target == Dungeon.hero && !target.isAlive()){
                Dungeon.fail(getClass());
                GLog.n(M.L(this, "bleed_die"));
            }

            percentDamage -= (int)percentDamage;

            decreaseTime();

            if(getLayer()==0) detach();
            
            return true;
        }
        
        @Override
        public void storeInBundle(Bundle b){
            super.storeInBundle(b);
            b.put("leftTime", lasting);
            b.put("damageLeft", dmg);
            b.put("percentDamage", percentDamage);
        }

        @Override
        public void restoreFromBundle(Bundle b){
            super.restoreFromBundle(b);
            lasting = b.getFloatArray("leftTime");
            dmg = b.getFloatArray("damageLeft");
            percentDamage = b.getFloat("percentDamage");
        }

        @Override
        public int icon(){
            return BuffIndicator.BLEEDING;
        }

        @Override
        public String desc() {
            return M.L(this, "desc", getLayer(), oneDamage());
        }

        @Override
        public String toString() {
            return M.L(this, "name");
        }

        @Override
        public String heroMessage() {
            return M.L(this, "heromsg");
        }

        @Override
        public float iconFadePercent() {
            return Math.max(0, (5 - getLayer()) / 5f);
        }

    }

    public static class RipperHSprite extends RipperSprite{
        public RipperHSprite(){
            super();
            brightness(0.75f);
        }

        @Override
        public void resetColor(){
            super.resetColor();
            brightness(0.75f);
        }
    }
}
