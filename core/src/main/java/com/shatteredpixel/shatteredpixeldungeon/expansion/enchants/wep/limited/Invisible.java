package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.limited;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses.CountInscription;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;

public class Invisible extends CountInscription {
    {
        defaultTriggers = 16;
    }
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        if(damage >= defender.HP) {
            defender.damage(damage * 11 / 10, attacker);
            if (defender.isAlive()) {
                return 0;
            }
            else{
                new FlavourBuff(){
                    @Override
                    public boolean act() {
                        Buff.affect(attacker, Invisibility.class, 6f + weapon.buffedLvl() * 1.5f);
                        detach();
                        return true;
                    }
                }.attachTo(attacker);

                consume(weapon, attacker);
            }
        }
        return damage;
    }
}
