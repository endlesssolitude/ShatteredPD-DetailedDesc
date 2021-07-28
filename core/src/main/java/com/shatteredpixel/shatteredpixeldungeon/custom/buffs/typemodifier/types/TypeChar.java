package com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.SingleType;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.TypeConsts;

public class TypeChar extends SingleType {
    {
        type = TypeConsts.CHAR;
    }
    @Override
    public boolean isType(Object src) {
        return src instanceof Char;
    }
}
