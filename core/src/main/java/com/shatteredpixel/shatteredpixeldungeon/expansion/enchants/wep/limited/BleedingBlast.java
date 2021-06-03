package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.limited;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.ConsistBleeding;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses.CountInscription;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;

public class BleedingBlast extends CountInscription {
    {
        defaultTriggers = 150;
    }
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        ConsistBleeding bleeding = Buff.affect(defender, ConsistBleeding.class);
        if(bleeding != null) {
            boolean notFull = bleeding.newLayer(Math.min(4f + weapon.buffedLvl()*0.125f, 6f), Math.min(1.5f + weapon.buffedLvl()*0.05f, 2.25f));
            if (!notFull) {
                bleeding.burst();
                bleeding.detach();
            }
        }
        consume(weapon, attacker);
        return damage;
    }
}
