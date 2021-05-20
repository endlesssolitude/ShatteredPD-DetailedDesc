package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enhancedPotion;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.expansion.alchemy.CustomRecipe;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.Runestone;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndUseItem;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//what to override:
//setCategory(decides if potion should drink/throw depending on the level)
//apply: the effect of drinking (throw effect default)
//shatter: throw effect
//Actually maintaining 6 levels of potion is problematic......
public class EnhancedPotion extends Item {
    protected static final float TIME_TO_DRINK = 1f;
    // NO another action
    protected static final int THROW_ONLY = 1;
    protected static final int DRINK_ONLY = 2;
    // Both actions are executable
    protected static final int NEUTRAL = 4;
    // Prefer one action, warn if do another;
    protected static final int THROW_PREFER = 8;
    protected static final int DRINK_PREFER = 16;

    protected static final int THROW = THROW_ONLY | THROW_PREFER;
    protected static final int DRINK = DRINK_ONLY | DRINK_PREFER;

    protected static final String AC_DRINK = "drink";
    protected static final String AC_CHOOSE = "choose";

    protected int drinkOrThrow;
    protected int enhanceLevel;

    {
        stackable = true;
        defaultAction = AC_DRINK;
        drinkOrThrow = NEUTRAL;
        unique = true;
        bones = false;
        enhanceLevel = 1;
    }

    public String enhanceIdentifier(){
        if(enhanceLevel==0){
            return "";
        }
        if(enhanceLevel==1){
            return "+";
        }
        if(enhanceLevel==2){
            return "++";
        }
        if(enhanceLevel==-1){
            return "-";
        }
        if(enhanceLevel==-2){
            return "--";
        }
        if(enhanceLevel>=3){
            return "+++";
        }
        if(enhanceLevel<=-3){
            return "---";
        }
        return "MISSING";
    }

    public int identifierColor(){
        return enhanceLevel>=0?0xFFFFFF:0xFF0000;
    }

    @Override
    public boolean isUpgradable(){return false;}

    @Override
    public boolean isIdentified(){return true;}

    @Override
    public boolean collect( Bag container ) {
        if (super.collect( container )){
            setAction();
            return true;
        } else {
            return false;
        }
    }

    public void setLevel(int level){
        this.enhanceLevel = level;
        setCategory();
        setAction();
    }

    protected void setCategory(){
        //do nothing byDefault
        //drinkOrThrow = something
        //be sure to contain all cases
        drinkOrThrow = NEUTRAL;
    }

    @Override
    public String status(){
        return enhanceIdentifier() + (quantity > 1 ? ":"+quantity : "");
    }

    @Override
    public boolean isSimilar(Item item){
        if(this.getClass() == item.getClass()) {
            return enhanceLevel == ((EnhancedPotion) item).enhanceLevel;
        }
        return false;
    }

    public void setAction(){
        if(drinkOrThrow == NEUTRAL){
            defaultAction = AC_CHOOSE;
        }
        if((drinkOrThrow & DRINK)>0){
            defaultAction = AC_DRINK;
        }
        if((drinkOrThrow & THROW)>0){
            defaultAction = AC_THROW;
        }

    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        if(drinkOrThrow == DRINK_ONLY){
            actions.remove(AC_THROW);
            actions.add(AC_DRINK);
        }
        if(drinkOrThrow == DRINK_PREFER || drinkOrThrow == NEUTRAL || drinkOrThrow == THROW_PREFER){
            actions.add(AC_DRINK);
        }
        return actions;
    }

