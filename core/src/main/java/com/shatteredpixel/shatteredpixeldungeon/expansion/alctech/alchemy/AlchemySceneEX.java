package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.alchemy;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Belongings;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.Dart;
import com.shatteredpixel.shatteredpixeldungeon.journal.Journal;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.ExitButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.ItemSlot;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndInfoItem;
import com.watabou.gltextures.TextureCache;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.Camera;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.NoosaScript;
import com.watabou.noosa.NoosaScriptNoLighting;
import com.watabou.noosa.SkinnedBlock;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.ui.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AlchemySceneEX extends PixelScene {

    private static final ItemButton[] inputs = new ItemButton[3];
    private ItemSlot output;

    private Emitter smokeEmitter;
    private Emitter bubbleEmitter;

    private Emitter lowerBubbles;
    private SkinnedBlock water;

    private RenderedTextBlock energyLeft;
    private RenderedTextBlock energyCost;

    private RedButton btnCombine;

    private static final int BTN_SIZE	= 28;

    @Override
    public void create() {
        super.create();

        water = new SkinnedBlock(
                Camera.main.width, Camera.main.height,
                Dungeon.level.waterTex() ){

            @Override
            protected NoosaScript script() {
                return NoosaScriptNoLighting.get();
            }

            @Override
            public void draw() {
                //water has no alpha component, this improves performance
                Blending.disable();
                super.draw();
                Blending.enable();
            }
        };
        water.autoAdjust = true;
        add(water);

        Image im = new Image(TextureCache.createGradient(0x66000000, 0x88000000, 0xAA000000, 0xCC000000, 0xFF000000));
        im.angle = 90;
        im.x = Camera.main.width;
        im.scale.x = Camera.main.height/5f;
        im.scale.y = Camera.main.width;
        add(im);

        RenderedTextBlock title = PixelScene.renderTextBlock( M.L(this, "title"), 9 );
        title.hardlight(Window.TITLE_COLOR);
        title.setPos(
                (Camera.main.width - title.width()) / 2f,
                (20 - title.height()) / 2f
        );
        align(title);
        add(title);

        int w = 50 + Camera.main.width/2;
        int left = (Camera.main.width - w)/2;

        int pos = (Camera.main.height - 100)/2;

        RenderedTextBlock desc = PixelScene.renderTextBlock(6);
        desc.maxWidth(w);
        desc.text( M.L(this, "text") );
        desc.setPos(left + (w - desc.width())/2, pos);
        add(desc);

        pos += desc.height() + 6;

        synchronized (inputs) {
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = new ItemButton() {
                    @Override
                    protected void onClick() {
                        super.onClick();
                        if (item != null) {
                            if (!(item instanceof AlchemistsToolkit)) {
                                if (!item.collect()) {
                                    Dungeon.level.drop(item, Dungeon.hero.pos);
                                }
                            }
                            item = null;
                            slot.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
                            updateState();
                        }
                        AlchemySceneEX.this.addToFront(WndBag.lastBag( itemSelector, WndBag.Mode.ALL, M.L(this, "title")));
                    }

                    @Override
                    protected boolean onLongClick() {
                        if (item != null){
                            Game.scene().addToFront(new WndInfoItem(item));
                            return true;
                        }
                        return false;
                    }
                };
                inputs[i].setRect(left + 10, pos, BTN_SIZE, BTN_SIZE);
                add(inputs[i]);
                pos += BTN_SIZE + 2;
            }
        }

        btnCombine = new RedButton(""){
            Image arrow;

            @Override
            protected void createChildren() {
                super.createChildren();

                arrow = Icons.get(Icons.ARROW);
                add(arrow);
            }

            @Override
            protected void layout() {
                super.layout();
                arrow.x = x + (width - arrow.width)/2f;
                arrow.y = y + (height - arrow.height)/2f;
                PixelScene.align(arrow);
            }

            @Override
            public void enable(boolean value) {
                super.enable(value);
                if (value){
                    arrow.tint(1, 1, 0, 1);
                    arrow.alpha(1f);
                    bg.alpha(1f);
                } else {
                    arrow.color(0, 0, 0);
                    arrow.alpha(0.6f);
                    bg.alpha(0.6f);
                }
            }

            @Override
            protected void onClick() {
                super.onClick();
                combine();
            }
        };
        btnCombine.enable(false);
        btnCombine.setRect(left + (w-30)/2f, inputs[1].top()+5, 30, inputs[1].height()-10);
        add(btnCombine);

        output = new ItemSlot(){
            @Override
            protected void onClick() {
                super.onClick();
                if (visible && item.trueName() != null){
                    AlchemySceneEX.this.addToFront(new WndInfoItem(item));
                }
            }
        };
        output.setRect(left + w - BTN_SIZE - 10, inputs[1].top(), BTN_SIZE, BTN_SIZE);

        ColorBlock outputBG = new ColorBlock(output.width(), output.height(), 0x9991938C);
        outputBG.x = output.left();
        outputBG.y = output.top();
        add(outputBG);

        add(output);
        output.visible = false;

        bubbleEmitter = new Emitter();
        smokeEmitter = new Emitter();
        bubbleEmitter.pos(0, 0, Camera.main.width, Camera.main.height);
        smokeEmitter.pos(outputBG.x + (BTN_SIZE-16)/2f, outputBG.y + (BTN_SIZE-16)/2f, 16, 16);
        bubbleEmitter.autoKill = false;
        smokeEmitter.autoKill = false;
        add(bubbleEmitter);
        add(smokeEmitter);

        pos += 10;

        lowerBubbles = new Emitter();
        lowerBubbles.pos(0, pos, Camera.main.width, Math.max(0, Camera.main.height-pos));
        add(lowerBubbles);
        lowerBubbles.pour(Speck.factory( Speck.BUBBLE ), 0.1f );

        ExitButton btnExit = new ExitButton(){
            @Override
            protected void onClick() {
                Game.switchScene(GameScene.class);
            }
        };
        btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
        add( btnExit );

        energyLeft = PixelScene.renderTextBlock(M.L(this, "energy", availableEnergy()), 9);
        energyLeft.setPos(
                (Camera.main.width - energyLeft.width())/2,
                Camera.main.height - 5 - energyLeft.height()
        );
        add(energyLeft);

        energyCost = PixelScene.renderTextBlock(6);
        add(energyCost);

        fadeIn();

        try {
            Dungeon.saveAll();
            Badges.saveGlobal();
            Journal.saveGlobal();
        } catch (IOException e) {
            ShatteredPixelDungeon.reportException(e);
        }
    }

    @Override
    public void update() {
        super.update();
        water.offset( 0, -5 * Game.elapsed );
    }

    @Override
    protected void onBackPressed() {
        Game.switchScene(GameScene.class);
    }

    protected WndBag.Listener itemSelector = new WndBag.Listener() {
        @Override
        public void onSelect( Item item ) {
            synchronized (inputs) {
                if (item != null && inputs[0] != null) {
                    for (ItemButton input : inputs) {
                        if (input.item == null) {
                            if (item instanceof Dart) {
                                input.item(item.detachAll(Dungeon.hero.belongings.backpack));
                            } else if (item instanceof AlchemistsToolkit) {
                                clearSlots();
                                input.item(item);
                            } else {
                                input.item(item.detach(Dungeon.hero.belongings.backpack));
                            }
                            break;
                        }
                    }
                    updateState();
                }
            }
        }
    };

    private<T extends Item> ArrayList<T> filterInput(Class<? extends T> itemClass){
        ArrayList<T> filtered = new ArrayList<>();
        for (ItemButton input : inputs) {
            Item item = input.item;
            if (itemClass.isInstance(item)) {
                filtered.add((T) item);
            }
        }
        return filtered;
    }

    private void updateState(){

        ArrayList<Item> ingredients = filterInput(Item.class);
        CustomRecipe recipe = RecipeLibrary.findRecipe(ingredients);

        if (recipe != null){
            int cost = recipe.cost(ingredients);

            output.item(recipe.sampleOutput(ingredients));
            output.visible = true;

            energyCost.text( M.L(this, "cost", cost) );
            energyCost.setPos(
                    btnCombine.left() + (btnCombine.width() - energyCost.width())/2,
                    btnCombine.top() - energyCost.height()
            );

            energyCost.visible = (cost > 0);

            if (cost <= availableEnergy()) {
                btnCombine.enable(true);
                energyCost.resetColor();
            } else {
                btnCombine.enable(false);
                energyCost.hardlight(0xFF0000);
            }

        } else {
            btnCombine.enable(false);
            output.visible = false;
            energyCost.visible = false;
        }

    }

    private void combine(){

        ArrayList<Item> ingredients = filterInput(Item.class);
        CustomRecipe recipe = RecipeLibrary.findRecipe(ingredients);

        Item result = null;

        if (recipe != null){
            provider.spendEnergy(recipe.cost(ingredients));
            energyLeft.text(M.L(this, "energy", availableEnergy()));
            energyLeft.setPos(
                    (Camera.main.width - energyLeft.width())/2,
                    Camera.main.height - 5 - energyLeft.height()
            );

            result = recipe.brew(ingredients);
        }

        if (result != null){
            bubbleEmitter.start(Speck.factory( Speck.BUBBLE ), 0.01f, 100 );
            smokeEmitter.burst(Speck.factory( Speck.WOOL ), 10 );
            Sample.INSTANCE.play( Assets.Sounds.PUFF );

            output.item(result);

            try {
                Dungeon.saveAll();
            } catch (IOException e) {
                ShatteredPixelDungeon.reportException(e);
            }

            synchronized (inputs) {
                for (ItemButton input : inputs) {
                    if (input != null && input.item != null) {
                        if (input.item.quantity() <= 0 || input.item instanceof AlchemistsToolkit) {
                            input.slot.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
                            input.item = null;
                        } else {
                            input.slot.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));

                        }
                    }
                }
            }

            if(!result.collect()){
                Dungeon.level.drop(result, Dungeon.hero.pos);
            }

            btnCombine.enable(false);
        }

    }

    public void populate(ArrayList<Item> toFind, Belongings inventory){
        clearSlots();

        int curslot = 0;
        for (Item finding : toFind){
            int needed = finding.quantity();
            ArrayList<Item> found = inventory.getAllSimilar(finding);
            while (!found.isEmpty() && needed > 0){
                Item detached;
                if (finding instanceof Dart) {
                    detached = found.get(0).detachAll(inventory.backpack);
                } else {
                    detached = found.get(0).detach(inventory.backpack);
                }
                inputs[curslot].item(detached);
                curslot++;
                needed -= detached.quantity();
                if (detached == found.get(0)) {
                    found.remove(0);
                }
            }
        }
        updateState();
    }

    @Override
    public void destroy() {
        synchronized ( inputs ) {
            clearSlots();
            Arrays.fill(inputs, null);
        }

        try {
            Dungeon.saveAll();
            Badges.saveGlobal();
            Journal.saveGlobal();
        } catch (IOException e) {
            ShatteredPixelDungeon.reportException(e);
        }
        super.destroy();
    }

    public void clearSlots(){
        synchronized ( inputs ) {
            for (ItemButton input : inputs) {
                if (input != null && input.item != null) {
                    if (!(input.item instanceof AlchemistsToolkit)) {
                        if (!input.item.collect()) {
                            Dungeon.level.drop(input.item, Dungeon.hero.pos);
                        }
                    }
                    input.item(null);
                    input.slot.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
                }
            }
        }
    }

    public static class ItemButton extends Component {

        protected NinePatch bg;
        protected ItemSlot slot;

        public Item item = null;

        @Override
        protected void createChildren() {
            super.createChildren();

            bg = Chrome.get( Chrome.Type.RED_BUTTON);
            add( bg );

            slot = new ItemSlot() {
                @Override
                protected void onPointerDown() {
                    bg.brightness( 1.2f );
                    Sample.INSTANCE.play( Assets.Sounds.CLICK );
                }
                @Override
                protected void onPointerUp() {
                    bg.resetColor();
                }
                @Override
                protected void onClick() {
                    ItemButton.this.onClick();
                }

                @Override
                protected boolean onLongClick() {
                    return ItemButton.this.onLongClick();
                }
            };
            slot.enable(true);
            add( slot );
        }

        protected void onClick() {}
        protected boolean onLongClick() {
            return false;
        }

        @Override
        protected void layout() {
            super.layout();

            bg.x = x;
            bg.y = y;
            bg.size( width, height );

            slot.setRect( x + 2, y + 2, width - 4, height - 4 );
        }

        public void item( Item item ) {
            slot.item( this.item = item );
        }
    }

    private static AlchemyExpansionProvider provider;

    public static void setProvider( AlchemyExpansionProvider p ){
        provider = p;
    }

    public static int availableEnergy(){
        return provider == null ? 0 : provider.getEnergy();
    }

    public static boolean providerIsToolkit(){
        return provider instanceof AlchemistsToolkit.kitEnergy;
    }

    public interface AlchemyExpansionProvider {

        int getEnergy();

        void spendEnergy(int reduction);

    }
}
