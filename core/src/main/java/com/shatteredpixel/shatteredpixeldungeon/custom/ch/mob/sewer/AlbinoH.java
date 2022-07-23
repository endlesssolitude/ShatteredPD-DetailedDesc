package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.sewer;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AllyBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Albino;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.sprites.AlbinoSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class AlbinoH extends Mob {
    {
        spriteClass = AlbinoSprite.class;

        HP = HT = 12 + Dungeon.depth * 4 - 4;
        defenseSkill = 5 + Dungeon.depth - 1;
        EXP = 3;

        loot = new MysteryMeat();
        lootChance = 1f;
    }

    {
        immunities.add(AllyBuff.class);
    }
    @Override
    public int damageRoll() {
        return Random.IntRange(1, 4 + Dungeon.depth - 1);
    }

    @Override
    public int attackSkill(Char target) {
        return 10 + 2*Dungeon.depth;
    }

    @Override
    public int drRoll() {
        return Random.IntRange(0, Dungeon.depth);
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        damage = super.attackProc( enemy, damage );
        if (Random.Int( 100 ) < 38 + Dungeon.depth * 7) {
            Buff.affect( enemy, Bleeding.class ).set( damage );
        }

        return damage;
    }
}
