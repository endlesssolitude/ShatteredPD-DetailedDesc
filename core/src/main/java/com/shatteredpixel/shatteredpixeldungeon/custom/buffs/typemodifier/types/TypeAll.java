package com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types;

import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.SingleType;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.TypeConsts;

public class TypeAll extends SingleType {
    {
        type = TypeConsts.ALL;
    }
    @Override
    public boolean isType(Object src) {
        return true;
    }
}
