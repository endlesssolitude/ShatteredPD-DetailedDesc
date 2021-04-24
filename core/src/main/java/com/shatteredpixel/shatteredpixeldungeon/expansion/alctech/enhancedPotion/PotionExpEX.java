package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enhancedPotion;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.buffs.AttackBuff;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class PotionExpEX extends EnhancedPotion{
    {
        image = ItemSpriteSheet.POTION_GOLDEN;
    }

    @Override
    protected void setCategory() {
        drinkOrThrow = DRINK_PREFER;
    }

    @Override
    public void apply(Hero hero){
        if(enhanceLevel == 1){
            p1(hero);
        }
        if(enhanceLevel == 2){
            p2(hero);
        }
        if(enhanceLevel >= 3){
            p3(hero);
        }
        if(enhanceLevel == -1){
            n1(hero);
        }
        if(enhanceLevel == -2){
            n2(hero);
        }
        if(enhanceLevel <= -3){
            n3(hero);
        }
    }

    protected void p1(Hero h){
        h.earnExp((int) (h.maxExp()*1.55f), PotionOfExperience.class);
    }
    protected void p2(Hero h){
        h.earnExp((int) (h.maxExp()*2.1f), PotionOfExperience.class);
    }
    protected void p3(Hero h){
        h.earnExp((int) (h.maxExp()*2.6f), PotionOfExperience.class);
        Buff.affect(h, Bless.class, 128f);
    }
    protected void n1(Hero h){
        heroDegrade(h, (int) (h.maxExp()*(1.3f)));
    }
    protected void n2(Hero h){
        heroDegrade(h, (int) (h.maxExp()*(2.7f)));
    }
    protected void n3(Hero h){
        heroDegrade(h, (int) (h.maxExp()*(3.2f)));
        Buff.affect(h, PlainVampire.class).setHits(20).setRate(0.225f);
        h.sprite.showStatus(0xD030D0, M.L(this, "vampire"));
    }

    protected void heroDegrade(Hero h, int exp){
        h.exp -= exp;
        int lvl = h.lvl;
        int dLevel = 0;
        while(h.lvl > 1 && h.exp < 0 ){
            h.lvl --;
            h.exp += h.maxExp();
            h.updateHT(true);
            ++dLevel;
        }
        if(h.sprite!= null && lvl-h.lvl>0){
            h.sprite.showStatus(CharSprite.NEGATIVE, M.L(this, "degrade", lvl-h.lvl));
            h.sprite.emitter().start(ElmoParticle.FACTORY, 0.05f, 20 + dLevel*15);
        }
        Sample.INSTANCE.play(Assets.Sounds.DEGRADE);
        Item.updateQuickslot();
    }

    public static class PlainVampire extends AttackBuff {
        @Override
        protected int proc(Weapon w, Char attacker, Char defender, int damage) {
            int heal = Math.round(damage*rate);
            heal = Math.min(attacker.HT - attacker.HP, heal);
            if (heal > 0 && attacker.isAlive()) {
                attacker.HP += heal;
                attacker.sprite.emitter().start( Speck.factory( Speck.HEALING ), 0.4f, 1 );
                attacker.sprite.showStatus( CharSprite.POSITIVE, Integer.toString( heal ) );
            }
            return damage;
        }
    }
}
