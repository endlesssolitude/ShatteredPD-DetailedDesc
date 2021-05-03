package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enchs.wep;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;

public class Brave extends Weapon.Enchantment {
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        float missing = 1.15f - (float)attacker.HP / attacker.HT;
        damage *= (1f + missing * missing * Math.min(0.4f + weapon.buffedLvl()*0.06f, 1f));
        return damage;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing(0xFFFF33);
    }

}
