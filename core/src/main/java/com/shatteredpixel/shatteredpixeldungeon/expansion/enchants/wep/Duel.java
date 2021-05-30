package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.GME;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses.Inscription;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;

public class Duel extends Inscription {
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        float diff = (float)attacker.HP/attacker.HT - (float)defender.HP/defender.HT;
        diff = Math.min(1f,  Math.max(diff * Math.min(0.3f + weapon.buffedLvl() * 0.025f, 0.8f), -1f));
        damage = GME.accurateRound(damage * diff);
        return damage;
    }
}
