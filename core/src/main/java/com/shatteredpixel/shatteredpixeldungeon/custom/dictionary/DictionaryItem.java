package com.shatteredpixel.shatteredpixeldungeon.custom.dictionary;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class DictionaryItem extends Item {
    {
        unique = true;
    }

    @Override
    public String name(){
        String s = super.name();
        if(s.equals("!!!NO TEXT FOUND!!!")){
            GLog.w(this.getClass().getName() + "name missing!");
        }
        return s;
    }

    @Override
    public String info(){
        String s = super.desc();
        if(s.equals("!!!NO TEXT FOUND!!!")){
            GLog.w(this.getClass().getName() + "desc missing!");
        }
        return s;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

}
