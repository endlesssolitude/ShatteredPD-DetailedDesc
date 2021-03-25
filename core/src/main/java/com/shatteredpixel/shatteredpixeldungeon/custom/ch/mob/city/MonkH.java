package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.city;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Monk;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.BallisticaFloat;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.GME;
import com.shatteredpixel.shatteredpixeldungeon.custom.visuals.MissileSpriteCustom;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gauntlet;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gloves;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class MonkH extends Monk {
    {
        EXP=13;
    }

    @Override
    public float attackDelay(){ return 0.9f*super.attackDelay();}

    protected int hitsToDisarm = 0;

    @Override
    public int attackProc(Char enemy, int damage){
        if (enemy == Dungeon.hero) {

            Hero hero = Dungeon.hero;
            KindOfWeapon weapon = hero.belongings.weapon;

            if (weapon != null
                    && !(weapon instanceof Gloves)
                    && !(weapon instanceof Gauntlet)
                    && !weapon.cursed) {
                if (hitsToDisarm == 0) hitsToDisarm = Random.NormalIntRange(5, 7);

                if (--hitsToDisarm == 0) {
                    hero.belongings.weapon = null;
                    Dungeon.quickslot.convertToPlaceholder(weapon);
                    Item.updateQuickslot();
                    GLog.w(Messages.get(this, "disarm", weapon.name()));

                    BallisticaFloat ba = new BallisticaFloat(hero.pos, GME.angle(pos, hero.pos) + Random.Float(-22.5f, 22.5f), 6, Ballistica.PROJECTILE);
                    ((MissileSpriteCustom) hero.sprite.parent.recycle(MissileSpriteCustom.class)).
                            reset(hero.sprite,
                                    ba.collisionPosI,
                                    weapon, 0.6f, 1.25f,
                                    new Callback() {
                                        @Override
                                        public void call() {
                                            Dungeon.level.drop(weapon, ba.collisionPosI).sprite.drop();
                                        }
                                    });
                }
            }
        }
        return super.attackProc(enemy, damage);
    }

    @Override
    public void storeInBundle(Bundle b){
        super.storeInBundle(b);
        b.put("hitToDisarm", hitsToDisarm);
    }
    @Override
    public void restoreFromBundle(Bundle b){
        super.restoreFromBundle(b);
        hitsToDisarm = b.getInt("hitToDisarm");
    }
}
