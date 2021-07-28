package com.shatteredpixel.shatteredpixeldungeon.custom.ch.boss.goo;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.custom.ch.boss.BossDifficulty;
import com.shatteredpixel.shatteredpixeldungeon.custom.ch.boss.DR;
import com.shatteredpixel.shatteredpixeldungeon.custom.ch.boss.Timer;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.GME;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.RangeMap;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CausticSlimeSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;


//No defense, NEVER attack
public class MiniGoo extends Mob {
    {
        spriteClass = CausticSlimeSprite.class;

        DR.readFile(BossDifficulty.fullPath(HardGoo.whichBoss));
        HT = HP = DR.getInt(MiniGoo.class, "ht");

        EXP = 0;
        maxLvl = -999;

        defenseSkill = DR.getInt(MiniGoo.class, "eva");

        baseSpeed = DR.getFloat(MiniGoo.class, "speed");

        baseDamage = DR.getFloat(MiniGoo.class, "base_damage");

        passiveExplodeFactor = DR.getFloat(MiniGoo.class, "passive_explode_factor");

        suicideExplodeFactor = DR.getFloat(MiniGoo.class, "suicide_explode_factor");

        neighbourHeal = DR.getFloat(MiniGoo.class, "neighbour_heal");
    }

    private float baseDamage;
    private float passiveExplodeFactor;
    private float suicideExplodeFactor;
    private float neighbourHeal;

    @Override
    protected boolean canAttack(Char enemy) {
        return false;
    }

    @Override
    public boolean act(){
        Buff b = buff(Timer.class);
        if (b == null) {
            explode(true);
            die(this);
            return true;
        }else{
            if(b.cooldown() < 2f){
                sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "!!!") );
            }
        }

        return super.act();
    }

    protected boolean explode(boolean empower){
        int[] area = empower ? RangeMap.centeredRect(pos, 2, 2) : RangeMap.manhattanCircle(pos, 1);
        for(int n: area){
            if(!Dungeon.level.insideMap(n)) continue;
            Char ch=findChar(n);
            if(ch!=null){
                if(!(ch instanceof HardGoo || ch instanceof MiniGoo)) {
                    ch.damage(Math.round((empower?suicideExplodeFactor:passiveExplodeFactor)
                            *(Math.round(Random.Float(0.8f, 1.2f) * baseDamage))), this);
                    Buff.affect(ch, Vulnerable.class, empower?9f:3f);
                }
                if (ch == Dungeon.hero && !ch.isAlive()) {
                    Dungeon.fail(getClass());
                    GLog.n(M.L(MiniGoo.class, "explode_death", this.name()));
                }
            }

            Dungeon.level.setCellToWater(true, n);

            CellEmitter.get(n).start(ElmoParticle.FACTORY, 0.02f, 6);

        }
        if(!empower){
            for(int n: RangeMap.centeredRect(pos, 1, 1)){
                if(!Dungeon.level.insideMap(n)) continue;
                Char ch=findChar(n);
                if(ch!=null && ch.alignment == Alignment.ENEMY){
                    ch.HP = Math.min(ch.HP + GME.accurateRound(neighbourHeal), ch.HT);
                }
            }
        }

        Dungeon.level.setCellToWater(true, pos);

        return true;
    }

    @Override
    public void die(Object cause){
        if(buff(Timer.class)!=null){
            explode(false);
        }
        super.die(cause);
    }

    @Override
    public String name() {
        return super.name() + BossDifficulty.getName(BossDifficulty.getDifficulty(HardGoo.whichBoss));
    }
}
