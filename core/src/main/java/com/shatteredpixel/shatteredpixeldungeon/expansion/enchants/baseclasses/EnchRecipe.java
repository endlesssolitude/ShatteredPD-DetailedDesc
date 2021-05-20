package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses;

import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.attack.Meteor;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor.Glyph;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.InfernalBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon.Enchantment;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Arrays;

//Enchantment and Glyph are actually the same thing but, they are designed differently. WTF.
public enum EnchRecipe{
    METEOR(true, Meteor.class, InfernalBrew.class, PotionOfLiquidFlame.class);

    public ArrayList<Class<? extends Item>> input = new ArrayList<>();
    public Class<? extends Enchantment> ench;
    public Class<? extends Glyph> glyph;
    public boolean isEnch = false;

    //the first boolean is never used, but it can distinguish the constructors because wild class is "the same".
    EnchRecipe(boolean noUse, Class<? extends Enchantment> ench, Class<? extends Item>... in){
        this.ench = ench;
        this.input.addAll(Arrays.asList(in));
        isEnch = true;
    }
    EnchRecipe(Class<? extends Glyph> glyph, Class<? extends Item>... in){
        this.glyph = glyph;
        this.input.addAll(Arrays.asList(in));
        isEnch = false;
    }

    public static final int last_enchant_index = 1;
    //find a corresponding recipe and return it, or return null.
    public static EnchRecipe searchForRecipe(boolean isEnchantment, Class<? extends Item>... in){
        for(EnchRecipe recipe: EnchRecipe.values()) {
            if(isEnchantment != recipe.isEnch) continue;
            if(in.length<recipe.input.size()) continue;
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
    public static boolean enchant(EquipableItem toEnchant, EnchRecipe recipe){
        boolean enchant = false;
        if(toEnchant instanceof Weapon){
            ((Weapon) toEnchant).enchant(Reflection.newInstance(recipe.ench));
            enchant = true;
        }
        if(toEnchant instanceof Armor){
            ((Armor) toEnchant).inscribe(Reflection.newInstance(recipe.glyph));
            enchant = true;
        }
        return enchant;
    }

}
