package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.buffs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;

public class PlainVampire extends AttackBuff{
    @Override
    protected int proc(Weapon w, Char attacker, Char defender, int damage) {
        int heal = Math.round(damage*rate);
        heal = Math.min(attacker.HT - attacker.HP, heal);
        if (heal > 0 && attacker.isAlive()) {
            attacker.HP += heal;
            attacker.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 1 );
            attacker.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( heal ) );
        }
        return damage;
    }
}
