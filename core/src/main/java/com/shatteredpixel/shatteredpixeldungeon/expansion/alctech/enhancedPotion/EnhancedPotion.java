package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enhancedPotion;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndUseItem;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

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
    protected static final int DRINK = DRINK_ONLY | THROW_PREFER;

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
        setCategory(level);
        setAction();
    }

    protected void setCategory(int level){
        //do nothing byDefault
        //drinkOrThrow = something
    }

    @Override
    public String status(){
        StringBuilder sb = new StringBuilder(enhanceIdentifier());
        sb.append(" ");
        sb.append(quantity > 1? String.valueOf(quantity):"");
        return sb.toString();
    }

    @Override
    public boolean isSimilar(Item item){
        if(item instanceof EnhancedPotion){
            return enhanceLevel == ((EnhancedPotion)item).enhanceLevel;
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

        if (drinkOrThrow == THROW_PREFER) {

            GameScene.show(
                    new WndOptions( Messages.get(Potion.class, "beneficial"),
                            Messages.get(Potion.class, "sure_throw"),
                            Messages.get(Potion.class, "yes"), Messages.get(Potion.class, "no") ) {
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
        return 75 * quantity;
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
        setCategory(enhanceLevel);
        setAction();
    }

    @Override
    public String name(){
        return M.L(this, "name");
    }

    @Override
    public String desc(){
        return M.L(this, "desc");
    }



}
