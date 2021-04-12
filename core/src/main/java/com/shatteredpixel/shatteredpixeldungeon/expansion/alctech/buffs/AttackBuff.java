package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.buffs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.watabou.utils.Bundle;

public abstract class AttackBuff extends Buff {

    protected int hits;
    protected float rate;

    public AttackBuff setHits(int hits){
        this.hits = hits;
        return this;
    }

    public AttackBuff addHits(int hits){
        this.hits += hits;
        return this;
    }

    public AttackBuff setRate(float rate){
        this.rate = rate;
        return this;
    }

    public void consume(){
        --hits;
        if(hits<=0) detach();
    }

    public float rate(){return this.rate;}

    @Override
    public void storeInBundle(Bundle b){
        super.storeInBundle(b);
        b.put("hits_left", hits);
        b.put("rate_for_attack", rate);
    }

    @Override
    public void restoreFromBundle(Bundle b){
        super.restoreFromBundle(b);
        hits = b.getInt("hits_left");
        rate = b.getInt("rate_for_attack");
    }

    public int trigger(Weapon w, Char attacker, Char defender, int damage) {
        consume();
        return proc(w, attacker, defender, damage);
    }

    protected abstract int proc(Weapon w, Char attacker, Char defender, int damage);
}
