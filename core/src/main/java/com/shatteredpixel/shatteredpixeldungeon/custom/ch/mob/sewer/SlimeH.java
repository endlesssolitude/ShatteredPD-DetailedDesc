package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.sewer;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Slime;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;

public class SlimeH extends Slime {
    @Override
    public int attackProc(Char enemy, int damage){
        Ballistica trajectory = new Ballistica(pos, enemy.pos, Ballistica.STOP_TARGET);
        trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size()-1), Ballistica.PROJECTILE);
        WandOfBlastWave.throwChar(enemy, trajectory, 2);
        return super.attackProc(enemy,damage);
    }

    @Override
    public void damage(int damage, Object src){
        damage = Math.min(damage,5);
        super.damage(damage, src);
    }
}
