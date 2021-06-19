package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.limited;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.GME;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses.CountInscription;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;

public class Sacrificing extends CountInscription {
    {
        defaultTriggers = 140;
    }
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        float power = 1.35f + Math.min(weapon.buffedLvl() * 0.05f, 0.65f);
        damage = Math.round(damage * power);
        float sacrifice = (damage - defender.HP)*0.334f;
        if(sacrifice < 0) sacrifice = 0;
        int canDamageHP = attacker.HP - attacker.HT/5 - 1;
        int selfdmg = Math.min(GME.accurateRound(sacrifice), Math.max(canDamageHP, 0));
        if(selfdmg>0) {
            attacker.damage(selfdmg, Sacrificing.class);
        }
        consume(weapon, attacker);
        return damage;
    }
}
