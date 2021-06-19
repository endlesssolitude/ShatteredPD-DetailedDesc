package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.sewer;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Albino;
import com.watabou.utils.Bundle;

public class AlbinoH extends Albino {
    {
        immunities.add(Corruption.class);
    }

    private int modifier = 0;

    public void setModifier(int modifier, boolean init) {
        this.modifier = modifier;
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
}
