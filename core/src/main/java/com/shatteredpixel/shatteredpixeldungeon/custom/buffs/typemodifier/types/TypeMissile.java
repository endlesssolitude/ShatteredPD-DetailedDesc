package com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types;

import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.SingleType;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.TypeConsts;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;

public class TypeMissile extends SingleType {
    {
        type = TypeConsts.MISSILE;
    }
    @Override
    public boolean isType(Object src) {
        return src instanceof MissileWeapon;
    }
}
