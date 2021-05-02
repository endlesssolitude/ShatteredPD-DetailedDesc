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
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.FilterUtil;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PotionHealingEX extends EnhancedPotion{
    {
        image = ItemSpriteSheet.POTION_CRIMSON;
    }

    @Override
    public void apply(Hero hero) {

    }

    @Override
    protected void setCategory() {

    }

    @Override
    public void shatter(int cell) {

    }

    protected void p1(Hero h){
        cure(h);
        heal(h, h.HT, 1f, 0);
    }

    protected void p2(Hero h){
        cure(h);
        heal(h, h.HT * 2, 3f, 3);
        Buff.affect(h, Barrier.class).setShield(h.HT/2 + 8);
    }

    protected void p3(Hero h){
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

    public static void heal( Char ch, int total, float percent, int flat){
        if (ch == Dungeon.hero && Dungeon.isChallenged(Challenges.NO_HEALING)){
            pharmacophobiaProc(Dungeon.hero);
        } else {
            //starts out healing 30 hp, equalizes with hero health total at level 11
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

    }

    protected void n2(int cell){

    }

    protected void n3(int cell){

    }

}
