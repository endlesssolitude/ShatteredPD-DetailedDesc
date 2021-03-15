package com.shatteredpixel.shatteredpixeldungeon.custom.testmode;

import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.BallisticaFloat;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.BallisticaReal;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.TargetedCell;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;

import java.util.ArrayList;

public class PVCTester extends TestItem {
    {
        image = ItemSpriteSheet.WAND_DISINTEGRATION;
        defaultAction = AC_SHOOT;
    }

    private static final String AC_SHOOT = "shoot";
    private int degree = 0;

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_SHOOT );
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {
        super.execute(hero, action);
        if(action.equals(AC_SHOOT)){
            for(int i=0;i<7;++i) {
                /*
                int cell = PoleVectorConverter.findBestTargetCell(curUser.pos, degree, 20);
                Ballistica b = new Ballistica(curUser.pos, cell, Ballistica.WONT_STOP);
                int targrt = b.collisionPos;
                //int cell = PoleVectorConverter.findTargetCell(curUser.pos, degree, 33);
                curUser.sprite.parent.add(new Beam.DeathRay(curUser.sprite.center(), DungeonTilemap.tileCenterToWorld(
                        targrt
                )));

                for (int p : b.path){
                    curUser.sprite.parent.add(new TargetedCell(p, 0xFF0000));
                }
                damageChar(b);
                */
                BallisticaReal bf = new BallisticaReal(curUser.pos, degree, 6, BallisticaReal.STOP_SOLID);
                curUser.sprite.parent.add(new Beam.DeathRay(DungeonTilemap.tileCenterToWorld(curUser.pos), bf.collisionF.clone().scale(DungeonTilemap.SIZE)));
                curUser.sprite.parent.add(new TargetedCell(bf.collisionI, 0xFF0000));
                //for (int p : bf.pathI){
                 //   curUser.sprite.parent.add(new TargetedCell(p, 0xFF0000));
                //}
                //damageChar(bf);
                degree += 17;
            }
        }
    }

    protected void damageChar(BallisticaFloat bf){
        for(Integer pos: bf.subPath(1, bf.dist)){
            Char victim = Actor.findChar(pos);
            if(victim!=null){
                victim.damage(1, this);
            }
        }
    }
}
