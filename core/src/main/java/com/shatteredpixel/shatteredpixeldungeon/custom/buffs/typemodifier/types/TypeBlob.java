package com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.types;

import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.SingleType;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier.TypeConsts;

public class TypeBlob extends SingleType {
    {
        type = TypeConsts.BLOB;
    }
    @Override
    public boolean isType(Object src) {
        return src instanceof Blob;
    }
}
