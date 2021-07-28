package com.shatteredpixel.shatteredpixeldungeon.custom.buffs.typemodifier;

import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public abstract class SingleType implements Bundlable {

    public int type;

    private float modifier;

    public float getModifier() {
        return modifier;
    }

    public void setModifier(float modifier) {
        this.modifier = modifier;
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        bundle.put("modifier", modifier);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        modifier = bundle.getFloat("modifier");
    }

    public abstract boolean isType(Object src);
}
