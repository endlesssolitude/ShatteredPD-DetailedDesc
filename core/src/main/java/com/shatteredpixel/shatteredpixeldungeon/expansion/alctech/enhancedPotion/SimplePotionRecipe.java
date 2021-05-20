package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enhancedPotion;

import com.shatteredpixel.shatteredpixeldungeon.expansion.alchemy.CustomRecipe;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;

import java.util.ArrayList;

public class SimplePotionRecipe extends CustomRecipe.FixedRecipe{
    protected int lvl=0;

    @Override
    public Item sampleOutput(ArrayList<Item> ingredients){
        Item result = super.sampleOutput(ingredients);
        if(result instanceof EnhancedPotion){
            ((EnhancedPotion)result).setLevel(lvl);
        }
        return result;
    }
}
