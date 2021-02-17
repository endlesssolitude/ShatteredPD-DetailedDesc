package com.shatteredpixel.shatteredpixeldungeon.custom.challenges.challengeboss;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MindVision;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class BackgroundBeamCounter extends Buff {
    public float left = 9999999f;

    protected boolean shouldExist(Ballistica beam){
        boolean shouldExist = false;
        for (int p : beam.path) {if(Dungeon.level.solid[p]) {shouldExist = true; break;}}
        return shouldExist;
    }

    protected void beamHit(Ballistica beam){

        for (int p : beam.path) {
            if (Dungeon.level.flamable[p]) {
                Dungeon.level.destroy( p );
                GameScene.updateMap( p );
            }
            Char ch = findChar(p);
            if (ch != null) {
                if (ch.alignment != Char.Alignment.ENEMY) {
                    Buff.affect(ch, Blindness.class, 5f);
                    Buff.detach(ch, MindVision.class);
                    ch.damage(Random.IntRange(10, 15), TenguHard.class);
                    if (ch == Dungeon.hero && !ch.isAlive()) {
                        Dungeon.fail(getClass());
                    }
                }
            }
        }
    }

    @Override
    public boolean act() {
        if(!target.isAlive()) detach();
        int w = Dungeon.level.width();
        int x = Dungeon.level.width() - 2 ;
        int y = Dungeon.level.height() - 2;
        for(int i=0; i<x; ++i) {
            if ((int) (left*2f) % 5 == i % 5) {
                Ballistica beam = new Ballistica(i + 1 + w, i + 1 + (y - 1) * w, Ballistica.WONT_STOP);
                if(shouldExist(beam)) {
                    target.sprite.parent.add(new Beam.DeathRay(DungeonTilemap.raisedTileCenterToWorld(beam.sourcePos), DungeonTilemap.raisedTileCenterToWorld(beam.collisionPos)));
                    beamHit(beam);
                }
            }
        }

        for(int i=0; i<y; ++i) {
            if ((int) (left*2f) % 5 == (i+2) % 5) {
                Ballistica beam = new Ballistica(w*(i+1) + 1, w*(i+2)-1, Ballistica.WONT_STOP);
                if(shouldExist(beam)) {
                    target.sprite.parent.add(new Beam.DeathRay(DungeonTilemap.raisedTileCenterToWorld(beam.sourcePos), DungeonTilemap.raisedTileCenterToWorld(beam.collisionPos)));
                    beamHit(beam);
                }
            }
        }

        spend(TICK);
        left -= TICK;
        return true;
    }

    private static final String LEFT = "efc_left";
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( LEFT, left );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        left = bundle.getFloat( LEFT );
    }

}
