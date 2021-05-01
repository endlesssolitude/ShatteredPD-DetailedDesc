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
    private int count = 6;

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
            PotionFireEX.skyFire(defender.pos, Math.min(2f + 0.12f* weapon.buffedLvl(), 3.5f), attacker.sprite, ()->{
                for(Char ch: Actor.chars()){
                    if(ch.alignment != Char.Alignment.ALLY) {
                        if (Dungeon.level.trueDistance(ch.pos, attacker.pos) <= 3f) {
                            Buff.affect(ch, Burning.class).reignite(ch);
                            ch.damage(Random.IntRange(8 + Dungeon.depth, 12 + Dungeon.depth * 2), this);
                        }
                    }
                }
            });
            count = 6;
        }

        return damage;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing(0xFF9812);
    }
}
