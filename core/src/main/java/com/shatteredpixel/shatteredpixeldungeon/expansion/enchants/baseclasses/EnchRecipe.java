package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses;

import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.Cursed;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.TierChange;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.limited.Curing;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.limited.IceBreaking;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.limited.Meteor;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.limited.Unholy;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.BlizzardBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.InfernalBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.GooBlob;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.MetalShard;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTerror;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfAugmentation;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.plants.Fadeleaf;
import com.shatteredpixel.shatteredpixeldungeon.plants.Icecap;
import com.shatteredpixel.shatteredpixeldungeon.plants.Stormvine;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sungrass;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Arrays;

public enum EnchRecipe{
    METEOR_1(Meteor.class, 96, InfernalBrew.class, PotionOfLiquidFlame.class, PotionOfLevitation.class),
    CURSED_ATTACK(Cursed.class, 1, MetalShard.class, GooBlob.class, Ankh.class),
    ICE_BREAK(IceBreaking.class, 125, BlizzardBrew.class, PotionOfFrost.class, Icecap.Seed.class),
    UNHOLY(Unholy.class, 125, ScrollOfTerror.class, ScrollOfRage.class, ScrollOfRetribution.class),
    CURE(Curing.class, 175, PotionOfHealing.class, Sungrass.Seed.class, PotionOfHealing.class),

    TIER_UP_ONE(TierChange.TierUpOne.class, 1, StoneOfAugmentation.class, PotionOfHealing.class),
    TIER_UP_TWO(TierChange.TierUpTwo.class, 1, ScrollOfTransmutation.class, StoneOfEnchantment.class),
    TIER_UP_THREE(TierChange.TierUpThree.class, 1, ScrollOfTransmutation.class, PotionOfStrength.class, ScrollOfUpgrade.class),
    TIER_DOWN_ONE(TierChange.TierDownOne.class, 1, GooBlob.class, StoneOfAugmentation.class, PotionOfHealing.class),
    TIER_DOWN_TWO(TierChange.TierDownTwo.class, 1,  GooBlob.class, StoneOfAugmentation.class, StoneOfEnchantment.class),
    TIER_DOWN_THREE(TierChange.TierDownThree.class, 1,  GooBlob.class, ScrollOfTransmutation.class, StoneOfAugmentation.class),

    ERASE(null, 1, Fadeleaf.Seed.class, Stormvine.Seed.class);


    public ArrayList<Class<? extends Item>> input = new ArrayList<>();
    public Class<? extends Inscription> inscription;
    public int triggers;

    //the first boolean is never used, but it can distinguish the constructors because wild class is "the same".
    EnchRecipe(Class<? extends Inscription> ench, int triggers, Class<? extends Item>... in){
        this.inscription = ench;
        this.input.addAll(Arrays.asList(in));
        this.triggers = triggers;
    }

    public static final int last_enchant_index = 1;
    //find a corresponding recipe and return it, or return null.
    public static EnchRecipe searchForRecipe(ArrayList<Class<? extends Item>> in){
        for(EnchRecipe recipe: EnchRecipe.values()) {
            if(in.size()<recipe.input.size()) continue;
            boolean contain=true;
            for (Class<? extends Item> itemClass : in) {
                if(!recipe.input.contains(itemClass)){
                    contain=false;
                    break;
                }
            }
            if(contain){
                return recipe;
            }
        }
        return null;
    }
    //simply enchant
    //the return boolean indicates if ingredients should be consumed.
    public static boolean enchant(Weapon toEnchant, EnchRecipe recipe){
        if(recipe == null){
            return false;
        }
        if(recipe.inscription == null){
            if(toEnchant.inscription != null){
                GLog.i(M.L(Inscription.class, "washed_away", toEnchant.name(), toEnchant.inscription.name()));
                toEnchant.inscription.detachFromWeapon();
            }else{
                GLog.i(M.L(Inscription.class, "washed_nothing", toEnchant.name()));
            }
            return true;
        }
        Inscription inscription = Reflection.newInstance(recipe.inscription);
        if(inscription == null){
            return false;
        }
        if(inscription instanceof CountInscription){
            ((CountInscription) inscription).setTriggers(recipe.triggers);
        }
        if(toEnchant.inscription != null){
            toEnchant.inscription.detachFromWeapon();
        }
        inscription.attachToWeapon(toEnchant);
        return true;
    }

}
