package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.sewer;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Albino;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.sprites.AlbinoSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class AlbinoH extends Mob {
    {
        spriteClass = AlbinoSprite.class;

        HP = HT = 15;
        EXP = 3;

        loot = new MysteryMeat();
        lootChance = 1f;
    }
    {
        immunities.add(Corruption.class);
    }

    private int modifier = 0;

    public void setModifier(int modifier, boolean init) {
        this.modifier = Math.min(modifier, 5);
        setAttribute(init);
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put("modifier", modifier);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        modifier = bundle.getInt("modifier");
        setAttribute(false);
    }

    public void setAttribute(boolean init){
        HT = 15 + 3 * modifier + (modifier > 20? modifier*2-40 : 0);
        defenseSkill = 4 + modifier;
        maxLvl = Math.min(4 + modifier, 22);
        EXP = Math.min(2 + modifier/4, 7);

        if(init){
            HP = HT;
        }
    }

    @Override
    public int damageRoll() {
        return super.damageRoll() + modifier*4/5;
    }

    @Override
    public int attackSkill(Char target) {
        return super.attackSkill(target) + modifier*5/4;
    }

    @Override
    public int drRoll() {
        return super.drRoll() + modifier/2;
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        damage = super.attackProc( enemy, damage );
        if (Random.Int( 2 ) == 0) {
            Buff.affect( enemy, Bleeding.class ).set( damage );
        }

        return damage;
    }
}
