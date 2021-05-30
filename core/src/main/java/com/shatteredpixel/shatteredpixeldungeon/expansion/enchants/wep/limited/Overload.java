package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.limited;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses.CountInscription;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.watabou.utils.Bundle;

public class Overload extends CountInscription {
    {
        defaultTriggers = 66;
    }
    private boolean buffed = false;

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        consume(weapon, attacker);
        return damage;
    }

    @Override
    protected void onGain() {
        super.onGain();
        if(!buffed) {
            weapon.level(weapon.level() * 2 + 1);
            buffed = true;
        }
    }

    @Override
    protected void onLose() {
        super.onLose();
        weapon.level(0);
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put("has_buffed", buffed);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        buffed = bundle.getBoolean("has_buffed");
    }
}
