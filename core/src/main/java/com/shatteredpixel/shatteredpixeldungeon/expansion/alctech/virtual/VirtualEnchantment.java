package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.virtual;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.buffs.PlainVampire;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;

//Virtual enchantment works as a bridge from buff to attack proc.
//Yeah buffs are quite limited if it can't interfere proc but
//The logic for buffs in proc is totally bullshit!!!

//Virtual enchantment manages all buffs from enhanced potions and other consumables in this expansion.
//Only one line change for original code.

//To use it for weapons, just add atk proc to the weapon proc.
//For armors, add def proc to armor proc.
public enum VirtualEnchantment{

    INSTANCE;

    public int attackProc(Weapon weapon, Char attacker, Char defender, int damage) {
        PlainVampire pv = attacker.buff(PlainVampire.class);
        if(pv != null){
            damage = pv.trigger(weapon, attacker, defender, damage);
        }
        return damage;
    }

    public int defenseProc(Armor armor, Char attacker, Char defender, int damage){
        return damage;
    }

}
