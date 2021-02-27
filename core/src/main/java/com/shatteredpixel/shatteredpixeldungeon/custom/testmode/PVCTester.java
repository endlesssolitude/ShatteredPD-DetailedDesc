package com.shatteredpixel.shatteredpixeldungeon.custom.testmode;

import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.BallisticaFloat;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

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
            for(int i=0;i<11;++i) {
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
                com.shatteredpixel.shatteredpixeldungeon.custom.utils.BallisticaFloat bf = new com.shatteredpixel.shatteredpixeldungeon.custom.utils.BallisticaFloat(curUser.pos, degree, 6, com.shatteredpixel.shatteredpixeldungeon.custom.utils.BallisticaFloat.WONT_STOP);
                curUser.sprite.parent.add(new Beam.DeathRay(curUser.sprite.center(), com.shatteredpixel.shatteredpixeldungeon.custom.utils.BallisticaFloat.coordToScreen(bf.collisionPosF)));
                //for (int p : bf.pathI){
                 //   curUser.sprite.parent.add(new TargetedCell(p, 0xFF0000));
                //}
                damageChar(bf);
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
