package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.hall;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Scorpio;
import com.shatteredpixel.shatteredpixeldungeon.effects.Wound;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.watabou.noosa.Camera;
import com.watabou.utils.Bundle;

//yeah it's already annoying enough
public class ScorpioH extends Scorpio {
    {
        viewDistance = 99;
    }

    private int hasAttacked = 0;

    @Override
    public int attackSkill(Char enemy){
        return super.attackSkill(enemy)*(5+hasAttacked)/5;
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        int d = super.attackProc(enemy, damage);
        ++hasAttacked;
        if(canHeadShot(enemy)) {
            d = Math.max(Math.max(enemy.HP - 1, enemy.HT * 2 / 3), d);
            Wound.hit(enemy);
            enemy.sprite.showStatus(CharSprite.NEGATIVE, "HEAD SHOT!!!");
            enemy.sprite.bloodBurstA(sprite.center(), enemy.HT);
            if(enemy instanceof Hero){
                Camera.main.shake(7f, 0.4f);
            }
        }else {
            d += (enemy.drRoll() + enemy.drRoll()) * hasAttacked / 24;
            if(hasAttacked > 5) Buff.affect(enemy, Blindness.class, 3f);
        }
        return d;
    }

    protected boolean canHeadShot(Char enemy){
        return hasAttacked > 6 && !enemy.fieldOfView[pos];
    }

    @Override
    public void move(int step){
        super.move(step);
        hasAttacked=Math.max(0,--hasAttacked);
    }

    @Override
    public void storeInBundle(Bundle b){
        super.storeInBundle(b);
        b.put("shots", hasAttacked);
    }

    @Override
    public void restoreFromBundle(Bundle b){
        super.restoreFromBundle(b);
        hasAttacked = b.getInt("shots");
    }

    @Override
    public void die(Object cause){
        super.die(cause);
        RipperH rh = new RipperH();
        rh.pos = Dungeon.level.randomRespawnCell(rh);
        GameScene.add(rh);
    }

}
