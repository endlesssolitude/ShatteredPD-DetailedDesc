package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.prison;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Thief;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.watabou.utils.Random;

public class ThiefH extends Thief {
    {
        EXP = 6;
        HT = HP = 21;
    }

    {
        immunities.add(Corruption.class);
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

    @Override
    public void rollToDropLoot(){
        if (Dungeon.hero.lvl <= maxLvl + 2){
            float chance = 0.15f;
            chance *= RingOfWealth.dropChanceMultiplier( Dungeon.hero );
            if(Random.Float()<chance){
                Dungeon.level.drop(new PotionOfHaste(), pos).sprite.drop();
            }
        }

        super.rollToDropLoot();

    }

}
