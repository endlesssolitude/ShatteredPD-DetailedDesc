package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.city;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Degrade;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Warlock;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import static com.shatteredpixel.shatteredpixeldungeon.items.Item.updateQuickslot;

public class WarlockH extends Warlock {
    private static final float TIME_TO_ZAP = 1f;

    @Override
    public void onZapComplete() {
        zapThis();
        next();
    }

    @Override
    protected boolean doAttack( Char enemy ) {

        if (Dungeon.level.adjacent( pos, enemy.pos )) {

            return super.doAttack( enemy );

        } else {

            if (sprite != null && (sprite.visible || enemy.sprite.visible)) {
                sprite.zap( enemy.pos );
                return false;
            } else {
                zapThis();
                return true;
            }
        }
    }

    private void zapThis() {
        spend( TIME_TO_ZAP );

        if (hit( this, enemy, true )) {
            //TODO would be nice for this to work on ghost/statues too
            if (enemy == Dungeon.hero && Random.Int( 2 ) == 0) {
                Buff.prolong(enemy, Degrade.class, Degrade.DURATION);
                Sample.INSTANCE.play(Assets.Sounds.DEBUFF);
            }

            if (enemy == Dungeon.hero && Random.Int( 2 ) == 0) {
                int drained = 0;
                for(Item i: Dungeon.hero.belongings){
                    if(i instanceof Wand){
                        if(Random.Int(50)==0) {((Wand) i).curCharges=-16;drained+=10;}
                        else{((Wand) i).curCharges -= 1;++drained;}
                    }
                }
                Sample.INSTANCE.play(Assets.Sounds.DEBUFF);
                enemy.sprite.emitter().burst( ShadowParticle.CURSE, 3+drained );
                updateQuickslot();
            }

            int dmg = Random.NormalIntRange( 12, 18 );
            enemy.damage( dmg, new DarkBolt() );

            if (enemy == Dungeon.hero && !enemy.isAlive()) {
                Dungeon.fail( getClass() );
                GLog.n( Messages.get(this, "bolt_kill") );
            }
        } else {
            enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
        }
    }

    private int hitsToDegrade = 0;
    @Override
    public int attackProc(Char enemy, int damage){

        ++hitsToDegrade;

        if(Random.Int(hitsToDegrade)>3 && enemy ==Dungeon.hero){

            Buff.prolong(enemy, Degrade.class, Degrade.DURATION/2f);
            Sample.INSTANCE.play(Assets.Sounds.DEBUFF);

            int drained = 0;
            for(Item i: Dungeon.hero.belongings){
                if(i instanceof Wand){
                    ((Wand) i).curCharges -= 1;
                    ++drained;
                }
            }
            enemy.sprite.emitter().burst( ShadowParticle.CURSE, 3+drained );
            updateQuickslot();

            hitsToDegrade = 0;
        }
        return super.attackProc(enemy, damage);
    }

    @Override
    public void storeInBundle(Bundle b){
        super.storeInBundle(b);
        b.put("hitsToDegrade", hitsToDegrade);
    }

    @Override
    public void restoreFromBundle(Bundle b){
        super.restoreFromBundle(b);
        hitsToDegrade = b.getInt("hitsToDegrade");
    }
}
