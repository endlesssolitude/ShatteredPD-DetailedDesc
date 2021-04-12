package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.container;

import com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enhancedPotion.EnhancedPotion;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PotionBag extends Bag {
    {
        image = ItemSpriteSheet.POTION_AZURE;
        unique = true;
    }

    @Override
    public boolean canHold( Item item ) {
        if (item instanceof EnhancedPotion){
            return super.canHold(item);
        } else {
            return false;
        }
    }

    @Override
    public int value() {
        return 40;
    }

    public int capacity(){
        return 19;
    }
}
