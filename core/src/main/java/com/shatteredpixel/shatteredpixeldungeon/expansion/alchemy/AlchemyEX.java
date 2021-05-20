package com.shatteredpixel.shatteredpixeldungeon.expansion.alchemy;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.Game;

import java.util.ArrayList;

public class AlchemyEX extends Item implements AlchemySceneEX.AlchemyExpansionProvider {
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

            AlchemySceneEX.setProvider(this);
            Game.switchScene(AlchemySceneEX.class);

        }
    }

    @Override
    public int getEnergy() {
        return 77777;
    }

    @Override
    public void spendEnergy(int reduction) {

    }
}
