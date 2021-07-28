package com.shatteredpixel.shatteredpixeldungeon.custom.ch.boss;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Grim;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public abstract class BossNew extends Mob {

    public static int whichBoss = 0;

    protected int baseMin;
    protected int baseMax;
    protected int baseAcc;
    protected int baseEva;
    protected int baseHT;
    protected int baseMinDef;
    protected int baseMaxDef;

    protected void initProperty() {
        properties.add(Property.BOSS);
        immunities.add(Grim.class);
        immunities.add(ScrollOfPsionicBlast.class);
        immunities.add(ScrollOfRetribution.class);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( baseMin, baseMax );
    }

    @Override
    public int attackSkill( Char target ) {
        return Math.round(baseAcc);
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(baseMinDef, baseMaxDef);
    }

    public BossDifficulty difficulty = BossDifficulty.EASY;

    public void setDifficulty(BossDifficulty difficulty){
        this.difficulty = difficulty;
    }

    public void initBoss(BossDifficulty difficulty, boolean firstCreate){
        setDifficulty(difficulty);
        DR.readFile(BossDifficulty.fullPath(whichBoss));
        readData();
        DR.clear();
        initProperty();
        this.HT = baseHT;
        this.defenseSkill = baseEva;
        if(firstCreate){
            this.HP = baseHT;
        }
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put("difficulty", this.difficulty);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        this.difficulty = bundle.getEnum("difficulty", BossDifficulty.class);
        initBoss(this.difficulty, false);
    }

    @Override
    public String name() {
        return super.name() + BossDifficulty.getName(this.difficulty.ordinal());
    }

    public abstract void readData();
}
