package com.shatteredpixel.shatteredpixeldungeon.custom.ch.boss.goo;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.custom.ch.boss.BossNew;
import com.shatteredpixel.shatteredpixeldungeon.custom.ch.boss.DR;
import com.shatteredpixel.shatteredpixeldungeon.custom.ch.boss.Timer;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.RangeMap;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.DriedRose;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.GoldenKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.GooBlob;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GooSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Iterator;

public class HardGoo extends BossNew {
    {
        whichBoss = 0;
        spriteClass = GooSprite.class;
    }

    private static final int map_left_boundary = 5;
    private static final int map_right_boundary = 31;

    private float waterSpeed;
    private float halfHealthModifier;
    private float allWaterModifier;
    private float pumpModifier;

    private boolean forcePumpAttack;
    private int maxPumpRange;
    private boolean pumpDestroy;
    private boolean pumpDebuff;
    //aoe causes 60% damage to other chars in range
    private boolean pumpAttackAOE;

    private float pumpChanceA;
    private float pumpChanceB;

    private boolean canPassiveSummon;
    private float passiveSummonCd;
    private boolean canDamageSummon;
    private int damageSummonThreshold;

    private boolean firstChangeWater;

    private int waterRegeneration;
    private int extraRegeneration;

    private boolean extraKeyDrop;

    @Override
    public void readData() {
        this.baseHT = DR.getInt(HardGoo.class, "base_ht");
        this.baseAcc = DR.getInt(HardGoo.class, "base_acc");
        this.baseEva = DR.getInt(HardGoo.class, "base_eva");
        this.baseMin = DR.getInt(HardGoo.class, "base_min_atk");
        this.baseMax = DR.getInt(HardGoo.class, "base_max_atk");
        this.baseMinDef = DR.getInt(HardGoo.class, "base_min_def");
        this.baseMaxDef = DR.getInt(HardGoo.class, "base_max_def");
        this.baseSpeed = DR.getFloat(HardGoo.class, "base_speed");
        this.EXP = DR.getInt(HardGoo.class, "exp");
        waterSpeed = DR.getFloat(HardGoo.class, "water_speed");
        halfHealthModifier = DR.getFloat(HardGoo.class, "half_health_modifier");
        allWaterModifier = DR.getFloat(HardGoo.class, "all_water_modifier");
        pumpModifier = DR.getFloat(HardGoo.class, "pump_modifier");
        forcePumpAttack = DR.getBool(HardGoo.class, "force_pump_attack");
        maxPumpRange = DR.getInt(HardGoo.class, "max_pump_range");
        pumpDestroy = DR.getBool(HardGoo.class, "pump_destroy");
        pumpDebuff = DR.getBool(HardGoo.class, "pump_debuff");
        pumpAttackAOE = DR.getBool(HardGoo.class, "pump_attack_aoe");
        pumpChanceA = DR.getFloat(HardGoo.class, "pump_chance_a");
        pumpChanceB = DR.getFloat(HardGoo.class, "pump_chance_b");
        canPassiveSummon = DR.getBool(HardGoo.class, "can_passive_summon");
        passiveSummonCd = DR.getFloat(HardGoo.class, "passive_summon_cd");
        canDamageSummon = DR.getBool(HardGoo.class, "can_damage_summon");
        damageSummonThreshold = DR.getInt(HardGoo.class, "damage_summon_threshold");
        firstChangeWater = DR.getBool(HardGoo.class, "first_change_water");
        waterRegeneration = DR.getInt(HardGoo.class, "water_regeneration");
        extraRegeneration = DR.getInt(HardGoo.class, "extra_regeneration");
        extraKeyDrop = DR.getBool(HardGoo.class, "extra_key_drop");
    }

    private boolean isPumpAttack = false;

    @Override
    protected void initProperty() {
        super.initProperty();
        properties.add(Property.DEMONIC);
        properties.add(Property.ACIDIC);
    }

    private int pumpedUp = 0;

    private int surroundingWater(){
        int water = 0;
        for(int n: PathFinder.NEIGHBOURS8){
            if(Dungeon.level.water[n+pos]){
                water++;
            }
        }
        return water;
    }

