package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enchs.armor;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;

public class Poisonous extends Armor.Glyph {
    @Override
    public int proc(Armor armor, Char attacker, Char defender, int damage) {
        Buff.affect(defender, Poison.class).extend(3f + armor.buffedLvl()/2f);
        return damage;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing(0xF347EC);
    }
}
