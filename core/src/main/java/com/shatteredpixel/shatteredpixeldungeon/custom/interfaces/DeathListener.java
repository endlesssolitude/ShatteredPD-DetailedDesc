package com.shatteredpixel.shatteredpixeldungeon.custom.interfaces;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;

public interface DeathListener {
    public void onDeath(Char victim, Object cause);
}
