package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses.Inscription;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;

public class Armory extends Inscription {
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        damage = (int) (attacker.drRoll() * (1.1f + Math.min(weapon.buffedLvl() * 0.025f, 0.3f)));
        damage -= defender.drRoll();
        return damage;
    }
}
