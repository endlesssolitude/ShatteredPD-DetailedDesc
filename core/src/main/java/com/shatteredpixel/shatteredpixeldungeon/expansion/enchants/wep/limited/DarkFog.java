package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.limited;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.SmokeScreen;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses.CountInscription;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;

public class DarkFog extends CountInscription {
    {
        defaultTriggers = 44;
    }
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        GameScene.add(Blob.seed(attacker.pos, Math.min(weapon.buffedLvl() * 3 + 20, 50), SmokeScreen.class));
        consume(weapon, attacker);
        return damage;
    }

    @Override
    public void useUp(Weapon w, Char attacker) {
        super.useUp(w, attacker);
        GameScene.add(Blob.seed(attacker.pos, 1000, SmokeScreen.class));
    }
}