    @Override
    public int damageRoll() {
        int damage = Math.round(super.damageRoll() * (pumpedUp>0?pumpModifier:1f) * (surroundingWater()>7?.9f* allWaterModifier :1f) * (berserk()?.9f*halfHealthModifier:1f));
        if(pumpedUp>0) pumpedUp=0;
        return damage;
    }

    @Override
    public int attackSkill( Char target ) {
        return Math.round(super.attackSkill(target)*(pumpedUp>0?100f*pumpModifier:1f) * (surroundingWater()>7?2f* allWaterModifier :1f));
    }

    @Override
    public int defenseSkill(Char enemy) {
        return (int)(super.defenseSkill(enemy) * (surroundingWater()>7? allWaterModifier :1f));
    }

    @Override
    public float speed(){
        return super.speed()*(Dungeon.level.water[pos]?waterSpeed:1f)*(surroundingWater()>7? allWaterModifier :1f);
    }

    private boolean berserk(){ return HP*2<HT; }

    @Override
    public boolean act() {
        if(firstChangeWater){
            Dungeon.level.setCellToWater(true, pos);
        }

        if(state != SLEEPING) {
            if (canPassiveSummon) {
                Buff t = buff(Timer.class);
                if (t == null) {
                    Buff.affect(this, Timer.class, berserk() ? passiveSummonCd / halfHealthModifier : passiveSummonCd);
                    summonMiniGoo();
                }
            }
        }

        if (Dungeon.level.water[pos] && HP < HT) {

            int heal = 0;
            heal += waterRegeneration;
            if(surroundingWater() > 7){
                heal += extraRegeneration;
            }
            HP = Math.min(HT, HP + heal);

            if (HP*2 >= HT) {
                BossHealthBar.bleed(false);
                ((GooSprite) sprite).spray(false);
            }

            sprite.emitter().burst( Speck.factory( Speck.HEALING ), Math.min(heal, 6) );
        }

        if(forcePumpAttack && pumpedUp>0 && paralysed<=0){
            enemy = chooseEnemy();
            return doAttack(enemy);
        }

        Dungeon.level.setCellToWater(true, pos);

        return super.act();
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        if(pumpedUp > 0){
            return	distance( enemy ) <= maxPumpRange
                && new Ballistica( pos, enemy.pos, Ballistica.STOP_TARGET | Ballistica.STOP_SOLID | Ballistica.IGNORE_SOFT_SOLID).collisionPos == enemy.pos
                    && new Ballistica( enemy.pos, pos, Ballistica.STOP_TARGET | Ballistica.STOP_SOLID | Ballistica.IGNORE_SOFT_SOLID).collisionPos == pos;
        }
        return super.canAttack(enemy);
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        damage = super.attackProc( enemy, damage );

        if (isPumpAttack) {
            Camera.main.shake( 3, 0.3f );

            if(pumpDebuff) {
                Buff.affect(enemy, Vulnerable.class, 10f);
                Buff.affect(enemy, Blindness.class, 5f);
            }
            isPumpAttack = false;
        }

        return damage;
    }

    private void summonMiniGoo(){
        ArrayList<Integer> candidates = new ArrayList<>();
        boolean[] solid = Dungeon.level.solid;

        for (int n : RangeMap.centeredRect(pos, 1, 1)) {
            if (!solid[n] && Actor.findChar(n) == null) {
                candidates.add(n);
            }
        }

        if (candidates.size() > 0) {
            MiniGoo mini = new MiniGoo();
            Buff.affect(mini, Timer.class, berserk()? 5f:7f);
            mini.pos = Random.element( candidates );
            mini.state = mini.HUNTING;

            Dungeon.level.occupyCell(mini);

            GameScene.add( mini , 0f );
            Actor.addDelayed( new Pushing( mini, pos, mini.pos ), -1 );
        }
    }

    @Override
    public void updateSpriteState() {
        super.updateSpriteState();

        if (pumpedUp > 0){
            ((GooSprite)sprite).pumpUp( pumpedUp );
        }
    }

