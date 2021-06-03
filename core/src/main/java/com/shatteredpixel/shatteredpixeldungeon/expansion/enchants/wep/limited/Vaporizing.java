package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.limited;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.GME;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses.CountInscription;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;

public class Vaporizing extends CountInscription {
    {
        defaultTriggers = 50;
    }
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        if (Dungeon.level.map[defender.pos] == Terrain.WATER){
            Level.set( defender.pos, Terrain.EMPTY);
            GameScene.updateMap( defender.pos );
            CellEmitter.get( defender.pos ).burst( Speck.factory( Speck.STEAM ), 10 );
            defender.damage(GME.accurateRound(Dungeon.depth * 1.2f + 5f), Vaporizing.class);
        } else {
            Buff.affect( defender, Burning.class ).reignite(defender, 3f);
        }
        consume(weapon, attacker);
        return damage;
    }
}