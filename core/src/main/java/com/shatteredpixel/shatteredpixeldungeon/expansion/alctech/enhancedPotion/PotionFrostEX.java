package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enhancedPotion;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blizzard;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.buffs.DefendBuff;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.Game;
import com.watabou.noosa.Halo;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class PotionFrostEX extends EnhancedPotion{
    {
        image = ItemSpriteSheet.POTION_AZURE;
    }

    @Override
    public void apply(Hero hero) {
        if(enhanceLevel==-1){
            n1(hero);
        }else if(enhanceLevel == -2){
            n2(hero);
        }else if(enhanceLevel == -3){
            n3(hero);
        }else {
            new PotionOfFrost().apply(hero);
        }
    }

    @Override
    public void shatter(int cell){
        super.shatter(cell);
        if(enhanceLevel == 1){
            p1(cell);
        }
        if(enhanceLevel == 2){
            p2(cell);
        }
        if(enhanceLevel == 3){
            p3(cell);
        }
        if(enhanceLevel<0){
            new PotionOfFrost().shatter(cell);
        }
    }

    @Override
    public void setCategory(){
        if(enhanceLevel > 0){
            drinkOrThrow = THROW_ONLY;
        }else{
            drinkOrThrow = DRINK_PREFER;
        }
    }

    protected void p1(int cell){
        for(int i: PathFinder.NEIGHBOURS9){
            Char ch = Actor.findChar(cell+i);
            if(ch != null){
                Buff.affect(ch, Frost.class, i==0?5000f:5f);
            }
        }
    }

    protected void p2(int cell){
        for(int i: PathFinder.NEIGHBOURS8){
            Char ch = Actor.findChar(cell+i);
            if(ch != null){
                Buff.affect(ch, Frost.class, 5f);
            }
        }

        Char ch = Actor.findChar(cell);
        if(ch != null){
            Buff.affect(ch, DiffusiveFrost.class, 8f);
        }
    }

    protected void p3(int cell){
        GameScene.flash(0x802090FF, true);
        for(Char ch: Actor.chars()){
            if(ch.alignment != Char.Alignment.ALLY ){
                Buff.affect(ch, DiffusiveFrost.class, Random.Float(12f, 22f));
            }
        }
    }

    protected void n1(Hero h){
        IceArmor ia = h.buff(IceArmor.class);
        if(ia!=null){
            ia.addHits(1);
        }else{
            Buff.affect(h, IceArmor.class).setHits(1);
        }
    }

    protected void n2(Hero h){
        IceArmor ia = h.buff(IceArmor.class);
        if(ia!=null){
            ia.addHits(3);
        }else{
            Buff.affect(h, IceArmor.class).setHits(3);
        }
    }

    protected void n3(Hero h){
        Buff.affect(h, BlizzardMastery.class).set(BlizzardMastery.DURATION);
    }


    public static class DiffusiveFrost extends Frost{
        {
            immunities.add(Frost.class);
        }
        private static final float radius = 4f;
        @Override
        public boolean attachTo( Char target ){
            if(target.buff(Frost.class)!=null  || target.buff(DiffusiveFrost.class)!=null){
                return false;
            }
            return super.attachTo(target);
        }
        @Override
        public void detach(){
            super.detach();
            if(target.sprite != null){
                target.sprite.parent.add(new Halo(radius* DungeonTilemap.SIZE, 0xB06097F4, 1f){
                    private float life = 1.5f;
                    @Override
                    public void update(){
                        if((life -= Game.elapsed) < 0f){
                            killAndErase();
                        }else{
                            am = life * (-0.66f);
                            aa = life * (+0.66f);
                        }
                        super.update();
                    }
                    @Override
                    public void draw() {
                        Blending.setLightMode();
                        super.draw();
                        Blending.setNormalMode();
                    }
                }.point(target.sprite.center().x, target.sprite.center().y));
            }
            for(Char ch: Actor.chars()){
                if(ch.alignment != Char.Alignment.ALLY && ch != target){
                    if(Dungeon.level.trueDistance(ch.pos, target.pos)<radius) {
                        if(ch.buff(Frost.class)==null &&ch.buff(DiffusiveFrost.class)==null) {
                            if(Random.Int(3)==0) {
                                Buff.affect(ch, Frost.class, Random.Float(7f, 12f));
                            }else{
                                Buff.affect(ch, DiffusiveFrost.class, Random.Float(7f, 12f));
                            }
                        }
                    }
                }
            }
        }
        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0f, 0f, 1f);
        }
    }

    public static class IceArmor extends DefendBuff {
        {
            announced = true;
            type = buffType.POSITIVE;
        }

        @Override
        public int icon(){
            return BuffIndicator.ARMOR;
        }

        @Override
        public void tintIcon(Image icon){
            icon.invert();
        }

        @Override
        protected int proc(Armor a, Char attacker, Char defender, int damage) {
            damage = 0;
            if(target.sprite != null){
                target.sprite.parent.add(new Halo(3f* DungeonTilemap.SIZE, 0xB06097F4, 1f){
                    private float life = 1.5f;
                    @Override
                    public void update(){
                        if((life -= Game.elapsed) < 0f){
                            killAndErase();
                        }else{
                            am = life * (-0.66f);
                            aa = life * (+0.66f);
                        }
                        super.update();
                    }
                    @Override
                    public void draw() {
                        Blending.setLightMode();
                        super.draw();
                        Blending.setNormalMode();
                    }
                }.point(target.sprite.center().x, target.sprite.center().y));
            }
            for(Char ch: Actor.chars()){
                if(ch.alignment != Char.Alignment.ALLY && ch != target){
                    if(Dungeon.level.trueDistance(ch.pos, target.pos)<3f) {
                        if(ch.buff(Frost.class)==null &&ch.buff(DiffusiveFrost.class)==null) {
                            Buff.affect(ch, Frost.class, Random.Float(5f, 9f));
                        }
                    }
                }
            }
            return damage;
        }
    }

    public static class BlizzardMastery extends FlavourBuff{

        {
            type = buffType.POSITIVE;
            announced = true;
        }

        public static final float DURATION	= 84f;

        protected float left;

        private static final String LEFT	= "left";

        @Override
        public void storeInBundle( Bundle bundle ) {
            super.storeInBundle( bundle );
            bundle.put( LEFT, left );
        }

        @Override
        public void restoreFromBundle( Bundle bundle ) {
            super.restoreFromBundle( bundle );
            left = bundle.getFloat( LEFT );
        }

        public void set( float duration ) {
            this.left = duration;
        }

        @Override
        public boolean act() {
            if(left > 4f) {
                GameScene.add(Blob.seed(target.pos, 60, Blizzard.class));
            }

            spend(TICK);
            left -= TICK;
            if (left <= 0){
                detach();
            }

            return true;
        }

        @Override
        public int icon() {
            return BuffIndicator.FROST;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0x95FFA2);
        }

        @Override
        public float iconFadePercent() {
            return Math.max(0, (DURATION - left) / DURATION);
        }

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", dispTurns(left));
        }

        {
            immunities.add( Blizzard.class );
            immunities.add( Chill.class );
            immunities.add( Frost.class );
        }
    }
}
