package com.shatteredpixel.shatteredpixeldungeon.custom.challenges;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blizzard;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.CorrosiveGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.SmokeScreen;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.StormCloud;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArcaneArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.BlobImmunity;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Degrade;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Drowsy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FrostImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Levitation;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Regeneration;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SoulMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ToxicImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.AlarmTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.BlazingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.CursingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.FlockTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.GuardianTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.PoisonDartTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.StormTrap;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.TargetHealthIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

import static com.shatteredpixel.shatteredpixeldungeon.items.Item.updateQuickslot;

public class UnpredictableFuture extends Buff {

    private float cooldown = 75;
    private int positive = 0;
    // used to avoid extreme fortune or misfortune.
    // it shows how lucky the target was and modify the chance of positive and negative incidents.
    // neutral incidents are not affected.
    // the higher this value is, the less positive incidents and the more negative incidents would happen, and vice versa.
    private float forgetFactor = 0.92f;
    private float predict = 10f;
    //forget the incidents long ago.
    private boolean warn = false;
    private static final String COOL_DOWN = "cool_down";
    private static final String POSITIVE = "positive";
    {
        type = buffType.NEUTRAL;
        announced = true;
    }

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( COOL_DOWN, cooldown);
        bundle.put(POSITIVE, positive);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        cooldown = bundle.getFloat( COOL_DOWN );
        positive = bundle.getInt(POSITIVE);
    }

    @Override
    public boolean act(){
        spend(TICK);
        cooldown -= 1f;
        if(!warn && cooldown<predict){
            warn = true;
            GLog.w(Messages.get(this,"coming", cooldown));
        }

        if(cooldown<0){
            positive = Math.round(positive*forgetFactor);
            cooldown += Random.Float(40, 100) + 5 - Dungeon.depth; // 75 average, -1 per level
            warn = false;
            int tries = 1+ Random.Int(Dungeon.depth/7 + 1);
            for(int i=0;i<tries;++i){
                generateIncidents();
            }
        }
        return true;
    }

    @Override
    public int icon(){ return BuffIndicator.COMBO; }

    @Override
    public float iconFadePercent() {
        if(cooldown < predict){
            return  1f - cooldown/predict;
        }
        return 0f;
    }

    @Override
    public String desc(){
        if(cooldown<predict){
            return Messages.get(this, "desc_warn", cooldown, positive);
        }else{
            return Messages.get(this, "desc_normal", positive);
        }

    }

    @Override
    public String toString(){
        return Messages.get(this, "name");
    }




    private void generateIncidents(){
        int seed = Random.Int(100);
        if(seed<40){
            generateBuff();
        }else if(seed<53){
            generateBlob();
        }else if(seed<72){
            affectItem();
        }else if(seed<96){
            affectChars();
        }else{
            affectPositive();
        }
    }

    private void generateBuff(){
        int r = Random.chances(new float[]{185f - positive, 250f - positive, 260f + positive, 275f + positive});
        switch(r){
            case 1: geneNegativeEnemyBuff(); break;
            case 2: geneNegativeHeroBuff(); break;
            case 3: genePositiveEnemyBuff(); break;
            case 0: default: genePositiveHeroBuff(); break;
        }
    }

    //175 weight
    private void genePositiveHeroBuff(){
        int r = Random.Int(100);
        if(r<15){
            Buff.affect(target, ArcaneArmor.class).set(20, Random.Int(15,45) );
            GLog.p(Messages.get(this,"hero_arcanearmor"));
            positive += 20;
        }else if(r<28){
            Buff.affect(target, MagicImmune.class, Random.Float(10,30));
            GLog.p(Messages.get(this,"hero_magicimmune"));
            positive += 10;
        }else if(r<45){
            Buff.affect(target, Barkskin.class).set(target.HT/3, 3);
            GLog.p(Messages.get(this,"hero_barkskin"));
            positive += 15;
        }else if(r<60){
            Buff.affect(target, FrostImbue.class, (Random.Int(10) == 0? 1024f:50f));
            GLog.p(Messages.get(this,"hero_elementimbue"));
            positive += 10;
        }else if(r<77){
            Buff.affect(target, Invisibility.class, 20f);
            GLog.p(Messages.get(this,"hero_invisibility"));
            positive += 5;
        }else if(r<80){
            Buff.affect(target, Invisibility.class, 65535f);
            GLog.p(Messages.get(this,"hero_invisibilitylong"));
            positive += 35;
        }else if(r<100){
            Buff.affect(target, Haste.class, Random.Float(10,30));
            GLog.p(Messages.get(this,"hero_haste"));
            positive += 10;
        }
    }
    //250 weight
    private void geneNegativeEnemyBuff(){
        int r = Random.Int(100);
         if(r<15){
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if (Dungeon.level.heroFOV[mob.pos] && mob.alignment == Char.Alignment.ENEMY) {
                    Buff.affect(mob, Charm.class, Random.Float(20f,45f)).object = target.id();
                }
            }
            GLog.p(Messages.get(this,"mob_fov_charm"));
            positive += 10;
        }else if(r<29){
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if (Dungeon.level.heroFOV[mob.pos] && mob.alignment == Char.Alignment.ENEMY) {
                    Buff.prolong(mob, Cripple.class, (Random.Int(15)==0?65535f:20f));
                    Buff.prolong(mob, Blindness.class, (Random.Int(15)==0?65535f:20f));
                }
            }
            GLog.p(Messages.get(this,"mob_fov_blind"));
            positive += 10;
        }else if(r<44){
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if (Dungeon.level.heroFOV[mob.pos] && mob.alignment == Char.Alignment.ENEMY) {
                    if(!mob.isImmune(Corruption.class)){
                        Buff.affect(mob, Corruption.class);
                    }
                }
            }
            GLog.p(Messages.get(this,"mob_fov_corrupt"));
            positive += 16;
        }else if(r<56){
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if (mob.alignment == Char.Alignment.ENEMY) {
                    Buff.affect(mob, Vertigo.class, 30f);
                    Buff.affect(mob, Levitation.class, 30f);
                }
            }
            GLog.p(Messages.get(this,"mob_all_vertigo"));
            positive += 10;
        }else if(r<72){
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if (mob.alignment == Char.Alignment.ENEMY) {
                    Buff.affect(mob, Drowsy.class);
                }
            }
            GLog.p(Messages.get(this,"mob_all_sleep"));
            positive += 7;
        }else if(r<85){
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if (mob.alignment == Char.Alignment.ENEMY) {
                    Buff.affect(mob, Weakness.class,100f);
                    Buff.affect(mob, Hex.class, 100f);
                    Buff.affect(mob, Vulnerable.class, 100f);
                }
            }
            GLog.p(Messages.get(this,"mob_all_vunlerable"));
            positive += 20;
        }else if(r<100){
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if (mob.alignment == Char.Alignment.ENEMY) {
                    Buff.affect(mob, SoulMark.class,65535f);
                }
            }
            GLog.p(Messages.get(this,"mob_all_soulmark"));
            positive += 35;
        }
    }
    //275
    private void geneNegativeHeroBuff(){
        int r = Random.Int(100);
         if(r<39){
            for(Buff b: target.buffs()){
                if(b.type == Buff.buffType.POSITIVE && !(b instanceof Regeneration)){
                    b.detach();
                }
            }
            GLog.n(Messages.get(this,"hero_dispel_positive"));
            positive -= 9;
        }else if(r<53){
            Buff.affect(target, Blindness.class, 10f);
            Buff.affect(target, Cripple.class, 10f);
            //GLog.n(Messages.get(this,"hero_blind"));
            positive -= 16;
        }else if(r<65){
            Buff.affect(target, Weakness.class, 50f);
            Buff.affect(target, Hex.class, 50f);
            Buff.affect(target, Vulnerable.class, 50f);
            GLog.n(Messages.get(this,"hero_vunlerable"));
            positive -= 15;
        }else if(r<74){
            Buff.affect(target, Degrade.class, (Random.Int(8)==0?1024f:64f));
            GLog.n(Messages.get(this,"hero_degrade"));
            positive -= 18;
        }else if(r<93){
            Buff.affect(target, Roots.class, 10f);
            positive -= 10;
        }else if(r<100){
             if(Dungeon.depth > 10){
                 Buff.affect(target, Levitation.class, 8f);
                 Buff.affect(target, Burning.class).reignite(target,8f);
                 GLog.n(Messages.get(this,"hero_burning"));
             }
             positive -= 15;
        }
    }
    //300
    private void genePositiveEnemyBuff(){
        int r = Random.Int(100);
        if(r<12){
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if (mob.alignment == Char.Alignment.ENEMY) {
                    Buff.affect(mob, BlobImmunity.class,(Random.Int(5)==0?1024f:100f));
                }
            }
            GLog.n(Messages.get(this,"mob_all_blobimmunity"));
            positive -= 13;
        }else if(r<40){
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if(mob.alignment == Char.Alignment.ENEMY){
                    for(Buff b: mob.buffs()){
                        if(b.type == buffType.NEGATIVE && !(b instanceof Corruption)){
                            b.detach();
                        }
                    }
                }
            }
            GLog.n(Messages.get(this,"mob_all_cleanse_negative"));
            positive -= 8;
        }else if(r<49){
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if(mob.alignment == Char.Alignment.ENEMY) {
                    Buff.affect(mob, FrostImbue.class, 20f);
                    Buff.affect(mob, ToxicImbue.class).set(20f);
                }
            }
            GLog.n(Messages.get(this,"mob_all_elementimbue"));
            positive -= 13;
        }else if(r<58){
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if(mob.alignment == Char.Alignment.ENEMY) {
                    Buff.affect(mob, Haste.class, (Random.Int(5) == 0 ? 65535f : 20f));
                }
            }
            GLog.n(Messages.get(this,"mob_all_haste"));
            positive -= 18;
        }else if(r<70){
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if(mob.alignment == Char.Alignment.ENEMY) {
                    Buff.affect(mob, Adrenaline.class, (Random.Int(5) == 0 ? 200f : 20f));
                }
            }
            GLog.n(Messages.get(this,"mob_all_adrenaline"));
            positive -= 15;
        }else if(r<82){
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if(!mob.properties().contains(Char.Property.BOSS) && mob.alignment == Char.Alignment.ENEMY)
                    Buff.affect(mob, Healing.class).setHeal(65535, 0,Dungeon.depth/5 +1);
            }
            GLog.n(Messages.get(this,"mob_all_healing"));
            positive -= 12;
        }else if(r<91){
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if(mob.alignment == Char.Alignment.ENEMY){
                    Buff.affect(mob,ArcaneArmor.class).set(Math.min(mob.HT, 80),1);
                }
            }
            GLog.n(Messages.get(this,"mob_all_arcanearmor"));
            positive -= 15;
        }else if(r<100){
            for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                if(mob.alignment == Char.Alignment.ENEMY) {
                    Buff.affect(mob, Barkskin.class).set(Math.min(mob.HT, 80), 2);
                }
            }
            GLog.n(Messages.get(this,"mob_all_barkskin"));
            positive -= 15;
        }
    }

    //Blob generation is neutral, does nothing with positive balance.
    private void generateBlob(){

        int r = Random.chances(new float[]{10,10,8,6,10,10,10});

        if(r==0){
            for (int i = 0; i < Dungeon.level.length(); i++){
                if(i%56 == Random.Int(56)) GameScene.add( Blob.seed(i, 1000, ToxicGas.class));
            }
            GLog.i(Messages.get(this,"blob_toxicgas"));
        }else if(r==1){
            for (int i = 0; i < Dungeon.level.length(); i++){
                if(i%56 == Random.Int(56)) GameScene.add( Blob.seed(i, 250, CorrosiveGas.class));
            }
            GLog.i(Messages.get(this,"blob_acid"));
        }else if(r==2){
            do{
                new BlazingTrap().set(Dungeon.level.randomDestination(null)).activate();
            }while(Random.Int(4)!=0);
            GLog.i(Messages.get(this,"blob_fire"));
        }else if(r==3){
            do{
                new StormTrap().set(Dungeon.level.randomDestination(null)).activate();
            }while(Random.Int(3)!=0);
            GLog.i(Messages.get(this,"blob_electricity"));
        }else if(r==4){
            for (int i = 0; i < Dungeon.level.length(); i++){
                if(i%56 == Random.Int(56)) GameScene.add( Blob.seed(i, 1000, SmokeScreen.class));
            }
            GLog.i(Messages.get(this,"blob_smoke"));
        }else if(r==5){
            for (int i = 0; i < Dungeon.level.length(); i++){
                if(i%56 == Random.Int(56)) GameScene.add( Blob.seed(i, 1000, Blizzard.class));
            }
            GLog.i(Messages.get(this,"blob_blizzard"));
        }else if(r==6){
            for (int i = 0; i < Dungeon.level.length(); i++){
                if(i%56 == Random.Int(56)) GameScene.add( Blob.seed(i, 1000, StormCloud.class));
            }
            GLog.i(Messages.get(this,"blob_cloud"));
        }
    }

    private void affectItem(){
        int r = Random.chances(new float[]{300 - positive , 160, 210 + positive});
        switch(r){
            case 0: affectItemPositive(); break;
            case 2: affectItemNegative(); break;
            case 1: default: affectItemNeutral(); break;
        }
    }

    private void affectItemPositive(){
        int r = Random.chances(new float[]{5,5,10,10,15});
        switch(r) {
            case 0:{
                KindOfWeapon w = Dungeon.hero.belongings.weapon;
                if(w!=null){
                    switch(Random.Int(3)){
                        case 0: ((Weapon) w).enchant(Weapon.Enchantment.randomCommon()); break;
                        case 1: ((Weapon) w).enchant(Weapon.Enchantment.randomUncommon()); break;
                        case 2: ((Weapon) w).enchant(Weapon.Enchantment.randomRare()); break;
                    }
                    GLog.p(Messages.get(this,"weapon_enchant"));
                    positive += 13;
                }
                break;
            }
            case 1:{
                Armor a = Dungeon.hero.belongings.armor;
                if(a!=null){
                    switch (Random.Int(3)){
                        case 0: a.inscribe(Armor.Glyph.randomCommon()); break;
                        case 1: a.inscribe(Armor.Glyph.randomRare()); break;
                        case 2: a.inscribe(Armor.Glyph.randomUncommon()); break;
                    }
                    GLog.p(Messages.get(this,"armor_inscribe"));
                    positive += 10;
                }
                break;
            }
            case 2:{
                for(Item i: Dungeon.hero.belongings){
                    if(ScrollOfRemoveCurse.uncursable(i)){
                        ScrollOfRemoveCurse.uncurse(Dungeon.hero, i);
                    }
                }
                GLog.p(Messages.get(this,"item_cleanseall"));
                positive += 15;
                break;
            }
            case 3:{
                for(Item i: Dungeon.hero.belongings){
                    if(i instanceof Wand){
                        ((Wand)i).curCharges = (Random.Int(8)==0?24:((Wand) i).maxCharges);
                        updateQuickslot();
                    }
                }
                GLog.p(Messages.get(this,"wand_charge"));
                positive += 20;
                break;
            }
            case 4:{
                int reward = 50*Dungeon.depth;
                if(Random.Int(4) == 0){
                    reward *= 2;
                    if(Random.Int(6)==0){
                        reward *=3;
                    }
                }
                Dungeon.gold += reward;
                GLog.p(Messages.get(this,"gold_gain"));
                positive += 6;
            }
            break;
        }
    }

    private void affectItemNegative(){
        int r = Random.chances(new float[]{10, 10, 14});
        if(r==0){
            CursingTrap.curse(Dungeon.hero);
            positive -= 20;
        }else if(r==1){
            for(Item i: Dungeon.hero.belongings){
                if(i instanceof Wand){
                    ((Wand)i).curCharges = (Random.Int(10)==0?-100:0);
                    updateQuickslot();
                }
            }
            GLog.n(Messages.get(this,"wand_discharge"));
            positive -= 20;
        }else if(r==2){
            Dungeon.gold = (Random.Int(6)==0?Dungeon.gold/3:Dungeon.gold*2/3);
            GLog.n(Messages.get(this,"gold_loss"));
            positive -= 8;
        }
    }

    private void affectItemNeutral(){
        int r = Random.chances(new float[]{10,10,5,3});
        if(r==0){
            int buy = 0;
            int price = 30*Dungeon.depth +30;
            while(Dungeon.gold>price && buy<3){
                Potion p = (Potion)Generator.random(Generator.Category.POTION);
                if(!p.collect()) Dungeon.level.drop(p, Dungeon.hero.pos).sprite.drop();
                Dungeon.gold -= price;
                ++buy;
            }
            GLog.i(Messages.get(this,"gold_to_potion"));
        }else if(r==1){
            int buy = 0;
            int price = 30*Dungeon.depth +30;
            while(Dungeon.gold>price && buy<3){
                Scroll s = (Scroll)Generator.random(Generator.Category.SCROLL);
                if(!s.collect()) Dungeon.level.drop(s, Dungeon.hero.pos).sprite.drop();
                Dungeon.gold -= price;
                ++buy;
            }
            GLog.i(Messages.get(this,"gold_to_scroll"));
        }else if(r==2){
            Ring ring = (Ring)Generator.random(Generator.Category.RING);
            ring.cursed = true;
            ring.level(0);
            Dungeon.level.drop(ring, Dungeon.hero.pos).sprite.drop();
            GLog.i(Messages.get(this,"ring_generate"));
        }else if(r==3){
            Ring ring = (Ring)Generator.random(Generator.Category.RING);
            ring.cursed = true;
            ring.level(Random.Int(2,5));
            Dungeon.level.drop(ring, Dungeon.hero.pos).sprite.drop();
            GLog.i(Messages.get(this,"ring_generate"));
        }
    }

    private void affectChars(){
        int r = Random.chances(new float[]{ 270 - positive, 300 + positive, 100});
        switch (r){
            case 0: affectCharsPositive(); break;
            case 1: affectCharsNegative(); break;
            case 2: affectCharsNeutral(); break;
        }
    }

    private void affectCharsPositive(){
        int r = Random.chances(new float[]{2,2,8,5,3,6});
        switch(r){
            case 0: {
                Dungeon.hero.earnExp(Dungeon.hero.maxExp(), null);
                positive += 22;
                break;
            }
            case 1: {
                Dungeon.hero.STR++;
                GLog.p(Messages.get(this,"hero_gain_strength"));
                positive += 40;
                break;
            }
            case 2:{
                int transfer = 0;
                for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
                    if(Dungeon.level.heroFOV[mob.pos] && mob.alignment == Char.Alignment.ENEMY){
                        if(!(mob.properties().contains(Char.Property.BOSS) || mob.properties().contains(Char.Property.MINIBOSS))){
                            transfer += mob.HP/2;
                            mob.damage(mob.HP-1, this);
                        }
                    }
                }
                if(transfer>0) {
                    Buff.append(target, Healing.class).setHeal(transfer, 0,1);
                    positive += 23;
                }
                GLog.p(Messages.get(this,"soulsiphon"));
                break;
            }
            case 3:{
                int total = 0;
                for(Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )){
                    if(mob.alignment == Char.Alignment.ALLY){
                        total += mob.HP;
                        mob.damage(mob.HT,this);
                    }
                }
                for(Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )){
                    if(mob.alignment == Char.Alignment.ENEMY){
                        if(!(mob.properties().contains(Char.Property.BOSS) || mob.properties().contains(Char.Property.MINIBOSS))){
                            mob.damage(total, this);
                        }else{
                            mob.damage(total/2, this);
                        }
                    }
                }
                GLog.w(Messages.get(this,"sacrifice_ally"));
                positive += 5;
                break;
            }
            case 4:{
                for(Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )){
                    if(!(mob.properties().contains(Char.Property.BOSS) || mob.properties().contains(Char.Property.MINIBOSS))){
                        mob.destroy();
                        mob.sprite.killAndErase();
                        Dungeon.level.mobs.remove(mob);
                        TargetHealthIndicator.instance.target(null);
                    }
                }
                GLog.i(Messages.get(this,"exterminate"));
                positive += 35;
                break;
            }
            case 5:{
                int dmg = Dungeon.hero.HP/2 - 1 ;
                Dungeon.hero.damage(Dungeon.hero.HP/2 -1, this);
                for(Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )){
                    if(mob.alignment == Char.Alignment.ENEMY){
                        mob.damage(Math.round(dmg*1.6f), this);
                    }
                }
                GLog.w(Messages.get(this,"self_sacrifice"));
                positive += 5;
                break;
            }
        }
    }

    private void affectCharsNegative(){
        int r = Random.chances(new float[]{15, 7, 10, 5, 5, 15});
        switch(r){
            case 1: {
                if(Random.Int(3) == 0){
                    new GuardianTrap().set(Dungeon.hero.pos).activate();
                }else{
                    new AlarmTrap().set(Dungeon.hero.pos).activate();
                }
                positive -= 17;
                break;
            }
            case 2:{
                if(!Dungeon.bossLevel(Dungeon.depth)){
                    for(Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )){
                        if(mob.alignment == Char.Alignment.ENEMY){
                            if(!(mob.properties().contains(Char.Property.BOSS) || mob.properties().contains(Char.Property.MINIBOSS))){
                                if(Random.Int(2) == 0){
                                    mob.destroy();
                                    mob.sprite.killAndErase();
                                    Dungeon.level.mobs.remove(mob);
                                    TargetHealthIndicator.instance.target(null);
                                }else{
                                    Buff.affect(mob, BlobImmunity.class, 65535f);
                                    Buff.affect(mob, Haste.class, 65535f);
                                    Buff.affect(mob, FrostImbue.class, 65535f);
                                    Buff.affect(mob, Healing.class).setHeal(65535, 0, Dungeon.depth/2+1);
                                }
                            }
                        }
                    }
                    GLog.n(Messages.get(this,"mob_sacrifice_gain"));
                }
                positive -= 21;
                break;
            }
            case 3:{
                for(int i=0;i<3;++i){
                    new PoisonDartTrap().set(Dungeon.level.randomDestination(null)).activate();
                }
                GLog.i(Messages.get(this,"poisondart_trap"));
                positive -= 7;
                break;
            }
            case 4:{
                Dungeon.hero.HP = 1;
                for(Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )){
                    if(mob.alignment == Char.Alignment.ALLY){
                        mob.HP = 1;
                    }
                }
                GLog.n(Messages.get(this,"near_death"));
                GameScene.flash(0xCC0000);
                positive -= 50;
                break;
            }
            case 5: {
                for(Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )){
                    if(mob.alignment == Char.Alignment.ENEMY){
                        new Minibomb().explode(mob.pos);
                    }
                }
                GLog.i(Messages.get(this,"mob_explode"));
                positive -= 8;
                break;
            }
            case 0: default:{
                new Minibomb().explode(Dungeon.hero.pos);
                GLog.i(Messages.get(this,"minibomb_explode"));
                positive -= 5;
                break;
            }
        }
    }

    private void affectCharsNeutral(){
        int r = Random.chances(new float[]{10, 10});
        if(r==0){
            if(!Dungeon.bossLevel(Dungeon.depth)){
                GLog.i(Messages.get(this,"mob_tp"));
                for(Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )){
                    if(!mob.properties().contains(Char.Property.IMMOVABLE)){
                        ScrollOfTeleportation.teleportChar(mob);
                    }
                }
            }
        }else if(r==1) {
            new FlockTrap().set(Dungeon.hero.pos).activate();
            for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
                new FlockTrap().set(mob.pos).activate();
            }
            GLog.i(Messages.get(this, "flocking_trap"));
        }
    }

    private void affectPositive(){
        int r = Random.chances(new float[]{10,5,2,10,5,2,7,3});
        switch (r){
            case 0: positive -= 40; break;
            case 1: positive -= 90; break;
            case 2: positive -= 160; break;
            case 3: positive += 25; break;
            case 4: positive += 50; break;
            case 5: positive += 100; break;
            case 6: positive /= 2; break;
            case 7: positive = 0; break;
        }
        if(r<=2) GLog.p(Messages.get(this, "fortune"));
        else if(r<=5) GLog.w(Messages.get(this, "misfortune"));
        else if(r<=7) GLog.i(Messages.get(this, "balance"));
    }

    public class Minibomb extends Bomb{
        public void explode(int cell){
            //We're blowing up, so no need for a fuse anymore.
            this.fuse = null;

            Sample.INSTANCE.play( Assets.Sounds.BLAST );

            if (explodesDestructively()) {

                ArrayList<Char> affected = new ArrayList<>();

                if (Dungeon.level.heroFOV[cell]) {
                    CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);
                }

                boolean terrainAffected = false;
                for (int n : PathFinder.NEIGHBOURS9) {
                    int c = cell + n;
                    if (c >= 0 && c < Dungeon.level.length()) {
                        if (Dungeon.level.heroFOV[c]) {
                            CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
                        }

                        if (Dungeon.level.flamable[c]) {
                            Dungeon.level.destroy(c);
                            GameScene.updateMap(c);
                            terrainAffected = true;
                        }

                        //destroys items / triggers bombs caught in the blast.
                        Heap heap = Dungeon.level.heaps.get(c);
                        if (heap != null)
                            heap.explode();

                        Char ch = Actor.findChar(c);
                        if (ch != null) {
                            affected.add(ch);
                        }
                    }
                }

                for (Char ch : affected){

                    //if they have already been killed by another bomb
                    if(!ch.isAlive()){
                        continue;
                    }

                    int dmg = Random.NormalIntRange(3,6);

                    //those not at the center of the blast take less damage
                    if (ch.pos != cell){
                        dmg = Math.round(dmg*0.67f);
                    }

                    if (dmg > 0) {
                        ch.damage(dmg, this);
                    }

                    if (ch == Dungeon.hero && !ch.isAlive()) {
                        Dungeon.fail(Bomb.class);
                    }
                }

                if (terrainAffected) {
                    Dungeon.observe();
                }
            }
        }
    }
}
