package com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types;

import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.SingleType;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.TypeConsts;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;

public class TypeWand extends SingleType{
    {
        type = TypeConsts.WAND;
    }
    @Override
    public boolean isType(Object src) {
        return src instanceof Wand;
    }
}
