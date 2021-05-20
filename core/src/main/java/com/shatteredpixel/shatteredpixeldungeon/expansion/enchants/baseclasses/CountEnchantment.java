package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.custom.visuals.HaloQuick;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Bundle;

public abstract class CountEnchantment extends Weapon.Enchantment {
    protected int glowColor;
    protected int leftTriggers;
    protected int defaultTriggers = 30;

    public CountEnchantment(){
        super();
        leftTriggers = defaultTriggers;
    }

    public CountEnchantment(int trigger){
        super();
        leftTriggers = trigger;
    }

    public CountEnchantment addTriggers(int t){
        leftTriggers += t;
        return this;
    }

    public CountEnchantment setTriggers(int t){
        leftTriggers += t;
        return this;
    }

    public int getLeftTriggers(){
        return this.leftTriggers;
    }

    public int getColor(){return glowColor;}

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        damage = myProc(weapon, attacker, defender, damage);
        consume(weapon, attacker);
        return damage;
    }

    public boolean consume(Weapon w, Char attacker){
        leftTriggers--;
        if(leftTriggers<=0){
            detach(w, attacker);
            return true;
        }
        return false;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        glowColor = Color();
        return new ItemSprite.Glowing(glowColor);
    }

    @Override
    public String desc() {
        if(leftTriggers < 10000) {
            return M.L(this, "desc") + M.L(this, "left_triggers");
        }
        return M.L(this, "desc");
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put("left_triggers", leftTriggers);
        bundle.put("glow_color", glowColor);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        leftTriggers = bundle.getInt("left_triggers");
        glowColor = bundle.getInt("glow_color");
    }
    //this triggers when enchantment is used up.
    public void detach(Weapon w, Char attacker){
        w.enchant(null);
        if(attacker.sprite != null){
            attacker.sprite.parent.add(new HaloQuick(2f, glowColor, 1f, 2.5f));
        }
    }

    protected abstract int myProc(Weapon w, Char attacker, Char defender, int damage);
    protected abstract int Color();
}