    @Override
    protected boolean doAttack( Char enemy ) {
        //if ready to pump up...
        if(pumpedUp == 0 && Random.Float() < (berserk()? pumpChanceB : pumpChanceA)){
            ++pumpedUp;
            ((GooSprite)sprite).pumpUp( pumpedUp);
            if (Dungeon.level.heroFOV[pos]) {
                sprite.showStatus( CharSprite.NEGATIVE, Messages.get(this, "!!!") );
                GLog.n( Messages.get(this, "pumpup") );
                Sample.INSTANCE.play( Assets.Sounds.CHARGEUP, 1f, 0.8f );
            }
            spend( attackDelay() );
            return true;
            //if pumping up...
        }else if(pumpedUp > 0){
            //preparing
            if(pumpedUp < maxPumpRange){
                ++pumpedUp;
                ((GooSprite)sprite).pumpUp( pumpedUp);
                Sample.INSTANCE.play( Assets.Sounds.CHARGEUP );
                spend( attackDelay() );
                return true;

                //attack!
            }else{
                //complex because attack may be forced and has null enemy to attack.
                boolean visible = Dungeon.level.heroFOV[pos];
                boolean cancelled = true;
                //can't attack unseen enemy, even the attack is forced!
                boolean enemyInFOV = enemy != null && enemy.isAlive() && fieldOfView[enemy.pos] && enemy.invisible <= 0;
                //if can attack
                if(enemyInFOV && canAttack(enemy)) {
                    isPumpAttack = true;
                    if (visible) {
                        ((GooSprite) sprite).pumpAttack();
                    } else {
                        ((GooSprite)sprite).triggerEmitters();
                        attack(enemy);
                    }
                    cancelled = false;
                }
                //else cancel attack
                if(cancelled){
                    pumpedUp = 0;
                    ((GooSprite)sprite).clearEmitters();
                }
                //spend even attack is canceled because we have other on-attack effects
                spend( attackDelay() );
                //put aoe attack right after the animation
                //or it would be very odd to see allies attacked before animation.
                if(pumpAttackAOE){
                    final HardGoo attacker = this;
                    Actor.addDelayed(new Actor() {
                        {
                            actPriority = VFX_PRIO;
                        }
                        @Override
                        protected boolean act() {
                            Actor.remove(this);
                            //REMEMBER: WHEN A SET/LIST MIGHT DELETE ITEMS IN THEIR LOOP, USE ITERATOR!!!
                            //NEVER USE: ENHANCED FOR
                            //in this case, the aoe is powerful enough to kill multiple chars
                            //and it might cause unexpected problems
                            //Yes it actually happens! How can you imagine the hero get killed when wearing Immortal Shield?
                            Iterator<Char> iter = Actor.chars().iterator();
                            while(iter.hasNext()){
                                Char ch = iter.next();
                                if(ch.alignment != Alignment.ENEMY && ch != enemy && attacker.canAttack(ch)){
                                    int damage = (int) (attacker.damageRoll() * 0.45f * attacker.pumpModifier);
                                    damage = ch.defenseProc(attacker, damage);
                                    damage -= ch.drRoll();
                                    damage = attacker.attackProc(ch, damage);
                                    ch.damage(damage, this);
                                    if(ch == Dungeon.hero && !ch.isAlive()){
                                        Dungeon.fail(getClass());
                                        GLog.n(M.L(HardGoo.class, "aoe_damage"));
                                    }
                                }
                            }
                            return true;
                        }
                    }, -1);
                }
                //and so does level destroy.
                if(pumpDestroy){
                    final int p = this.pos;
                    Actor.addDelayed(new Actor() {
                        {
                            actPriority = VFX_PRIO;
                        }
                        @Override
                        protected boolean act() {
                            Actor.remove(this);
                            int[] area = RangeMap.manhattanRing(p, 0, maxPumpRange);
                            int w; int h;
                            for(int i: area){
                                w = i%Dungeon.level.width();
                                h = i/Dungeon.level.width();
                                if(w>map_left_boundary && w<map_right_boundary
                                        && h>map_left_boundary && h<map_right_boundary){
                                    //the outside ring is powerless to destroy wall. Or this ability would be too op.
                                    if(Dungeon.level.solid[i] && RangeMap.manhattanDist(i, p)<maxPumpRange) {
                                        Level.set(i, Terrain.EMPTY);
                                    }
                                    if(RangeMap.manhattanDist(i, p)<=maxPumpRange){
                                        Dungeon.level.setCellToWater(true, i);
                                    }
                                }
                                CellEmitter.get(i).start(ElmoParticle.FACTORY, 0.04f, 8);
                            }
                            return true;
                        }
                    }, -1);
                }
                //we regard cancel as complete because no animation is needed to play.
                return !visible || cancelled;
            }
            //normal attack.
        }else{
            return super.doAttack(enemy);
        }
    }

