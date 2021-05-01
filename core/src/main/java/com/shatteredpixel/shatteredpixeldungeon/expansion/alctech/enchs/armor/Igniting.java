package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enchs.armor;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;

public class Igniting extends Armor.Glyph {
    @Override
    public int proc(Armor armor, Char attacker, Char defender, int damage) {
        Buff.affect(attacker, Burning.class);
        return damage;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing(0xFF9812);
    }
}
