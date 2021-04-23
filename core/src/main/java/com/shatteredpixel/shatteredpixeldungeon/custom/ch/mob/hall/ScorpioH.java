package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.hall;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Scorpio;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.effects.Wound;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.watabou.noosa.Camera;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

//yeah it's already annoying enough
public class ScorpioH extends Scorpio {
    {
        viewDistance = 99;
    }

    {
        immunities.add(Corruption.class);
    }

    private int hasAttacked = 0;

    @Override
    public int attackSkill(Char enemy){
        return super.attackSkill(enemy)*(5+hasAttacked)/5;
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        int d = super.attackProc(enemy, damage);
        hasAttacked = Math.min(8, ++hasAttacked);
        if(canHeadShot(enemy)) {
            d = Math.max(Math.max(enemy.HP - 1, enemy.HT * 2 / 3), d);
            Wound.hit(enemy);
            enemy.sprite.showStatus(CharSprite.NEGATIVE, M.L(this, "head_shot"));
            enemy.sprite.bloodBurstA(sprite.center(), enemy.HT);
            if(enemy instanceof Hero){
                Camera.main.shake(4f, 0.4f);
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

    @Override
    public void rollToDropLoot(){
        if (Dungeon.hero.lvl <= maxLvl + 2){
            float chance = 0.04f;
            chance *= RingOfWealth.dropChanceMultiplier( Dungeon.hero );
            chance = Math.min(0.1f, chance);
            if(Random.Float()<chance){
                RingOfSharpshooting r=new RingOfSharpshooting();
                r.level(Random.chances(new float[]{0.3f - 2f*chance, 0.4f + chance, 0.2f+chance/2f, 0.1f + chance/2f}));
                Dungeon.level.drop(r, pos).sprite.drop();
            }
        }

        super.rollToDropLoot();

    }
}
