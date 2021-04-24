package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enchs.wep;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.effects.Wound;
import com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enhancedPotion.PotionFrostEX;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class IceBreaking extends Weapon.Enchantment {

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        if(defender.buff(Frost.class) != null || defender.buff(PotionFrostEX.DiffusiveFrost.class) != null){
            damage *= Math.min(2.2f + weapon.buffedLvl() * 0.12f, 4f);
            Sample.INSTANCE.play(Assets.Sounds.HIT_SLASH, Random.Float(1.0f, 1.3f));
            Wound.hit(defender);
        }else{
            Chill chill = defender.buff(Chill.class);
            if(chill != null ){
                if(Random.Float()<chill.cooldown()/Chill.DURATION) {
                    new FlavourBuff() {
                        {
                            actPriority = VFX_PRIO;
                        }

                        public boolean act() {
                            Buff.affect(target, Frost.class, Frost.DURATION);
                            return super.act();
                        }
                    }.attachTo(defender);
                }
                Buff.affect(defender, Chill.class, Random.Float(3f, 4f + weapon.buffedLvl()/2f));
            }else{
                Buff.affect(defender, Chill.class, Random.Float(3f, 4f + weapon.buffedLvl()/2f));
            }

        }

        return damage;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing(0x0080FF);
    }
}
