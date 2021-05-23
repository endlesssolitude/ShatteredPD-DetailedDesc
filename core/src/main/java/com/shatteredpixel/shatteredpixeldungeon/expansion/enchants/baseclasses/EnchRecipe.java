package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses;

import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.limited.Meteor;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.InfernalBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Arrays;

public enum EnchRecipe{
    METEOR(Meteor.class, 80, InfernalBrew.class, PotionOfLiquidFlame.class);

    public ArrayList<Class<? extends Item>> input = new ArrayList<>();
    public Class<? extends Inscription> inscription;
    public int triggers=0;

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
    public static boolean enchant(Weapon toEnchant, EnchRecipe recipe){
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
