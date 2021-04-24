package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enchs.armor;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;

public class LastStanding extends Armor.Glyph {

    @Override
    public int proc(Armor armor, Char attacker, Char defender, int damage) {
        if(defender.HP * 3 / 4 - damage  <= 1  && defender.buff(OffFront.class)==null){
            Buff.affect(defender, FastShielding.class).setShield(Math.max(defender.HT * 25, 1200));
            new Flare(8, 32).color(0xFFFF66, true).show(defender.sprite, 2f);
            Buff.affect(defender, OffFront.class, Math.max(1200f - 60f* armor.buffedLvl(), 840f));
            defender.HP = Math.max(defender.HP, defender.HT/5);
            return 0;
        }
        return damage;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing(0xFFFF80);
    }

    public static class FastShielding extends Barrier {
        @Override
        public boolean act() {
            decShield(shielding() / 9 + 1);
            return super.act();
        }
    }

    public static class OffFront extends FlavourBuff {
        {
            announced = true;
        }

        @Override
        public int icon() {
            return BuffIndicator.COMBO;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0xFFFF80);
        }

        @Override
        public String desc() {
            return M.L(OffFront.class, "cd");
        }

        @Override
        public String heroMessage() {
            return M.L(OffFront.class, "save");
        }
    }
}
