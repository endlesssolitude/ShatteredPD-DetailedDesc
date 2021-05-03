package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enchs.wep;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.effects.Wound;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Unholy extends Weapon.Enchantment {

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        float power = defender.properties().contains(Char.Property.DEMONIC) || defender.properties().contains(Char.Property.UNDEAD) ?
                Math.max(0.9f - 0.03f*weapon.buffedLvl(), 0.6f) : Math.min(1.25f + weapon.buffedLvl()*0.075f, 2f);
        damage *= power;
        if(power > 1f){
            attacker.damage( Math.min(2 + weapon.buffedLvl() / 2, Math.max(attacker.HP - attacker.HT/4, 0)), this);
            if(Random.Float()<(power-1f)/4f){
                damage *= (1f+power);
                Wound.hit(defender);
                defender.sprite.emitter().burst(ShadowParticle.UP,  5);
            }
        }else{
            if(Random.Float()<(1f-power)){
                Buff.affect(attacker, Healing.class).setHeal(Math.min(weapon.buffedLvl() * 3 / 2 + 5, damage / 2), 1f, 0);
                damage = 0;
            }
        }
        return damage;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing(0x222222);
    }
}
