package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enhancedPotion;

import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfInvisibility;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfMindVision;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfParalyticGas;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfPurity;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.plants.Blindweed;
import com.shatteredpixel.shatteredpixeldungeon.plants.Earthroot;
import com.shatteredpixel.shatteredpixeldungeon.plants.Fadeleaf;
import com.shatteredpixel.shatteredpixeldungeon.plants.Firebloom;
import com.shatteredpixel.shatteredpixeldungeon.plants.Icecap;
import com.shatteredpixel.shatteredpixeldungeon.plants.Mageroyal;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant;
import com.shatteredpixel.shatteredpixeldungeon.plants.Rotberry;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sorrowmoss;
import com.shatteredpixel.shatteredpixeldungeon.plants.Starflower;
import com.shatteredpixel.shatteredpixeldungeon.plants.Stormvine;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sungrass;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;

import java.util.HashMap;
//potion brew library
public class PBL {
    private static final HashMap<Class<? extends Potion>, Integer> potionId = new HashMap<>();
    static {
        potionId.put(PotionOfExperience.class,      0);
        potionId.put(PotionOfFrost.class,           1);
        potionId.put(PotionOfHaste.class,           2);
        potionId.put(PotionOfHealing.class,         3);
        potionId.put(PotionOfLiquidFlame.class,     4);
        potionId.put(PotionOfInvisibility.class,    5);
        potionId.put(PotionOfLevitation.class,      6);
        potionId.put(PotionOfMindVision.class,      7);
        potionId.put(PotionOfParalyticGas.class,    8);
        potionId.put(PotionOfPurity.class,          9);
        potionId.put(PotionOfStrength.class,        10);
        potionId.put(PotionOfToxicGas.class,        11);
    }

    private static final HashMap<Class<? extends Plant.Seed>, Integer> seedId = new HashMap<>();
    static {
        seedId.put(Starflower.Seed.class,           0);
        seedId.put(Icecap.Seed.class,               1);
        seedId.put(Swiftthistle.Seed.class,         2);
        seedId.put(Sungrass.Seed.class,             3);
        seedId.put(Firebloom.Seed.class,            4);
        seedId.put(Blindweed.Seed.class,            5);
        seedId.put(Stormvine.Seed.class,            6);
        seedId.put(Fadeleaf.Seed.class,             7);
        seedId.put(Earthroot.Seed.class,            8);
        seedId.put(Mageroyal.Seed.class,            9);
        seedId.put(Rotberry.Seed.class,             10);
        seedId.put(Sorrowmoss.Seed.class,           11);
    }

    private static final HashMap<Integer, Float> chanceMap = new HashMap<>();
    static {
        chanceMap.put(0, 1f);
        chanceMap.put(1, 3f);
        chanceMap.put(2, 2f);
        chanceMap.put(3, 3f);
        chanceMap.put(4, 3f);
        chanceMap.put(5, 2f);
        chanceMap.put(6, 3f);
        chanceMap.put(7, 3f);
        chanceMap.put(8, 2f);
        chanceMap.put(9, 2f);
        chanceMap.put(10, 0f);
        chanceMap.put(11, 2f);
    }

    private static float[] normalize(float[] vector){
        float[] unitVector = new float[vector.length];
        float total = vectorLength(vector);
        //failure
        if(total < 0.0001f) total=0.0001f;

        for(int i=0;i<vector.length;++i){
            unitVector[i] = vector[i]/total;
        }
        return unitVector;
    }

    private static final HashMap<Integer, Class<? extends EnhancedPotion>> enhancedLib = new HashMap<>();
    static {
        enhancedLib.put(0, PotionExpEX.class);
        enhancedLib.put(1, PotionFrostEX.class);
        enhancedLib.put(2, PotionHasteEX.class);
        enhancedLib.put(3, PotionHealingEX.class);
        enhancedLib.put(4, PotionFireEX.class);
        enhancedLib.put(5, PotionInvEX.class);
        enhancedLib.put(6, PotionFlyEX.class);
        enhancedLib.put(7, PotionMvEX.class);
        enhancedLib.put(8, PotionParalysisEX.class);
        enhancedLib.put(9, PotionPurityEX.class);
        enhancedLib.put(10, PotionStrEX.class);
        enhancedLib.put(11, PotionToxicGasEX.class);
    }

    public static HashMap<Class<? extends Potion>, Integer> potionIdMap(){return potionId;}
    public static HashMap<Class<? extends Plant.Seed>, Integer> seedIdMap(){return seedId;}
    public static HashMap<Integer, Float> chanceMap(){return chanceMap;}
    public static HashMap<Integer, Class<? extends EnhancedPotion>> enhancedLibMap(){return enhancedLib;}

    public static float vectorLength(float[] vector){
        float total = 0f;
        for(float projection: vector){
            total +=projection*projection;
        }
        return (float) Math.sqrt(total);
    }

    public static float dotProduct(float[] left, float[] right){
        int length = Math.min(left.length, right.length);
        float product = 0;
        for(int i=0;i<length;++i){
            product+=left[i]*right[i];
        }
        return product;
    }
}