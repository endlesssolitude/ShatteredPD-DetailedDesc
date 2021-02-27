package com.shatteredpixel.shatteredpixeldungeon.custom.challenges.challengeboss;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Degrade;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Sleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Bee;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Eye;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.RipperDemon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.YogFist;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.TargetedCell;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PurpleParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.LarvaSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.YogSprite;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class YogHard extends Boss{
    {
        spriteClass= YogSprite.class;
        state=HUNTING;
        initProperty();
        initBaseStatus(1, 2, 1, 0, 1200, 0, 0);
        initStatus(180);
        properties.add(Property.IMMOVABLE);
        properties.add(Property.DEMONIC);
        viewDistance = 12;
    }

    private int phase = 0;

    private float abilityCooldown;
    private static final int MIN_ABILITY_CD = 10;
    private static final int MAX_ABILITY_CD = 15;

    private float summonCooldown;
    private static final int MIN_SUMMON_CD = 10;
    private static final int MAX_SUMMON_CD = 15;

    private ArrayList<Class> fistSummons = new ArrayList<>();

    {
        Random.pushGenerator(Dungeon.seedCurDepth());

        fistSummons.add(YogFist.BurningFist.class);
        fistSummons.add(YogFist.SoiledFist.class);
        fistSummons.add(YogFist.RottingFist.class);
        fistSummons.add(YogFist.RustedFist.class);
        fistSummons.add(YogFist.BrightFist.class);
        fistSummons.add(YogFist.DarkFist.class);

        Random.shuffle(fistSummons);

        Random.popGenerator();
    }

    private static final int SUMMON_DECK_SIZE = 4;
    private ArrayList<Class> regularSummons = new ArrayList<>();
    {
        for (int i = 0; i < SUMMON_DECK_SIZE; i++){
            if (i >= Statistics.spawnersAlive){
                regularSummons.add(Larva.class);
            } else {
                regularSummons.add(YogRipper.class);
            }
        }
        Random.shuffle(regularSummons);
    }

    private ArrayList<Integer> targetedCells = new ArrayList<>();

    @Override
    protected boolean act() {
        //char logic
        if (fieldOfView == null || fieldOfView.length != Dungeon.level.length()){
            fieldOfView = new boolean[Dungeon.level.length()];
        }
        Dungeon.level.updateFieldOfView( this, fieldOfView );

        throwItems();

        //mob logic
        enemy = chooseEnemy();

        enemySeen = enemy != null && enemy.isAlive() && fieldOfView[enemy.pos] && enemy.invisible <= 0;
        //end of char/mob logic

        if (phase == 0){
            if (Dungeon.hero.viewDistance >= Dungeon.level.distance(pos, Dungeon.hero.pos)) {
                Dungeon.observe();
            }
            if (Dungeon.level.heroFOV[pos]) {
                notice();
            }
        }

        if (phase == 4 && findFist() == null){
            yell(Messages.get(this, "hope"));
            summonCooldown = -20; //summon a burst of minions!
            phase = 5;
            setBackBeam(5, 5, 3, 2);
        }

        if (phase == 0){
            spend(TICK);
            return true;
        } else {

            boolean terrainAffected = false;
            HashSet<Char> affected = new HashSet<>();
            //delay fire on a rooted hero
            if (!Dungeon.hero.rooted) {
                for (int i : targetedCells) {
                    Ballistica b = new Ballistica(pos, i, Ballistica.WONT_STOP);
                    //shoot beams
                    sprite.parent.add(new Beam.DeathRay(sprite.center(), DungeonTilemap.raisedTileCenterToWorld(b.collisionPos)));
                    for (int p : b.path) {
                        Char ch = Actor.findChar(p);
                        if (ch != null && (ch.alignment != alignment || ch instanceof Bee)) {
                            affected.add(ch);
                        }
                        if (Dungeon.level.flamable[p]) {
                            Dungeon.level.destroy(p);
                            GameScene.updateMap(p);
                            terrainAffected = true;
                        }
                    }
                }
                if (terrainAffected) {
                    Dungeon.observe();
                }
                for (Char ch : affected) {
                    ch.damage(Random.NormalIntRange(20, 30), new Eye.DeathGaze());

                    if (Dungeon.level.heroFOV[pos]) {
                        ch.sprite.flash();
                        CellEmitter.center(pos).burst(PurpleParticle.BURST, Random.IntRange(1, 2));
                    }
                    if (!ch.isAlive() && ch == Dungeon.hero) {
                        Dungeon.fail(getClass());
                        GLog.n(Messages.get(Char.class, "kill", name()));
                    }
                }
                targetedCells.clear();
            }

            if (abilityCooldown <= 0){

                int beams = 1;
                HashSet<Integer> affectedCells = new HashSet<>();
                for (int i = 0; i < beams; i++){

                    int targetPos = Dungeon.hero.pos;
                    if (i != 0){
                        do {
                            targetPos = Dungeon.hero.pos + PathFinder.NEIGHBOURS8[Random.Int(8)];
                        } while (Dungeon.level.trueDistance(pos, Dungeon.hero.pos)
                                > Dungeon.level.trueDistance(pos, targetPos));
                    }
                    targetedCells.add(targetPos);
                    Ballistica b = new Ballistica(pos, targetPos, Ballistica.WONT_STOP);
                    affectedCells.addAll(b.path);
                }

                //remove one beam if multiple shots would cause every cell next to the hero to be targeted
                boolean allAdjTargeted = true;
                for (int i : PathFinder.NEIGHBOURS9){
                    if (!affectedCells.contains(Dungeon.hero.pos + i) && Dungeon.level.passable[Dungeon.hero.pos + i]){
                        allAdjTargeted = false;
                        break;
                    }
                }
                if (allAdjTargeted){
                    targetedCells.remove(targetedCells.size()-1);
                }
                for (int i : targetedCells){
                    Ballistica b = new Ballistica(pos, i, Ballistica.WONT_STOP);
                    for (int p : b.path){
                        sprite.parent.add(new TargetedCell(p, 0xFF0000));
                        affectedCells.add(p);
                    }
                }

                //don't want to overly punish players with slow move or attack speed
                spend(GameMath.gate(TICK, Dungeon.hero.cooldown(), 3*TICK));
                Dungeon.hero.interrupt();

                abilityCooldown += Random.NormalFloat(MIN_ABILITY_CD, MAX_ABILITY_CD);
                abilityCooldown -= (phase - 1);

            } else {
                spend(TICK);
            }

            while (summonCooldown <= 0){

                Class<?extends Mob> cls = regularSummons.remove(0);
                Mob summon = Reflection.newInstance(cls);
                regularSummons.add(cls);

                int spawnPos = -1;
                for (int i : PathFinder.NEIGHBOURS8){
                    if (Actor.findChar(pos+i) == null){
                        if (spawnPos == -1 || Dungeon.level.trueDistance(Dungeon.hero.pos, spawnPos) > Dungeon.level.trueDistance(Dungeon.hero.pos, pos+i)){
                            spawnPos = pos + i;
                        }
                    }
                }

                if (spawnPos != -1) {
                    summon.pos = spawnPos;
                    GameScene.add( summon );
                    Actor.addDelayed( new Pushing( summon, pos, summon.pos ), -1 );
                    summon.beckon(Dungeon.hero.pos);

                    summonCooldown += Random.NormalFloat(MIN_SUMMON_CD, MAX_SUMMON_CD);
                    summonCooldown -= (phase - 1);
                    if (findFist() != null){
                        summonCooldown += MIN_SUMMON_CD - (phase - 1);
                    }
                } else {
                    break;
                }
            }

        }

        if (summonCooldown > 0) summonCooldown-=1f;
        if (abilityCooldown > 0) abilityCooldown-=1f;

        //extra fast abilities and summons at the final 100 HP
        if (phase == 5 && abilityCooldown > 2){
            abilityCooldown = 2;
        }
        if (phase == 5 && summonCooldown > 3){
            summonCooldown = 3;
        }

        return true;
    }

    @Override
    public boolean isAlive() {
        return super.isAlive() || phase != 5;
    }

    @Override
    public boolean isInvulnerable(Class effect) {
        return phase == 0 || findFist() != null;
    }

    private void setBackBeam(int dx, int dy, int vx, int vy){
        YogBackgroundBeam b = buff(YogBackgroundBeam.class);
        if(b!=null){
            b.setDensity(dx, dy, vx, vy);
        }else{
            Buff.affect(this, YogBackgroundBeam.class).setDensity(dx, dy, vx, vy);
        }
    }

    @Override
    public void damage( int dmg, Object src ) {

        int preHP = HP;
        super.damage( dmg, src );

        if (phase == 0 || findFist() != null) return;

        if (phase < 4) {
            HP = Math.max(HP, HT - 350 * phase);
        } else if (phase == 4) {
            HP = Math.max(HP, 150);
        }
        int dmgTaken = preHP - HP;

        if (dmgTaken > 0) {
            abilityCooldown -= dmgTaken / 6f;
            summonCooldown -= dmgTaken / 6f;
        }

        if (phase < 4 && HP <= HT - 350*phase){

            Dungeon.level.viewDistance = Math.max(1, Dungeon.level.viewDistance-1);
            if (Dungeon.hero.buff(Light.class) == null){
                Dungeon.hero.viewDistance = Dungeon.level.viewDistance;
            }
            Dungeon.observe();
            GLog.n(Messages.get(this, "darkness"));
            sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "invulnerable"));

            YogFist fist = (YogFist) Reflection.newInstance(fistSummons.remove(0));
            fist.pos = Dungeon.level.exit;

            CellEmitter.get(Dungeon.level.exit-1).burst(ShadowParticle.UP, 25);
            CellEmitter.get(Dungeon.level.exit).burst(ShadowParticle.UP, 100);
            CellEmitter.get(Dungeon.level.exit+1).burst(ShadowParticle.UP, 25);

            if (abilityCooldown < 5) abilityCooldown = 5;
            if (summonCooldown < 5) summonCooldown = 5;

            int targetPos = Dungeon.level.exit + Dungeon.level.width();
            if (Actor.findChar(targetPos) == null){
                fist.pos = targetPos;
            } else if (Actor.findChar(targetPos-1) == null){
                fist.pos = targetPos-1;
            } else if (Actor.findChar(targetPos+1) == null){
                fist.pos = targetPos+1;
            }

            //1, 2, 3 FISTS !!!
            GameScene.add(fist, 4);
            Actor.addDelayed( new Pushing( fist, Dungeon.level.exit, fist.pos ), -1 );

            if(phase>1) {
                YogFist fist_1 = (YogFist) Reflection.newInstance(fistSummons.remove(0));
                fist_1.pos = this.pos + 1;
                GameScene.add(fist_1, 4);
            }

            if(phase>2) {
                YogFist fist_2 = (YogFist) Reflection.newInstance(fistSummons.remove(0));
                fist_2.pos = this.pos - 1;
                GameScene.add(fist_2, 4);
            }

            phase++;

            if(phase == 2){
                setBackBeam(9, 9, 1, 1);
            }else if(phase == 3){
                setBackBeam(9, 7, 2, 1);
            }else if(phase == 4){
                setBackBeam(7, 7, 3, 2);
            }
        }

        LockedFloor lock = Dungeon.hero.buff(LockedFloor.class);
        if (lock != null) lock.addTime(dmgTaken);

    }

    private YogFist findFist(){
        for ( Char c : Actor.chars() ){
            if (c instanceof YogFist){
                return (YogFist) c;
            }
        }
        return null;
    }

    @Override
    public void beckon( int cell ) {
    }

    @Override
    public void aggro(Char ch) {
        for (Mob mob : (Iterable<Mob>)Dungeon.level.mobs.clone()) {
            if (Dungeon.level.distance(pos, mob.pos) <= 8 &&
                    (mob instanceof Larva || mob instanceof RipperDemon)) {
                mob.aggro(ch);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void die( Object cause ) {

        for (Mob mob : (Iterable<Mob>)Dungeon.level.mobs.clone()) {
            if (mob instanceof Larva || mob instanceof RipperDemon) {
                mob.die( cause );
            }
        }

        Dungeon.level.viewDistance = 4;
        if (Dungeon.hero.buff(Light.class) == null){
            Dungeon.hero.viewDistance = Dungeon.level.viewDistance;
        }

        GameScene.bossSlain();
        Dungeon.level.unseal();
        super.die( cause );

        yell( Messages.get(this, "defeated") );
    }

    @Override
    public void notice() {
        if (!BossHealthBar.isAssigned()) {
            BossHealthBar.assignBoss(this);
            yell(Messages.get(this, "notice"));
            for (Char ch : Actor.chars()){
                if (ch instanceof DriedRose.GhostHero){
                    ((DriedRose.GhostHero) ch).sayBoss();
                }
            }
            if (phase == 0) {
                phase = 1;
                summonCooldown = Random.NormalFloat(MIN_SUMMON_CD, MAX_SUMMON_CD);
                abilityCooldown = Random.NormalFloat(MIN_ABILITY_CD, MAX_ABILITY_CD);
            }
        }
    }

    @Override
    public String description() {
        String desc = super.description();

        if (Statistics.spawnersAlive > 0){
            desc += "\n\n" + Messages.get(this, "desc_spawners");
        }

        return desc;
    }

    {
        immunities.add( Terror.class );
        immunities.add( Amok.class );
        immunities.add( Charm.class );
        immunities.add( Sleep.class );
        immunities.add( Vertigo.class );
        immunities.add( Frost.class );
        immunities.add( Paralysis.class );
    }

    private static final String PHASE = "phase";

    private static final String ABILITY_CD = "ability_cd";
    private static final String SUMMON_CD = "summon_cd";

    private static final String FIST_SUMMONS = "fist_summons";
    private static final String REGULAR_SUMMONS = "regular_summons";

    private static final String TARGETED_CELLS = "targeted_cells";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(PHASE, phase);

        bundle.put(ABILITY_CD, abilityCooldown);
        bundle.put(SUMMON_CD, summonCooldown);

        bundle.put(FIST_SUMMONS, fistSummons.toArray(new Class[0]));
        bundle.put(REGULAR_SUMMONS, regularSummons.toArray(new Class[0]));

        int[] bundleArr = new int[targetedCells.size()];
        for (int i = 0; i < targetedCells.size(); i++){
            bundleArr[i] = targetedCells.get(i);
        }
        bundle.put(TARGETED_CELLS, bundleArr);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        phase = bundle.getInt(PHASE);
        if (phase != 0) BossHealthBar.assignBoss(this);

        abilityCooldown = bundle.getFloat(ABILITY_CD);
        summonCooldown = bundle.getFloat(SUMMON_CD);

        fistSummons.clear();
        Collections.addAll(fistSummons, bundle.getClassArray(FIST_SUMMONS));
        regularSummons.clear();
        Collections.addAll(regularSummons, bundle.getClassArray(REGULAR_SUMMONS));

        for (int i : bundle.getIntArray(TARGETED_CELLS)){
            targetedCells.add(i);
        }
    }

    public static class Larva extends Mob {

        {
            spriteClass = LarvaSprite.class;

            HP = HT = 25;
            defenseSkill = 12;
            viewDistance = Light.DISTANCE;

            EXP = 5;
            maxLvl = -2;

            properties.add(Property.DEMONIC);

        }

        @Override
        public int attackSkill( Char target ) {
            return 30;
        }

        @Override
        public int damageRoll(){
            return Random.NormalIntRange(15, 25);
        }

        @Override
        public int drRoll() {
            return Random.NormalIntRange(0, 4);
        }

        @Override
        public void damage(int dmg, Object src){
            if(Dungeon.level.distance(pos, Dungeon.level.exit + Dungeon.level.width()*3)<=4){
                dmg = dmg/10;
            }
            super.damage(dmg, src);
        }

    }

    //used so death to yog's ripper demons have their own rankings description and are more aggro
    public static class YogRipper extends RipperDemon {
    }

    public static class YogBackgroundBeam extends BackgroundBeamCounter{
        @Override
        protected void beamHit(Ballistica beam){

            for (int p : beam.path) {
                if (Dungeon.level.flamable[p]) {
                    Dungeon.level.destroy( p );
                    GameScene.updateMap( p );
                }
                Char ch = findChar(p);
                if (ch != null) {
                    if (ch.alignment != Char.Alignment.ENEMY) {
                        Buff.affect(ch, Degrade.class, 4f);
                        ch.damage(Random.IntRange(10, 13), YogHard.class);
                        if (ch == Dungeon.hero && !ch.isAlive()) {
                            Dungeon.fail(getClass());
                        }
                    }
                }
            }
        }
    }

}
