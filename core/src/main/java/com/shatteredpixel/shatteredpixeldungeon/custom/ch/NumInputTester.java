package com.shatteredpixel.shatteredpixeldungeon.custom.ch;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.TestItem;
import com.shatteredpixel.shatteredpixeldungeon.custom.visuals.TextField;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

import java.util.ArrayList;

public class NumInputTester extends TestItem {
    {
        defaultAction = AC_INVOKE;
    }
    private static final String AC_INVOKE = "invoke";

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_INVOKE);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if(action.equals(AC_INVOKE)){
            GameScene.show(new WndInput());
        }
    }

    public static class WndInput extends Window{
        public WndInput(){
            TextField name = new TextField("title", ""){
                @Override
                public void onTextChange() {

                    GLog.i(text());
                }

                @Override
                public void onTextCancel() {
                }
            };
            name.setHint("no input means random");
            name.setLarge(false);
            name.setMaxStringLength(16);
            name.setRect(0,2, 120, 20);
            add(name);

            resize(120, 80);
        }
    }
}
