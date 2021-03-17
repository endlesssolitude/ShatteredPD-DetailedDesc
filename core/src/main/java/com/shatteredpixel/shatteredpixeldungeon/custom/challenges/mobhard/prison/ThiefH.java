package com.shatteredpixel.shatteredpixeldungeon.custom.challenges.mobhard.prison;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Thief;

public class ThiefH extends Thief {
    {
        EXP = 6;
        HT = HP = 21;
    }

    @Override
    protected boolean canAttack(Char enemy){
        if(enemy!=null && enemySeen && Dungeon.level.distance(pos, enemy.pos) < 3){
            return true;
        }
        return super.canAttack(enemy);
    }

    @Override
    public int attackProc(Char enemy, int damage){
        for(Mob m: Dungeon.level.mobs.toArray(new Mob[0])){
            if(m!=this && m.alignment == Alignment.ENEMY){
                if(m instanceof ThiefH){
                    if(((ThiefH) m).item == null){
                        Buff.affect(m, Haste.class, 5f);
                        m.beckon(enemy.pos);
                    }
                }
            }
        }
        return super.attackProc(enemy, damage);
    }

    @Override
    protected boolean steal( Hero hero ){
        boolean stole = super.steal(hero);
        if(stole) Buff.detach(this, Haste.class);
        return stole;
    }

}
