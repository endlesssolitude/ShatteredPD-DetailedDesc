package com.shatteredpixel.shatteredpixeldungeon.custom.interfaces;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;

public interface DamageListener {
    public void onDamage(Char victim, int damage, Object src);
    public int modifyDamage(Char victim, int damage, Object src);
}
