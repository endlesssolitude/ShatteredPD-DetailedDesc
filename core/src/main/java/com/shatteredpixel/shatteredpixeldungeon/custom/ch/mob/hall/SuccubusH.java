package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.hall;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Succubus;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.RangeMap;
import com.shatteredpixel.shatteredpixeldungeon.custom.visuals.effects.BeamCustom;
import com.shatteredpixel.shatteredpixeldungeon.effects.Effects;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;

public class SuccubusH extends Succubus {
    @Override
    public int attackProc(Char enemy, int damage){
        Charm charm = enemy.buff(Charm.class);
        boolean armorBreak = charm != null && charm.object == enemy.id();
        int dmg = super.attackProc(enemy, damage);
        if(armorBreak){
            //avoid extreme cases
            dmg += Math.min(enemy.drRoll(), enemy.drRoll())*3/5;
        }
        for(Char ch: Actor.chars()){
            if(ch.alignment == Alignment.ENEMY){
                if(ch!=this){
                    int dist = RangeMap.manhattanDist(this.pos, enemy.pos);
                    if(dist < 5){
                        int heal = 40/(2+dist);
                        Buff.affect(ch, Healing.class).setHeal(heal, 1, 0);
                        ch.sprite.showStatus(CharSprite.POSITIVE, "+%d", heal);
                        sprite.parent.add(new BeamCustom(sprite.center(), ch.sprite.center(), Effects.Type.HEALTH_RAY).setDiameter(0.5f).setLifespan(0.25f));
                    }
                }
            }
        }
        for(Mob m: Dungeon.level.mobs.toArray(new Mob[0])){
            if(m instanceof Succubus && m != this){
                m.beckon(pos);
            }
        }
        return dmg;
    }

    @Override
    public void die(Object cause){
        super.die(cause);
        RipperH rh = new RipperH();
        rh.pos = Dungeon.level.randomRespawnCell(rh);
        GameScene.add(rh);
    }


}
