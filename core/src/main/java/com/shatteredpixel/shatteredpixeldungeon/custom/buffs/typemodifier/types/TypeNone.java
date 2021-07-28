package com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types;

import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.SingleType;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.TypeConsts;

public class TypeNone extends SingleType {
    {
        type = TypeConsts.NONE;
    }
    @Override
    public boolean isType(Object src) {
        return false;
    }
}
