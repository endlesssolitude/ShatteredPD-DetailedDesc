package com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types;

import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.SingleType;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.TypeConsts;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;

public class TypeHazard extends SingleType {
    {
        type = TypeConsts.HAZARDS;
    }
    @Override
    public boolean isType(Object src) {
        if(src == null){
            return true;
        }
        //hope this works...
        return !(src instanceof Item || src instanceof Actor);
    }
}
