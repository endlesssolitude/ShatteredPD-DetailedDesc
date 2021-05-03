package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enhancedPotion;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Drowsy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.FilterUtil;
import com.shatteredpixel.shatteredpixeldungeon.custom.visuals.CellColorBlock;
import com.shatteredpixel.shatteredpixeldungeon.custom.visuals.HaloQuick;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.utils.PathFinder;

public class PotionHealingEX extends EnhancedPotion{
    {
        image = ItemSpriteSheet.POTION_CRIMSON;
    }

    @Override
    public void apply(Hero hero) {
        if(enhanceLevel == 1){
            p1(hero);
        }else if(enhanceLevel == 2){
            p2(hero);
        }else if(enhanceLevel == 3){
            p3d(hero);
        }else{
            new PotionOfHealing().apply(hero);
        }
    }

    @Override
    protected void setCategory() {
        if(enhanceLevel<0){
            drinkOrThrow = THROW_ONLY;
        }else if(drinkOrThrow == 1 || drinkOrThrow == 2){
            drinkOrThrow = DRINK_PREFER;
        }else{
            drinkOrThrow = NEUTRAL;
        }
    }

    @Override
    public void shatter(int cell) {
        if(enhanceLevel == -1){
            n1(cell);
        }else if(enhanceLevel == -2){
            n2(cell);
        }else if(enhanceLevel == -3){
            n3(cell);
        }else if(enhanceLevel == 3){
            p3t(cell);
        }else{
            new PotionOfHealing().shatter(cell);
            return;
        }
        super.shatter(cell);
    }

    protected void p1(Hero h){
        cure(h);
        heal(h, h.HT, 1f, 0);
    }

    protected void p2(Hero h){
        cure(h);
        heal(h, h.HT * 2, 0.3f, 1);
        Buff.affect(h, Barrier.class).setShield(h.HT/2 + 8);
    }

    protected void p3d(Hero h){
        cure(h);
        heal(h, h.HT*3, 0.05f, 1);
        Char[] suitable = FilterUtil.filter(Actor.chars(), (ch)->{
            return ch != h && ch.alignment == Char.Alignment.ALLY && h.fieldOfView[ch.pos];
        }).toArray(new Char[0]);
        for(Char ch: suitable){
            Buff.affect(ch, Barrier.class).setShield(ch.HT * 5 / 4);
            new Flare(6, 28).show(ch.sprite, 2f).color(0x36FF54, true);
        }
    }

    protected void p3t(int cell){
        for(Char ch: Actor.chars()){
            float dist = Dungeon.level.trueDistance(ch.pos, cell);
            if(dist < 4.5f){
                if(ch.properties().contains(Char.Property.DEMONIC) || ch.properties().contains(Char.Property.UNDEAD)){
                    float multiplier = ch.properties().contains(Char.Property.BOSS) ? 0.15f : (ch.properties().contains(Char.Property.MINIBOSS)?0.45f:1f);
                    ch.damage((int) ((ch.HT/2+ch.HP/2-1)*multiplier), this);
                    ch.sprite.emitter().burst(ShadowParticle.UP, (int)(multiplier*10+3));
                }else {
                    heal(ch, (int) (ch.HT * 2/(2+dist)), 0.25f, 1);
                }
            }
            Dungeon.hero.sprite.parent.add(new HaloQuick(4.5f* DungeonTilemap.SIZE, 0x00FF00, 1f, 0.5f)
                    .point(DungeonTilemap.tileCenterToWorld(cell).x, DungeonTilemap.tileCenterToWorld(cell).y));
        }
    }

    public static void heal( Char ch, int total, float percent, int flat){
        if (ch == Dungeon.hero && Dungeon.isChallenged(Challenges.NO_HEALING)){
            pharmacophobiaProc(Dungeon.hero);
        } else {
            Buff.affect(ch, Healing.class).setHeal(total, percent, flat);
        }
    }

    public static void pharmacophobiaProc( Hero hero ){
        // harms the hero for ~40% of their max HP in poison
        Buff.affect( hero, Poison.class).set(4 + hero.lvl/2);
    }

    public static void cure( Char ch ) {
        Buff.detach( ch, Poison.class );
        Buff.detach( ch, Cripple.class );
        Buff.detach( ch, Weakness.class );
        Buff.detach( ch, Vulnerable.class );
        Buff.detach( ch, Bleeding.class );
        Buff.detach( ch, Blindness.class );
        Buff.detach( ch, Drowsy.class );
        Buff.detach( ch, Slow.class );
        Buff.detach( ch, Vertigo.class);
    }

    protected void n1(int cell){
        for(int i: PathFinder.NEIGHBOURS9){
            Char ch = Actor.findChar(i+cell);
            if(ch != null){
                Buff.affect(ch, Poison.class).extend(4 + Dungeon.depth);
            }
            Dungeon.hero.sprite.parent.add(new CellColorBlock(i+cell, 0.6f, 0.1f, 0.15f, 0x80FF00FF, false));
        }
    }

    protected void n2(int cell){
        Char ch = Actor.findChar(cell);
        if(ch != null){
            float multiplier = ch.properties().contains(Char.Property.BOSS) ? 0.2f : ch.properties().contains(Char.Property.MINIBOSS) ? 0.6f : 1f;
            if(!(ch instanceof Hero)){
                ch.damage((int) (ch.HT * multiplier), this);
            }else{
                ch.damage(ch.HP - 1, this);
                if(!ch.isAlive()){
                    Dungeon.fail(getClass());
                }
            }
            ch.sprite.emitter().burst(ShadowParticle.UP, (int) (multiplier * 20 + 5));
        }
        Dungeon.hero.sprite.parent.add(new CellColorBlock(cell, 0.6f, 0.2f, 0.15f, 0x80FF0000, false));
    }

    protected void n3(int cell){
        for(int i: PathFinder.NEIGHBOURS9){
            n2(i+cell);
        }
    }

}
