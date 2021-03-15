package com.shatteredpixel.shatteredpixeldungeon.custom.testmode;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.scenes.AlchemyScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.Game;

import java.util.ArrayList;

public class AlchemizeSimulator extends TestItem implements AlchemyScene.AlchemyProvider {
    {
        image = ItemSpriteSheet.ARTIFACT_TOOLKIT;
        defaultAction = AC_BREW;
    }

    private static final String AC_BREW = "brew";

    private int charge = 2147483647;

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add(AC_BREW);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action ) {

        super.execute(hero, action);

        if (action.equals(AC_BREW)){

                AlchemyScene.setProvider(this);
                Game.switchScene(AlchemyScene.class);

        }
    }

    @Override
    public int getEnergy() {
        return charge;
    }

    @Override
    public void spendEnergy(int reduction) {
        charge -= reduction;
    }
}
