package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enchs;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.InventoryScroll;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;

public class ScrollOfTestInch extends InventoryScroll {
    {
        //mode = WndBag.Mode.EQUIPMENT;
    }

    @Override
    protected void onItemSelected(Item item) {
        if(item instanceof Weapon){
            //((Weapon) item).enchant(new MeteorFalling());
        }
        if(item instanceof Armor){
            //((Armor) item).inscribe(new Igniting());
        }
    }
}
