package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.sewer;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Gnoll;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.custom.visuals.MissileSpriteCustom;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

public class GnollH extends Gnoll {
    {
        EXP = 3;

        lootChance = 1f;


    }

    {
        immunities.add(Corruption.class);
    }

    private int dartLeft = 2;
    private boolean rangedAttack = false;

    @Override
    protected boolean canAttack(Char enemy){
        if(dartLeft>0){
            Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE);
            if(!Dungeon.level.adjacent( pos, enemy.pos ) && attack.collisionPos == enemy.pos){
                rangedAttack = true;
                return true;
            }
        }
        rangedAttack  = false;
        return super.canAttack(enemy);
    }

    @Override
    protected boolean doAttack(Char enemy){
        if(rangedAttack){
            dartLeft--;
            spend( attackDelay() );

            Actor.addDelayed(new Actor() {
                @Override
                public boolean act(){
                    final Actor toRemove = this;
                    ((MissileSpriteCustom)sprite.parent.recycle(MissileSpriteCustom.class)).reset(
                            pos, enemy.pos, new DartShot(), 1.25f, 0f,
                            new Callback() {
                                @Override
                                public void call() {
                                    onAttackComplete();
                                    Actor.remove(toRemove);
                                    toRemove.next();
                                }
                            }
                    );
                    return false;
                }
            },
                    -1);
            next();
            return true;
        }
        return super.doAttack(enemy);
    }

    @Override
    public void damage(int damage, Object src){
        if(enemySeen && enemy!=null) {
            for (Mob m: Dungeon.level.mobs.toArray(new Mob[0])){
                if(m instanceof GnollH && Dungeon.level.distance(pos, m.pos)<9){
                    m.beckon(enemy.pos);
                }
            }
        }
        super.damage(damage, src);
    }

    @Override
    public int damageRoll(){
        return super.damageRoll() - (rangedAttack ? 1:0);
    }

    @Override
    public int attackSkill(Char enemy){
        return Math.round(super.attackSkill(enemy)*(rangedAttack?1.5f:1f));
    }

    @Override
    public void storeInBundle(Bundle b){
        super.storeInBundle(b);
        b.put("dartLeft", dartLeft);
    }

    @Override
    public void restoreFromBundle(Bundle b){
        super.restoreFromBundle(b);
        dartLeft = b.getInt("dartLeft");
    }

    public static class DartShot extends Item {
        {
            image = ItemSpriteSheet.DART;
        }
    }
}
