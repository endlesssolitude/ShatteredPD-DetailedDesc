package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.GME;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses.Inscription;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;

public class Sacrificing extends Inscription {
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        float power = 1.28f + Math.min(weapon.buffedLvl() * 0.03f, 0.42f);
        damage = Math.round(damage * power);
        float sacrifice = (damage - defender.HP)*0.334f;
        if(sacrifice < 0) sacrifice = 0;
        int canDamageHP = attacker.HP - attacker.HT/5 - 1;
        int selfdmg = Math.min(GME.accurateRound(sacrifice), Math.max(canDamageHP, 0));
        if(selfdmg>0) {
            attacker.damage(selfdmg, Sacrificing.class);
        }
        return damage;
    }
}
