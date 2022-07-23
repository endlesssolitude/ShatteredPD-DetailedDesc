package com.shatteredpixel.shatteredpixeldungeon.expansion.mergeManagers;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.AttributeModifier;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.IgnoreArmor;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.PositiveBuffProhibition;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.ThornsShield;
import com.shatteredpixel.shatteredpixeldungeon.custom.buffs.ZeroAttack;
import com.shatteredpixel.shatteredpixeldungeon.custom.ch.boss.HDKItem;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.ImmortalShieldAffecter;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.GME;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

//Interface for attributes/damage/effects modifying and processing.
public enum CombatModifier {

    INSTANCE;

    public float attackModify(Char attacker, Char defender, float atk){
        AttributeModifier modifier = attacker.buff(AttributeModifier.class);
        if(modifier != null){
            atk = modifier.affectAtk(atk);
        }

        if(attacker.buff(ZeroAttack.class) != null){
            atk = 0;
        }
        return atk;
    }

    //modify dr. Happens after defProc but before armor reduces damage.
    public int defenseModify(Char attacker, Char defender, int dr, int damage){
        AttributeModifier modifier = defender.buff(AttributeModifier.class);
        if(modifier != null){
            dr = modifier.affectDef(dr);
        }
        if(attacker.buff(IgnoreArmor.class) != null) {
            dr = 0;
        }
        return dr;
    }

    public float accuracyModify(Char attacker, Char defender, float accuracy){
        AttributeModifier modifier = attacker.buff(AttributeModifier.class);
        if(modifier != null){
            accuracy = modifier.affectAcc(accuracy);
        }
        return accuracy;
    }

    public float evasionModify(Char attacker, Char defender, float evasion){
        AttributeModifier modifier = defender.buff(AttributeModifier.class);
        if(modifier != null){
            evasion= modifier.affectEva(evasion);
        }
        return evasion;
    }


    //modify what happens in atkProc.
    public int attackProc(Char attacker, Char defender, int damage) {
        /*
        PotionExpEX.PlainVampire pv = attacker.buff(PotionExpEX.PlainVampire.class);
        if(pv != null){
            damage = pv.trigger(weapon, attacker, defender, damage);
        }
        PotionFireEX.FireBeam fb = attacker.buff(PotionFireEX.FireBeam.class);
        if(fb != null){
            damage = fb.trigger(weapon, attacker, defender, damage);
        }

         */
        return damage;
    }

    //modify what happens in defProc
    public int defenseProc(Char attacker, Char defender, int damage){
        /*


        PotionFrostEX.IceArmor ia = defender.buff(PotionFrostEX.IceArmor.class);
        if(ia != null){
            damage = ia.trigger(armor, attacker, defender, damage);
        }

        PotionFireEX.FireArmor fa = defender.buff(PotionFireEX.FireArmor.class);
        if(fa!=null){
            damage = fa.trigger(armor, attacker, defender, damage);
        }

         */

        return damage;

    }

    //should have a tracker on who caused the damage
    //modify what happens before taking final damage
    public int damage(Char ch, int damage, Object src) {
        //Buff.detach(ch, PotionFrostEX.DiffusiveFrost.class);

        HDKItem.KingAmulet.DamageCheater damageCheater = ch.buff(HDKItem.KingAmulet.DamageCheater.class);
        if (damageCheater != null) {
            damage = damageCheater.onDamage(ch, damage);
        }

        if (ch.buff(ImmortalShieldAffecter.ImmortalShield.class) != null) {
            ch.sprite.showStatus(0x00FFFF, "%d", damage);
            damage = 0;
        }

        if(ch.buff(ThornsShield.class) != null){
            if(src instanceof Char){
                ((Char) src).damage(GME.accurateRound(damage* Random.Float(.7f, .8f)), this);
            }else{
                damage = 0;
            }
            if(src == Dungeon.hero && !((Hero) src).isAlive()){
                Dungeon.fail(ThornsShield.class);
                GLog.n(M.L(this, "ondeath"));
            }
        }

        AttributeModifier modifier = ch.buff(AttributeModifier.class);
        if(modifier != null){
            damage = modifier.affectDmg(damage);
        }

        return damage;
    }

    public boolean preAddBuff(Char ch, Buff b){

        return true;
    }

    public void postAddBuff(Char ch, Buff buff){
        if(ch.buff(PositiveBuffProhibition.class) != null){
            if(!buff.revivePersists && buff.type == Buff.buffType.POSITIVE){
                new FlavourBuff(){
                    {
                        actPriority = VFX_PRIO;
                    }
                    @Override
                    public void detach() {
                        buff.detach();
                        super.detach();
                    }
                }.attachTo(ch);

            }
        }
    }


}
