package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses.Inscription;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;

public class Switching extends Inscription {

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        if(!(attacker.properties().contains(Char.Property.IMMOVABLE) || defender.properties().contains(Char.Property.IMMOVABLE))) {
            int tempPos = defender.pos;
            ScrollOfTeleportation.appear(defender, attacker.pos);
            ScrollOfTeleportation.appear(attacker, tempPos);
        }

        return damage*2/5;
    }

    @Override
    protected void onGain() {
        super.onGain();
        weapon.RCH <<= 1;
        weapon.RCH += 3;
    }

    @Override
    protected void onLose() {
        super.onLose();
        weapon.RCH -= 3;
        weapon.RCH >>= 1;
    }
}
