package com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier;

import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types.TypeAll;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types.TypeBlob;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types.TypeBuff;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types.TypeChar;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types.TypeHazard;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types.TypeItem;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types.TypeMelee;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types.TypeMissile;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types.TypeNone;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types.TypeWand;

import java.util.HashMap;

public class TypeConsts {
    public static final int NONE = 0x0;
    public static final int MELEE = 0x1;
    public static final int MISSILE = 0x2;
    public static final int WAND = 0x4;
    public static final int BUFF = 0x8;
    public static final int BLOB = 0x10;
    public static final int ITEMS = 0x20;
    public static final int CHAR = 0x40;
    public static final int HAZARDS = 0x40000000;
    public static final int ALL = 0x80000000;

    public static HashMap<Integer, Class<? extends SingleType>> typeFinder;
    static {
        typeFinder = new HashMap<>();
        typeFinder.put(NONE, TypeNone.class);
        typeFinder.put(MELEE, TypeMelee.class);
        typeFinder.put(MISSILE, TypeMissile.class);
        typeFinder.put(WAND, TypeWand.class);
        typeFinder.put(BUFF, TypeBuff.class);
        typeFinder.put(BLOB, TypeBlob.class);
        typeFinder.put(ITEMS, TypeItem.class);
        typeFinder.put(CHAR, TypeChar.class);
        typeFinder.put(HAZARDS, TypeHazard.class);
        typeFinder.put(ALL, TypeAll.class);
    }
}
