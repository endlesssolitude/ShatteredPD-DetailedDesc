package com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types;

import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.SingleType;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.TypeConsts;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;

public class TypeMelee extends SingleType {
    {
        type = TypeConsts.MELEE;
    }

    @Override
    public boolean isType(Object src) {
        return src instanceof MeleeWeapon;
    }
}
