package com.shatteredpixel.shatteredpixeldungeon.custom.testmode;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.BallisticaFloat;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.BallisticaReal;
import com.shatteredpixel.shatteredpixeldungeon.custom.visuals.DelayerEffect;
import com.shatteredpixel.shatteredpixeldungeon.custom.visuals.effects.RotateBeam;
import com.shatteredpixel.shatteredpixeldungeon.effects.Effects;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;

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
            Actor.addDelayed(new Actor() {
                                 @Override
                                 protected boolean act() {
                                     final Actor toRemove = this;
                                     DelayerEffect.delayTime(0.5f, new Callback() {
                                         @Override
                                         public void call() {
                                            Actor.remove(toRemove);
                                            toRemove.next();
                                         }
                                     });
                                     return false;
                                 }
                             }, -1

            );

            hero.sprite.parent.add(new RotateBeam(Effects.Type.DEATH_RAY, new PointF(hero.pos% Dungeon.level.width(), hero.pos/Dungeon.level.width()).offset(0.5f, 0.1f),
                    45f, 360f, 40f, 200f, 1f, 1f).setProperty(BallisticaReal.STOP_SOLID));
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
