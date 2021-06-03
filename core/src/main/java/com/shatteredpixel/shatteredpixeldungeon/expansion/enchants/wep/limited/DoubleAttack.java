package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.limited;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.GME;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses.CountInscription;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;

public class DoubleAttack extends CountInscription {
    {
        defaultTriggers = 100;
    }

    private boolean isExtraAttack = false;

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        if(!isExtraAttack){
            isExtraAttack = true;
            attacker.attack(defender);
        }else{
            isExtraAttack = false;
            //only extra attack consumes the triggers
            consume(weapon, attacker);
        }
        return GME.accurateRound(damage * (Math.min(weapon.buffedLvl() * 0.01f + 0.5f, 0.6f)));
    }

}
