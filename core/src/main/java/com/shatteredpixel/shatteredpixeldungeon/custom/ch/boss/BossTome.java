package com.shatteredpixel.shatteredpixeldungeon.custom.ch.boss;

import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.custom.ch.ChallengeItem;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.ScrollPane;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;

public class BossTome extends ChallengeItem {
    {
        unique = true;
        image = ItemSpriteSheet.ARTIFACT_SPELLBOOK;
        defaultAction = AC_SET;
    }

    private static final String AC_SET = "set";

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> action = super.actions(hero);
        action.add(AC_SET);
        return action;
    }

    @Override
    public void execute(Hero hero, String action) {
        if(action.equals(AC_SET)){
            GameScene.show(new SettingsWindow());
        }else {
            super.execute(hero, action);
        }
    }

    @Override
    public String desc() {
        StringBuilder sb = new StringBuilder(M.L(BossTome.class, "desc"));
        for(int i=0; i<8;++i){
            int difficulty = BossDifficulty.getDifficulty(i);
            if(difficulty > 0){
                sb.append('\n');
                sb.append(5*i+5);
                sb.append("  ");
                sb.append(BossDifficulty.getName(difficulty));
            }
        }
        return sb.toString();
    }

    public static class SettingsWindow extends Window{
        private static final int WIDTH = 120;
        private static final int BOX_SIZE = 16;
        private static final int TTL_HEIGHT = 12;
        private static final int GAP= 2;
        private static final int MAX_HEIGHT = 150;

        private ArrayList<RenderedTextBlock> allText;
        private ArrayList<RedButton> lhsButtons;
        private ArrayList<RedButton> rhsButtons;
        private int[] difficultyCache;

        public SettingsWindow(){
            resize(WIDTH, 180);

            RenderedTextBlock rtb = PixelScene.renderTextBlock(M.L(BossTome.class, "title"), TTL_HEIGHT - GAP);
            rtb.setPos(WIDTH/2f - rtb.width()/2, GAP);
            PixelScene.align(rtb);
            rtb.hardlight(0x00FF00);
            add(rtb);

            float pos = 0;

            allText = new ArrayList<>();
            lhsButtons = new ArrayList<>();
            rhsButtons = new ArrayList<>();
            difficultyCache = new int[5];

            ScrollPane sp = new ScrollPane(new Component()){
                @Override
                public void onClick(float x, float y) {
                    super.onClick(x, y);
                    for(int i = 0, L = allText.size(); i < L; ++i){
                        RenderedTextBlock rtb = allText.get(i);
                        if(x > rtb.left() && x < rtb.right() && y > rtb.top() && y < rtb.bottom()){
                            StringBuilder descName = new StringBuilder("desc_");
                            switch (i){
                                case 0: descName.append("goo_"); break;
                                case 1: descName.append("tengu_"); break;
                                case 2: descName.append("dm300_"); break;
                                case 3: descName.append("dwarfking_"); break;
                                case 4: default: descName.append("yogreal_"); break;
                            }
                            descName.append(BossDifficulty.getNameKey(difficultyCache[i]));
                            GameScene.show(new WndMessage(M.L(BossDifficulty.class, descName.toString())));
                            break;
                        }
                    }
                }
            };
            add(sp);
            Component content = sp.content();

            for(int i = 0; i<5; ++i){
                final int j = i;

                RenderedTextBlock display = PixelScene.renderTextBlock( 10);
                content.add(display);
                allText.add(display);

                RedButton lhs = new RedButton("<<<", 6){
                    @Override
                    protected void onClick() {
                        super.onClick();
                        switchIndex(true, j);
                        updateText(j);
                    }
                };
                content.add(lhs);
                lhsButtons.add(lhs);

                RedButton rhs = new RedButton(">>>", 6){
                    @Override
                    protected void onClick() {
                        super.onClick();
                        switchIndex(false, j);
                        updateText(j);
                    }
                };
                content.add(rhs);
                rhsButtons.add(rhs);

                updateText(i);
                display.maxWidth(WIDTH - 2 * BOX_SIZE);
                display.setPos(WIDTH/2f - display.width()/2f, BOX_SIZE /2f + pos - display.height()/2f);
                PixelScene.align(display);

                lhs.setRect(0, pos, BOX_SIZE, BOX_SIZE);
                rhs.setRect(WIDTH - BOX_SIZE, pos, lhs.width(), lhs.height());

                int floor = 5*i+5;
                lhs.enable(Statistics.deepestFloor < floor);
                rhs.enable(Statistics.deepestFloor < floor);

                pos += GAP + lhs.height();

                difficultyCache[i] = BossDifficulty.getDifficulty(i);
                updateText(i);
            }

            resize(WIDTH, Math.min(MAX_HEIGHT, (int) Math.floor(pos + rtb.bottom()) + GAP));

            content.setRect(0, 0, WIDTH, pos);
            sp.setRect(0, rtb.bottom() + GAP * 2, WIDTH, Math.min(MAX_HEIGHT, (int) Math.floor(pos)));

        }

        private void switchIndex(boolean left, int whichButton){
            int current = difficultyCache[whichButton];
            current += (left ? -1: 1);
            if(current > 4 || current < 0){
                current = left?4:0;
            }
            difficultyCache[whichButton] = current;
        }

        private void updateText(int whichButton){
            int difficulty = difficultyCache[whichButton];
            RenderedTextBlock display = allText.get(whichButton);
            display.text((whichButton*5+5) + ": " + BossDifficulty.getName(difficulty));
            switch (difficulty){
                case 1:
                    display.hardlight(0x70E070); break;

                case 2:
                    display.hardlight(0x7070F0); break;

                case 3:
                    display.hardlight(0xE85050); break;

                case 4:
                    display.hardlight(0xF173AC); break;

                case 0: default:
                    display.hardlight(0xCCCCCC);

            }

            display.maxWidth(WIDTH - 2 * BOX_SIZE);
            display.setPos(WIDTH/2f - display.width()/2f, display.top());
            PixelScene.align(display);
        }

        @Override
        public void onBackPressed() {
            Statistics.enhance_boss_settings = 0;
            for(int i = 0, len = allText.size(); i<len; ++i){
                Statistics.enhance_boss_settings += difficultyCache[i] << (4*i);
            }
            super.onBackPressed();
        }
    }
}
