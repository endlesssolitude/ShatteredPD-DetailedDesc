package com.shatteredpixel.shatteredpixeldungeon.custom.challenges;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FrostImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class KillingHarmony extends Buff implements Hero.Doom {

    {
        announced = true;
        type = buffType.NEUTRAL;
    }

    private float rage = 120f;
    private static final String RAGE = "rage";

    private int lastKilling=0;
    private static final String LAST_KILLING = "last_killing";

    private float particalDamage = 0f;

    public void storeInBundle(Bundle bundle){
        super.storeInBundle(bundle);
        bundle.put(RAGE, rage);
        bundle.put(LAST_KILLING, lastKilling);
    }

    public void restoreFromBundle(Bundle bundle){
        super.restoreFromBundle(bundle);
        rage = bundle.getFloat(RAGE);
        lastKilling = bundle.getInt(LAST_KILLING);
    }

    @Override
    public boolean act(){
        //DON'T interfere with bosses.
        if(Dungeon.bossLevel(Dungeon.depth)) {
            rage = 60f;
            spend(TICK);
            return true;
        }

        if(Statistics.enemiesSlain > lastKilling){
            rage = Math.min(rage+22f*(rage>0?1f:(1f-rage/250f)),200);
        }else{
            rage = Math.max(Math.min(rage - 0.75f * (1f + Dungeon.depth/50f) , rage * 0.987f), -150f);
        }
        lastKilling = Statistics.enemiesSlain;

        procRage();

        spend(TICK);

        return true;
    }

    public float rageDamageFactor(){
        if(rage >=0f ){
            if(Dungeon.bossLevel(Dungeon.depth)) return (float) (1f + Math.sqrt(rage/100f * rage/100f + 2.25f) - 1.5f);
            else return 1f;
        }else{
            return 1f - (-rage) / (-2f*rage + 150f);
        }
    }

    private void procRage(){
        if(rage>0){
            if(rage > 150f){
                rage = 149f;
                int heal = target.HT / 8 + Random.Int(Dungeon.depth/3, Dungeon.depth*2/3);
                target.HP = Math.min(target.HP + heal, target.HT);
                target.sprite.showStatus(CharSprite.POSITIVE, Integer.toString(heal));

                for(Buff b: target.buffs()){
                    if(b.type == buffType.NEGATIVE){
                        detach();
                    }
                }

                if(Random.Int(5) == 0){
                    Buff.affect(target, Haste.class, 20f);
                }
            }else if(rage >75f){
                Buff.prolong(target, FrostImbue.class, 3f);
            }
        }else{
            particalDamage += -(rage+45f) * 0.00018f * target.HT;
            int dmg = (int)particalDamage;
            if(dmg>0){
                target.damage(dmg, this);
                particalDamage = particalDamage - (int)particalDamage;
            }if(rage < -90f){
                Buff.prolong(target, Blindness.class, 3f);
            }
        }
    }

    @Override
    public void onDeath() {
        Dungeon.fail( getClass() );
        GLog.n( Messages.get(this, "ondeath") );
    }

    @Override
    public int icon() {
        return BuffIndicator.WEAPON;
    }

    @Override
    public void tintIcon(Image icon){
        if(rage>0){
            icon.hardlight(0f, 0.5f+rage/300f, 0f);
        }else{
            icon.hardlight(0.5f-rage/300f, 0f, 0f);
        }
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", rage);
    }
}
