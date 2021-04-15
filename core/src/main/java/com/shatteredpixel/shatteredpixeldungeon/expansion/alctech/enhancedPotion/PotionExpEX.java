package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enhancedPotion;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.buffs.PlainVampire;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.plants.Starflower;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PotionExpEX extends EnhancedPotion{
    {
        image = ItemSpriteSheet.POTION_GOLDEN;
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
        heroDegrade(h, (int) (h.maxExp()*(1.7f)));
    }
    protected void n2(Hero h){
        heroDegrade(h, (int) (h.maxExp()*(3.5f)));
    }
    protected void n3(Hero h){
        heroDegrade(h, (int) (h.maxExp()*(4.7f)));
        Buff.affect(h, PlainVampire.class).setHits(20).setRate(0.225f);
    }

    protected void heroDegrade(Hero h, int exp){
        h.exp -= exp;
        int lvl = h.lvl;
        while(h.lvl > 1 && h.exp < 0 ){
            h.lvl --;
            h.exp += h.maxExp();
            h.updateHT(true);
        }
        if(h.sprite!= null && lvl-h.lvl>0){
            h.sprite.showStatus(CharSprite.NEGATIVE, M.L(this, "degrade", lvl-h.lvl));
        }
        Item.updateQuickslot();
    }

    public static class ExpP3Recipe extends SimplePotionRecipe{
        {
            inputs = new Class[]{PotionOfExperience.class, Starflower.Seed.class};
            inQuantity = new int[]{2,1};
            output = PotionExpEX.class;
            outQuantity = 1;
            lvl = 3;
            cost = 1;
        }
    }

    public static class ExpN3Recipe extends SimplePotionRecipe{
        {
            inputs = new Class[]{PotionOfExperience.class, Starflower.Seed.class, PotionOfToxicGas.class};
            inQuantity = new int[]{1,1,1};
            output = PotionExpEX.class;
            outQuantity = 1;
            lvl = -3;
            cost = 1;
        }
    }


}
