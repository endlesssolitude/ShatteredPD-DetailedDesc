package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enchs.armor;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;

public class RangedHeal extends Armor.Glyph {

    @Override
    public int proc(Armor armor, Char attacker, Char defender, int damage) {
        float power = Math.min(2f + 0.25f * armor.buffedLvl(), 5f);
        for(Char ch: Actor.chars()){
            float dist = Dungeon.level.trueDistance(ch.pos, defender.pos);
            if(dist < power){
                if(ch.alignment == Char.Alignment.ALLY && ch != defender){
                    int pre = ch.HP;
                    int toHeal = Math.round(power*1.6f);
                    ch.HP = Math.min(ch.HT, ch.HP + toHeal);
                    if(ch.HP - pre > 0){
                        ch.sprite.showStatus(0x00FF00, "+%d", ch.HP - pre );
                    }
                    if(toHeal > ch.HP - pre){
                        Buff.affect(ch, Barrier.class).incShield(toHeal + pre - ch.HP);
                        ch.sprite.showStatus(0xFFFFFF, "+%d", toHeal + pre - ch.HP );
                    }
                }
            }
        }
        return damage;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing(0x00FF00);
    }
}
