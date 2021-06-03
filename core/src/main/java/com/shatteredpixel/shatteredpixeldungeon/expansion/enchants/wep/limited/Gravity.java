package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.limited;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses.CountInscription;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;

public class Gravity extends CountInscription {
    {
        defaultTriggers = 30;
    }
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        if(defender.flying){
            damage *= Math.min(1.2f + weapon.buffedLvl()*0.02f, 1.4f);
            defender.flying = false;
            defender.sprite.centerEmitter().burst(Speck.factory(Speck.STAR), 12);
            Dungeon.level.occupyCell(defender);
            if(defender.isAlive()){
                Buff.affect(defender, Vertigo.class, 10f);
            }
            Camera.main.shake(2f, 0.3f);
            Sample.INSTANCE.play(Assets.Sounds.ROCKS, 0.85f);

            consume(weapon, attacker);
        }
        return damage;
    }
}
