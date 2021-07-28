package com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.SingleType;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.TypeConsts;

public class TypeBuff extends SingleType {
    {
        type = TypeConsts.BUFF;
    }
    @Override
    public boolean isType(Object src) {
        return src instanceof Buff;
    }
}
