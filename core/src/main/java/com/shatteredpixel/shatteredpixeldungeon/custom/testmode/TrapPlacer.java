package com.shatteredpixel.shatteredpixeldungeon.custom.testmode;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.AlarmTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.BlazingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.BurningTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ChillingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ConfusionTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.CorrosionTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.CursingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.DisarmingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.DisintegrationTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.DistortionTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ExplosiveTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.FlashingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.FlockTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.FrostTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.GrimTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.GrippingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.GuardianTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.OozeTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.PitfallTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.PoisonDartTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.RockfallTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ShockingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.StormTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.SummoningTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.TeleportationTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ToxicTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.Trap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.WarpingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.WeakeningTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.WornDartTrap;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.CheckBox;
import com.shatteredpixel.shatteredpixeldungeon.ui.IconButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class TrapPlacer extends TestItem {
    {
        image = ItemSpriteSheet.RECLAIM_TRAP;
        defaultAction = AC_PLACE;
    }

    private static final String AC_PLACE = "place";
    private static final String AC_SET = "set";
    //used for trap selection.
    //Currently there are 7 rows and 8 cols of trap sprites
    //and none shares the same sprite.
    //the 9th column are inactive traps.
    private int row = 0;
    private int column = 0;
    protected Trap trap = null;
    private boolean triggerWhenPut = false;
    //encoding for traps, column = color, row = shape;
    /*
    //trap colors
    public static final int RED     = 0;
    public static final int ORANGE  = 1;
    public static final int YELLOW  = 2;
    public static final int GREEN   = 3;
    public static final int TEAL    = 4;
    public static final int VIOLET  = 5;
    public static final int WHITE   = 6;
    public static final int GREY    = 7;
    public static final int BLACK   = 8;

    //trap shapes
    public static final int DOTS        = 0;
    public static final int WAVES       = 1;
    public static final int GRILL       = 2;
    public static final int STARS       = 3;
    public static final int DIAMOND     = 4;
    public static final int CROSSHAIR   = 5;
    public static final int LARGE_DOT   = 6;
     */

    //encoding of all traps(tengu trap not included)
    protected void indexToTrap(int row, int column) {
        if (column == 0) switch (row) {
            case 0:
                nt(new AlarmTrap());
                break;
            case 6:
                nt(new DisarmingTrap());
                break;
            case 3:
                nt(new GuardianTrap());
                break;
            case 4:
                nt(new PitfallTrap());
                break;
            default:
                nt(null);
                break;
        }
        else if (column == 1) switch (row) {
            case 3:
                nt(new BlazingTrap());
                break;
            case 0:
                nt(new BurningTrap());
                break;
            case 4:
                nt(new ExplosiveTrap());
                break;
            default:
                nt(null);
                break;
        }
        else if (column == 2) switch (row) {
            case 0:
                nt(new ShockingTrap());
                break;
            case 3:
                nt(new StormTrap());
                break;
            default:
                nt(null);
                break;
        }
        else if (column == 3) switch (row) {
            case 0:
                nt(new OozeTrap());
                break;
            case 5:
                nt(new PoisonDartTrap());
                break;
            case 2:
                nt(new ToxicTrap());
                break;
            case 1:
                nt(new WeakeningTrap());
                break;
            default:
                nt(null);
                break;
        }
        else if (column == 4) switch (row) {
            case 2:
                nt(new ConfusionTrap());
                break;
            case 6:
                nt(new DistortionTrap());
                break;
            case 1:
                nt(new SummoningTrap());
                break;
            case 0:
                nt(new TeleportationTrap());
                break;
            case 3:
                nt(new WarpingTrap());
                break;
            default:
                nt(null);
                break;
        }
        else if (column == 5) switch (row) {
            case 1:
                nt(new CursingTrap());
                break;
            case 5:
                nt(new DisintegrationTrap());
                break;
            default:
                nt(null);
                break;
        }
        else if (column == 6) switch (row) {
            case 0:
                nt(new ChillingTrap());
                break;
            case 1:
                nt(new FlockTrap());
                break;
            case 3:
                nt(new FrostTrap());
                break;
            default:
                nt(null);
                break;
        }
        else if (column == 7) switch (row) {
            case 2:
                nt(new CorrosionTrap());
                break;
            case 3:
                nt(new FlashingTrap());
                break;
            case 6:
                nt(new GrimTrap());
                break;
            case 0:
                nt(new GrippingTrap());
                break;
            case 4:
                nt(new RockfallTrap());
                break;
            case 5:
                nt(new WornDartTrap());
                break;
            default:
                nt(null);
                break;
        }
        else nt(null);
    }

    //abbr for newTrap()
    protected void nt(Trap t) {
        trap = t;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_PLACE);
        actions.add(AC_SET);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_PLACE)) {
            if (trap == null) {
                GLog.w(M.L(this, "null_trap"));
                return;
            }
            GameScene.selectCell(new CellSelector.Listener() {
                @Override
                public void onSelect(final Integer cell) {
                    if (cell != null) {
                        ((TrapPlacer) curItem).setTrap(cell);
                    }
                    curUser.next();
                }
                @Override
                public String prompt() {
                    return M.L(TrapPlacer.class, "prompt");
                }
            });

        } else if (action.equals(AC_SET)) {
            GameScene.show(new SettingsWindow());
        }
    }

    public void setTrap(Integer cell) {
        if (trap == null) {
            GLog.w(M.L(this, "null_trap"));
            return;
        }

        if (!canPlaceTrap(cell)) {
            {
                GLog.w(M.L(this, "invalid_tile"));
                return;
            }
        }

        Trap trapToCreate = trap;
        //create a new instance for trap
        indexToTrap(row, column);
        Dungeon.level.setTrap(trapToCreate.reveal(), cell);
        //logic for deciding if triggering trap
        //Dungeon.level.map[cell] = Terrain.TRAP;
        Level.set(cell, Terrain.TRAP, Dungeon.level);
        //avoid new traps
        //Dungeon.level.avoid[cell] = true;
        //Dungeon.level.passable[cell] = false;

        if(triggerWhenPut) trapToCreate.trigger();
    }

    private boolean canPlaceTrap(int cell) {

        int x = Dungeon.level.map[cell];

        if (x == Terrain.EMPTY || x == Terrain.GRASS || x == Terrain.DOOR || x == Terrain.OPEN_DOOR || x == Terrain.EMBERS || x == Terrain.PEDESTAL || x == Terrain.EMPTY_SP ||
                x == Terrain.HIGH_GRASS || x == Terrain.FURROWED_GRASS || x == Terrain.TRAP || x == Terrain.INACTIVE_TRAP || x == Terrain.EMPTY_DECO || x == Terrain.WATER) {
            if (Dungeon.level.heroFOV[cell]) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put("row", row);
        bundle.put("column", column);
        bundle.put("trigger", triggerWhenPut);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        row = bundle.getInt("row");
        column = bundle.getInt("column");
        triggerWhenPut = bundle.getBoolean("trigger");
        indexToTrap(row, column);
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", (trap == null ? Messages.get(TrapPlacer.class, "no_trap_selected") : Messages.get(trap.getClass(), "name")));
    }

    private class SettingsWindow extends Window{
        private static final int WIDTH = 120;
        private static final int HEIGHT = 144;

        private static final int BTN_SIZE = 14;

        private static final int ROWS = 7;
        private static final int COLS = 8;

        private ArrayList<IconButton> trapButtons = new ArrayList<>();
        private RenderedTextBlock selected;

        public SettingsWindow(){
            super();

            resize(WIDTH, HEIGHT);

            RenderedTextBlock ttl = PixelScene.renderTextBlock(M.L(SettingsWindow.class, "title"), 9);
            PixelScene.align(ttl);
            add(ttl);
            ttl.setPos(1, 1);
            ttl.hardlight(0x44A8E4);

            float left = (WIDTH - BTN_SIZE*COLS)/2f;
            float top = ttl.bottom() + 4f;
            for(int i=0; i<ROWS; ++i){
                for(int j=0;j<COLS;++j){
                    final int index = i*COLS + j;
                    IconButton btn = new IconButton(){
                        @Override
                        public void onClick(){
                            trapButtons.get(row*COLS+column).icon().resetColor();
                            row = index / COLS;
                            column = index % COLS;
                            trapButtons.get(index).icon().color(0xFFFF44);
                            updateText();
                        }
                    };
                    btn.icon(new Image(Assets.Environment.TERRAIN_FEATURES, 16*j, 16*i, 16, 16));
                    btn.setRect(left + BTN_SIZE * j, top + BTN_SIZE * i, BTN_SIZE, BTN_SIZE);
                    add(btn);
                    trapButtons.add(btn);
                }
            }
            trapButtons.get(row * COLS + column).icon().color(0xFFFF44);

            selected = PixelScene.renderTextBlock("", 6);
            PixelScene.align(selected);
            add(selected);

            CheckBox cb = new CheckBox(M.L(TrapPlacer.class, "trigger")){
                @Override
                protected void onClick(){
                    super.onClick();
                    triggerWhenPut = checked();
                }
            };
            cb.checked(triggerWhenPut);
            cb.setRect(1, top + 7 * BTN_SIZE + 4, WIDTH/2f, 16);
            add(cb);

            selected.setPos(WIDTH/2f + 6, top + 7 * BTN_SIZE + 4 + (cb.height()-7f)/2f);

            updateText();

            resize(WIDTH, (int) (cb.bottom() + 2));
        }

        private void updateText(){
            indexToTrap(row, column);
            selected.text(statDesc());
        }

        private String statDesc() {
            if(trap == null) return M.L(this, "null_trap");
            return M.L(trap.getClass(), "name");
        }
    }
}