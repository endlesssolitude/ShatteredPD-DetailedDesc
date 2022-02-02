package com.shatteredpixel.shatteredpixeldungeon.expansion.mergeManagers;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.ImmortalShieldAffecter;
import com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enhancedPotion.PotionExpEX;
import com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enhancedPotion.PotionFireEX;
import com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enhancedPotion.PotionFrostEX;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;

//Virtual enchantment works as a bridge from buff to attack proc.
//Yeah buffs are quite limited if it can't interfere proc but
//The logic for buffs in proc is totally bullshit!!!

//Virtual enchantment manages all buffs from enhanced potions and other consumables in this expansion.
//Only one line change for original code.

//To use it for weapons, just add atk proc to the weapon proc.
//For armors, add def proc to armor proc.
//For char damage proc, see Char::damage
public enum VirtualProc {

    INSTANCE;

    //This is just temporary, should implement interfaces for procs in buffs.
    public int attackProc(Weapon weapon, Char attacker, Char defender, int damage) {
        /*
        PotionExpEX.PlainVampire pv = attacker.buff(PotionExpEX.PlainVampire.class);
        if(pv != null){
            damage = pv.trigger(weapon, attacker, defender, damage);
        }
        PotionFireEX.FireBeam fb = attacker.buff(PotionFireEX.FireBeam.class);
        if(fb != null){
            damage = fb.trigger(weapon, attacker, defender, damage);
        }

         */
        return damage;
    }

    public int defenseProc(Armor armor, Char attacker, Char defender, int damage){
        /*


        PotionFrostEX.IceArmor ia = defender.buff(PotionFrostEX.IceArmor.class);
        if(ia != null){
            damage = ia.trigger(armor, attacker, defender, damage);
        }

        PotionFireEX.FireArmor fa = defender.buff(PotionFireEX.FireArmor.class);
        if(fa!=null){
            damage = fa.trigger(armor, attacker, defender, damage);
        }

         */

        return damage;

    }

    public int damage(Char ch, int damage, Object src) {
        //Buff.detach(ch, PotionFrostEX.DiffusiveFrost.class);

        if (ch.buff(ImmortalShieldAffecter.ImmortalShield.class) != null) {
            ch.sprite.showStatus(0x00FFFF, "%d", damage);
            damage = 0;
        }

        return damage;
    }

}