    @Override
    public void execute( final Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals( AC_CHOOSE )){

            GameScene.show(new WndUseItem(null, this) );

        } else if (action.equals( AC_DRINK )) {

            if (drinkOrThrow == THROW_PREFER) {

                GameScene.show(
                        new WndOptions( M.L(EnhancedPotion.class, "harmful"),
                                M.L(EnhancedPotion.class, "sure_drink"),
                                M.L(EnhancedPotion.class, "yes"), M.L(EnhancedPotion.class, "no") ) {
                            @Override
                            protected void onSelect(int index) {
                                if (index == 0) {
                                    drink( hero );
                                }
                            }
                        }
                );

            } else {
                drink( hero );
            }

        }
    }

    @Override
    public void doThrow( final Hero hero ) {

        if (drinkOrThrow == DRINK_PREFER) {

            GameScene.show(
                    new WndOptions( M.L(Potion.class, "beneficial"),
                            M.L(Potion.class, "sure_throw"),
                            M.L(Potion.class, "yes"), M.L(Potion.class, "no") ) {
                        @Override
                        protected void onSelect(int index) {
                            if (index == 0) {
                                EnhancedPotion.super.doThrow( hero );
                            }
                        }
                    }
            );

        } else {
            super.doThrow( hero );
        }
    }

    protected void drink( Hero hero ) {

        detach( hero.belongings.backpack );

        hero.spend( TIME_TO_DRINK );
        hero.busy();
        apply( hero );

        Sample.INSTANCE.play( Assets.Sounds.DRINK );

        hero.sprite.operate( hero.pos );
    }

    @Override
    protected void onThrow( int cell ) {
        if (Dungeon.level.map[cell] == Terrain.WELL || Dungeon.level.pit[cell]) {

            super.onThrow( cell );

        } else  {

            Dungeon.level.pressCell( cell );
            shatter( cell );

        }
    }

    public void apply( Hero hero ) {
        shatter( hero.pos );
    }

    public void shatter( int cell ) {
        if (Dungeon.level.heroFOV[cell]) {
            GLog.i(M.L(this, "shatter") );
            Sample.INSTANCE.play( Assets.Sounds.SHATTER );
            splash( cell );
        }
    }

    protected void splash( int cell ) {

        Fire fire = (Fire)Dungeon.level.blobs.get( Fire.class );
        if (fire != null)
            fire.clear( cell );

        final int color = splashColor();

        Char ch = Actor.findChar(cell);
        if (ch != null && ch.alignment == Char.Alignment.ALLY) {
            Buff.detach(ch, Burning.class);
            Buff.detach(ch, Ooze.class);
            Splash.at( ch.sprite.center(), color, 5 );
        } else {
            Splash.at( cell, color, 5 );
        }
    }

    protected int splashColor(){
        return ItemSprite.pick( image, 5, 9 );
    }

    @Override
    public int value() {
        return 30 * quantity * Math.abs(enhanceLevel);
    }

    @Override
    public void storeInBundle(Bundle b){
        super.storeInBundle(b);
        b.put("enhcancelevel", enhanceLevel);
    }

    @Override
    public void restoreFromBundle(Bundle b){
        super.restoreFromBundle(b);
        enhanceLevel = b.getInt("enhcancelevel");
        setCategory();
        setAction();
    }


    //+1, +2, +3: _1 2 3 -1 -2 -3: _11 12 13
    @Override
    public String name(){
        return M.L(this,  "name_" + (enhanceLevel > 0 ? enhanceLevel : (-enhanceLevel + 10)));
    }

    @Override
    public String desc(){
        return M.L(this, "desc_" + (enhanceLevel > 0 ? enhanceLevel : (-enhanceLevel + 10)));
    }


    public static class EnhancedPotionRecipe extends CustomRecipe{

        @Override
        public boolean testIngredients(ArrayList<Item> ingredients) {
            if(ingredients.size()!=3) return false;
            int count=0;
            boolean contain;
            for(Item it: ingredients){
                Class<? extends Item> itemClass = it.getClass();
                contain = false;
                for(Map.Entry<Class<? extends Potion>, Integer> entry : PBL.potionIdMap().entrySet()){
                    if(itemClass == entry.getKey()){
                        ++count;
                        contain = true;
                        break;
                    }
                }
                if(contain) {
                    continue;
                }
                for(Map.Entry<Class<? extends Plant.Seed>, Integer> entry : PBL.seedIdMap().entrySet()){
                    if(itemClass == entry.getKey()){
                        ++count;
                        contain = true;
                        break;
                    }
                }
            }

            return count==3;


        }

        @Override
        public int cost(ArrayList<Item> ingredients) {
            return 5;
        }

        @Override
        public Item brew(ArrayList<Item> ingredients) {
            if (!testIngredients(ingredients)) return null;

            for (Item ingredient : ingredients){
                ingredient.quantity(ingredient.quantity() - 1);
            }

            HashMap<Integer, Float> ingredientMap = new HashMap<>();

            for(Item it: ingredients){
                Class<? extends Item> itemClass = it.getClass();
                for(Map.Entry<Class<? extends Potion>, Integer> entry : PBL.potionIdMap().entrySet()){
                    if(itemClass == entry.getKey()){
                        if(!ingredientMap.containsKey(entry.getValue())){
                            ingredientMap.put(entry.getValue(), 1f);
                        }else{
                            float power = ingredientMap.get(entry.getValue());
                            ingredientMap.put(entry.getValue(), power+1f);
                        }
                        break;
                    }
                }
                for(Map.Entry<Class<? extends Plant.Seed>, Integer> entry : PBL.seedIdMap().entrySet()){
                    if(itemClass == entry.getKey()){
                        if(!ingredientMap.containsKey(entry.getValue())){
                            ingredientMap.put(entry.getValue(), 1/3f);
                        }else{
                            float power = ingredientMap.get(entry.getValue());
                            ingredientMap.put(entry.getValue(), 1/3f+power);
                        }
                        break;
                    }
                }
            }

            float total = 0;
            float abs = 0;
            for(Map.Entry<Integer, Float> entry: ingredientMap.entrySet()){
                total += entry.getValue()*entry.getValue();
                abs += Math.abs(entry.getValue());
            }
            total = (float) Math.sqrt(total);
            float chanceForRandom = 1.366f * (abs/total -1f);
            float maxPower = Float.MIN_VALUE;
            for(Map.Entry<Integer, Float> entry: ingredientMap.entrySet()){
                if(maxPower<entry.getValue()){
                    maxPower = entry.getValue();
                }
            }

            try{
                EnhancedPotion enhancedPotion;
                if(Random.Float()<chanceForRandom){
                    enhancedPotion = Reflection.newInstance(
                            PBL.enhancedLibMap().get(Random.chances(PBL.chanceMap())));
                }else {
                    enhancedPotion = Reflection.newInstance(
                            PBL.enhancedLibMap().get(Random.chances(ingredientMap)));
                }
                enhancedPotion.setLevel(brewLevel(Math.max(maxPower, 0)));
                return enhancedPotion;
            }catch (Exception e) {
                ShatteredPixelDungeon.reportException( e );
                return null;
            }
        }

        @Override
        public Item sampleOutput(ArrayList<Item> ingredients) {
            return new WndBag.Placeholder(ItemSpriteSheet.POTION_HOLDER){

                @Override
                public String name() {
                    return M.L(EnhancedPotion.class, "positive");
                }

                @Override
                public String info() {
                    return "";
                }
            };
        }


        private int brewLevel(float power){
            if(power<1f) return 1;
            if(power<2.33f) return Random.chances(new float[]{2.33f-power, power-1f})+1;
            if(power<3f) return Random.chances(new float[]{3f-power, power-2.33f})+2;
            return 3;
        }
    }

    public static class EnhancedPotionAlter extends CustomRecipe{

        @Override
        public boolean testIngredients(ArrayList<Item> ingredients) {
            if(ingredients.size() != 2) return false;
            boolean potion = false;
            boolean secondary = false;

            for (Item i : ingredients){
                if (i instanceof Plant.Seed || i instanceof Runestone){
                    secondary = true;
                } else if (PBL.enhancedLibMap().containsValue(i.getClass())) {
                    potion = true;
                }
            }

            return potion && secondary;
        }

        @Override
        public int cost(ArrayList<Item> ingredients) {
            return 1;
        }

        @Override
        public Item brew(ArrayList<Item> ingredients) {
            if(!testIngredients(ingredients)) return null;
            EnhancedPotion p = null;
            for(Item i: ingredients){
                if(i instanceof EnhancedPotion){
                    int enl = ((EnhancedPotion) i).enhanceLevel;
                    ((EnhancedPotion) i).setLevel(enl *-1);
                    p = (EnhancedPotion) i;
                }else{
                    i.quantity(i.quantity()-1);
                }
            }
            return p;
        }

        @Override
        public Item sampleOutput(ArrayList<Item> ingredients) {
            return new WndBag.Placeholder(ItemSpriteSheet.POTION_HOLDER){

                @Override
                public String name() {
                    return M.L(EnhancedPotion.class, "corrupted");
                }

                @Override
                public String info() {
                    return "";
                }
            };
        }
    }

}