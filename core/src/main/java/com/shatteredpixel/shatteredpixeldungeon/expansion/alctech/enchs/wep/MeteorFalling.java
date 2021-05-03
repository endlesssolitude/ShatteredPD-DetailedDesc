package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enchs.wep;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enhancedPotion.PotionFireEX;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class MeteorFalling extends Weapon.Enchantment{
    private int count = 4;

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put("countToMeteor", count);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        count = bundle.getInt("countToMeteor");
    }

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        if(--count<=0){
            float radius =  Math.min(2f + 0.2f* weapon.buffedLvl(), 3.6f);
            PotionFireEX.skyFire(defender.pos, radius, ()->{
                for(Char ch: Actor.chars()){
                    if(ch.alignment != Char.Alignment.ALLY) {
                        if (Dungeon.level.trueDistance(ch.pos, defender.pos) <= radius) {
                            Buff.affect(ch, Burning.class).reignite(ch);
                            ch.damage(Random.IntRange(10 + Dungeon.depth, 20 + Dungeon.depth * 7 / 3), this);
                        }
                    }
                }
            });
            count = 4;
        }

        return damage;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing(0xFF9812);
    }
}
