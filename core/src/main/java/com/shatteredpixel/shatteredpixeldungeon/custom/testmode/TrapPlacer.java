package com.shatteredpixel.shatteredpixeldungeon.custom.testmode;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
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
import com.shatteredpixel.shatteredpixeldungeon.ui.OptionSlider;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
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
                GLog.w(Messages.get(this, "null_trap"));
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
                    return Messages.get(TrapPlacer.class, "prompt");
                }
            });

        } else if (action.equals(AC_SET)) {
            GameScene.show(new DoubleAxisSelector());
        }
    }

    public void setTrap(Integer cell) {
        if (trap == null) {
            GLog.w(Messages.get(this, "null_trap"));
            return;
        }

        if (!canPlaceTrap(cell)) {
            {
                GLog.w(Messages.get(this, "invalid_tile"));
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

    protected class DoubleAxisSelector extends Window {
        RenderedTextBlock indexToTrap;
        OptionSlider opRow;
        OptionSlider opColumn;
        CheckBox cb;

        public DoubleAxisSelector() {
            super();
            width = 120;

            cb = new CheckBox(Messages.get(this, "trigger_when_put")){
                @Override
                protected void onClick() {
                    super.onClick();
                    triggerWhenPut = checked();
                }
            };
            cb.checked(triggerWhenPut);
            cb.setRect(0, 2, 120, 18);
            add(cb);

            //indexToTrap = new StyledButton(Chrome.Type.SCROLL, "");
            indexToTrap = PixelScene.renderTextBlock("", 6);
            indexToTrap.text(statDesc(row, column));
            indexToTrap.visible = true;
            indexToTrap.setPos(0, cb.bottom() + 2);
            add(indexToTrap);

            opColumn = new OptionSlider(Messages.get(this, "column_slider"), "0", "7", 0, 7) {
                @Override
                protected void onChange() {
                    column = getSelectedValue();
                    DoubleAxisSelector.this.updateText();
                }
            };
            opColumn.setRect(0, indexToTrap.top() + indexToTrap.height() + 2, 120, 26);
            opColumn.setSelectedValue(column);
            add(opColumn);

            opRow = new OptionSlider(Messages.get(this, "row_slider"), "0", "6", 0, 6) {
                @Override
                protected void onChange() {
                    row = getSelectedValue();
                    DoubleAxisSelector.this.updateText();
                }
            };
            opRow.setRect(0, opColumn.bottom() + 2, 120, 26);
            opRow.setSelectedValue(row);
            add(opRow);

            resize(120, (int) (opRow.bottom() + 2));
        }

        public void updateText() {
            indexToTrap(row, column);

            indexToTrap.text(statDesc(row, column));

            opColumn.setPos(0, indexToTrap.top() + indexToTrap.height() + 2);
            opRow.setPos(0, opColumn.bottom() + 2);
            resize(120, (int) (opRow.bottom() + 2));
        }

        private String statDesc(int row, int column) {
            String statDesc = Messages.get(this, "trap_index_pre", column, row, (trap == null ? Messages.get(TrapPlacer.class, "no_trap_selected") : Messages.get(trap.getClass(), "name")));
            String key = "trap_index_c" + String.valueOf(column);
            statDesc += Messages.get(this, key);
            return statDesc;
        }
    }
}