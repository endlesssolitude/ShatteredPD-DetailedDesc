package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.buffs;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.watabou.utils.Bundle;

public abstract class DefendBuff extends Buff {
    protected int hits;
    protected float rate;

    public DefendBuff setHits(int hits){
        this.hits = hits;
        return this;
    }

    public DefendBuff addHits(int hits){
        this.hits += hits;
        return this;
    }

    public DefendBuff setRate(float rate){
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

    @Override
    public String desc(){return M.L(this, "desc", hits);}

    @Override
    public String heroMessage() {
        return "";
    }

    @Override
    public String toString() {
        return M.L(this, "name");
    }

    public int trigger(Armor a, Char attacker, Char defender, int damage) {
        consume();
        return proc(a, attacker, defender, damage);
    }

    protected abstract int proc(Armor a, Char attacker, Char defender, int damage);
}
