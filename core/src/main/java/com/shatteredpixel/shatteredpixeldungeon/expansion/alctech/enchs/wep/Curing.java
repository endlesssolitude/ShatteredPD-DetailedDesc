package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enchs.wep;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;


public class Curing extends Weapon.Enchantment {
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        if(defender.properties().contains(Char.Property.DEMONIC) || defender.properties().contains(Char.Property.UNDEAD)){
            Buff.affect(defender, Poison.class).extend(6f + weapon.buffedLvl());
        }else{
            Buff.affect(defender, Healing.class).setHeal(2 + weapon.buffedLvl()/2, 1f, 0);
        }
        return damage;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing(0x00FF00);
    }
}
