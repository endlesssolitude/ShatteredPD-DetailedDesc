package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.city;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Monk;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.BallisticaFloat;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.GME;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Pasty;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gauntlet;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gloves;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SeniorSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class SeniorMonkH extends Monk {
    {
        spriteClass = SeniorSprite.class;
        lootChance=1f;
        loot = new Pasty();
    }

    @Override
    public float attackDelay(){return super.attackDelay()*0.85f;}

    protected int hitsForSkill = 0;

    @Override
    public int attackProc(Char enemy, int damage){
        ++hitsForSkill;
        if(enemy == Dungeon.hero && hitsForSkill>6){
            hitsForSkill = 0;
            Hero hero = Dungeon.hero;
            BallisticaFloat ba = new BallisticaFloat(enemy.pos, GME.angle(pos, enemy.pos)+ Random.Float(-22.5f, 22.5f), 8, BallisticaFloat.PROJECTILE);
            Actor.add(new Pushing(hero, hero.pos, ba.collisionPosI, new Callback() {
                @Override
                public void call() {
                    Camera.main.shake(3f, 0.5f);
                    Buff.affect(hero, Blindness.class, 6f);
                    Buff.affect(hero, Vertigo.class, 8f);
                    Buff.affect(hero, Cripple.class, 8f);
                    hero.sprite.centerEmitter().burst(Speck.factory(Speck.STAR), 10);
                    hero.pos = ba.collisionPosI;
                    Dungeon.level.occupyCell(hero);
                }
            }));
            KindOfWeapon wep = hero.belongings.weapon;
            if(wep != null && !wep.cursed && !(wep instanceof Gloves || wep instanceof Gauntlet)){
                Dungeon.level.drop(wep, hero.pos).sprite.drop();
                hero.belongings.weapon = null;
                Dungeon.quickslot.convertToPlaceholder(wep);
                Item.updateQuickslot();
            }

            GLog.n(M.L(this, "hard_strike"));
            spend(attackDelay());
        }
        return super.attackProc(enemy, damage);
    }

    @Override
    public void move( int step ) {
        // moving reduces cooldown by an additional 0.67, giving a total reduction of 1.67f.
        // basically monks will become focused notably faster if you kite them.
        // additional 0.67
        focusCooldown -= 0.67f;
        super.move( step );
    }

    @Override
    public void storeInBundle(Bundle b){
        super.storeInBundle(b);
        b.put("hitsForSkill", hitsForSkill);
    }

    @Override
    public void restoreFromBundle(Bundle b){
        super.restoreFromBundle(b);
        hitsForSkill = b.getInt("hitsForSkill");
    }
}
