package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.city;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Golem;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.RangeMap;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.utils.Bundle;
import com.watabou.utils.Reflection;

public class GolemH extends Golem {

    {
        EXP = 15;
    }
    {
        immunities.add(Corruption.class);
    }

    private int damageToElemental = 3;

    @Override
    public int defenseProc(Char enemy, int damage){
        damageToElemental--;
        if (damageToElemental <= 0) {
            summonElemental();
        }
        return super.defenseProc(enemy, damage);
    }

    @Override
    public void damage(int damage, Object src) {
        if (src instanceof Wand) {
            damageToElemental--;
            if (damageToElemental <= 0) {
                summonElemental();
            }
        }
        super.damage(damage, src);
    }

    protected void summonElemental(){
        damageToElemental += 3;
        int summonPos = -1;
        int[] range = RangeMap.manhattanRing(pos, 1, 2);
        for (int i : range) {
            if (!Dungeon.level.solid[i] && findChar(i) == null) {
                summonPos = i;
                break;
            }
        }
        if (summonPos == -1) summonPos = Dungeon.level.randomRespawnCell(this);
        ElementalH ele = Reflection.newInstance(ElementalH.random());
        ele.pos = summonPos;
        ele.HP = ele.HT / 2;
        ele.maxLvl = -100;
        ele.state = ele.HUNTING;
        GameScene.add(ele);
        sprite.emitter().start(ElmoParticle.FACTORY, 0.01f, 18);
    }

    @Override
    public void storeInBundle(Bundle b){
        super.storeInBundle(b);
        b.put("element", damageToElemental);
    }

    @Override
    public void restoreFromBundle(Bundle b){
        super.restoreFromBundle(b);
        damageToElemental = b.getInt("element");
    }

    @Override
    public int distance(Char enemy){
        int dist = super.distance(enemy);
        return  Math.max(dist, 1);
    }

}
