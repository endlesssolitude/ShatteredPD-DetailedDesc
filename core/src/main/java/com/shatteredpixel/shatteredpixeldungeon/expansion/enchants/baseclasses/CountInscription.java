package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;

public abstract class CountInscription extends Inscription{
    protected int leftTriggers;
    protected int defaultTriggers = 30;

    public CountInscription(){
        super();
        leftTriggers = defaultTriggers;
    }

    public CountInscription(int trigger){
        super();
        leftTriggers = trigger;
    }

    public CountInscription addTriggers(int t){
        leftTriggers += t;
        return this;
    }

    public CountInscription setTriggers(int t){
        leftTriggers = t;
        return this;
    }

    public int getLeftTriggers(){
        return this.leftTriggers;
    }

    public boolean consume(Weapon w, Char attacker){
        leftTriggers--;
        if(leftTriggers<=0){
            useUp(w, attacker);
            return true;
        }
        return false;
    }

    public void useUp(Weapon w, Char attacker){
        onLose();
        detachFromWeapon();
        GLog.w(M.L(CountInscription.class, "use_up"));
    }

    @Override
    public String desc() {
        return M.L(this, "desc", leftTriggers);
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put("left_triggers", leftTriggers);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        leftTriggers = bundle.getInt("left_triggers");
    }
}