    @Override
    public boolean attack( Char enemy, float dmgMulti, float dmgBonus, float accMulti ) {
        boolean result = super.attack(  enemy, dmgMulti, dmgBonus, accMulti );
        pumpedUp = 0;
        return result;
    }

    @Override
    protected boolean getCloser( int target ) {
        if (pumpedUp != 0) {
            pumpedUp = 0;
            sprite.idle();
        }
        return super.getCloser( target );
    }

    @Override
    public void damage(int dmg, Object src) {
        if (!BossHealthBar.isAssigned()){
            BossHealthBar.assignBoss( this );
        }
        if(pumpedUp > 0){
            dmg /= 2;
        }
        if(dmg > HT/10){
            dmg = HT/10 + (dmg-HT/10)/10 + 1;
        }
        if(canDamageSummon && dmg > damageSummonThreshold) summonMiniGoo();
        boolean bleeding = (HP*2 <= HT);
        super.damage(dmg, src);
        if ((HP*2 <= HT) && !bleeding){
            BossHealthBar.bleed(true);
            sprite.showStatus(CharSprite.NEGATIVE, Messages.get(this, "enraged"));
            ((GooSprite)sprite).spray(true);
            yell(Messages.get(this, "gluuurp"));
        }
        LockedFloor lock = Dungeon.hero.buff(LockedFloor.class);
        if (lock != null) lock.addTime(dmg*2);
    }

    @Override
    public void die( Object cause ) {

        super.die( cause );

        Dungeon.level.unseal();

        GameScene.bossSlain();
        Dungeon.level.drop( new IronKey( Dungeon.depth ).quantity(2), pos ).sprite.drop();
        if(extraKeyDrop) {
            Dungeon.level.drop(new GoldenKey(Dungeon.depth), pos).sprite.drop();
        }

        int blobs = Random.chances(new float[]{5, 4, 3, 2, 1}) + 3;
        for (int i = 0; i < blobs; i++){
            int ofs;
            do {
                ofs = PathFinder.NEIGHBOURS8[Random.Int(8)];
            } while (!Dungeon.level.passable[pos + ofs]);
            Dungeon.level.drop( new GooBlob(), pos + ofs ).sprite.drop();
        }

        Badges.validateBossSlain();

        yell( Messages.get(this, "defeated") );
    }

    @Override
    public void notice() {
        super.notice();
        if (!BossHealthBar.isAssigned()) {
            BossHealthBar.assignBoss(this);
            yell(Messages.get(this, "notice"));
            for (Char ch : Actor.chars()){
                if (ch instanceof DriedRose.GhostHero){
                    ((DriedRose.GhostHero) ch).sayBoss();
                }
            }
        }
    }

    private final String PUMPEDUP = "pumpedup";

    @Override
    public void storeInBundle( Bundle bundle ) {

        super.storeInBundle( bundle );

        bundle.put( PUMPEDUP , pumpedUp );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {

        super.restoreFromBundle( bundle );

        pumpedUp = bundle.getInt( PUMPEDUP );
        if (state != SLEEPING) BossHealthBar.assignBoss(this);
        if ((HP*2 <= HT)) BossHealthBar.bleed(true);
    }


}
