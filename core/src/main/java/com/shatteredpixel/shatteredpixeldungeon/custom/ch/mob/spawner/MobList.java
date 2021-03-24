package com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.spawner;

import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.city.ElementalH;
import com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.prison.DM100H;
import com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.sewer.CrabH;
import com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.sewer.GnollH;
import com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.sewer.RatH;
import com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.sewer.SlimeH;
import com.shatteredpixel.shatteredpixeldungeon.custom.ch.mob.sewer.SwarmH;

import java.util.ArrayList;
import java.util.Arrays;

public class MobList {

    public static ArrayList<Class<? extends Mob>> HardMobList(int depth){
        switch (depth){
            case 1: default:
                //3x rat, 1x snake
                return new ArrayList<>(Arrays.asList(
                        ElementalH.random()));
            case 2:
                //2x rat, 1x snake, 2x gnoll
                return new ArrayList<>(Arrays.asList(DM100H.class));
            case 3:
                //1x rat, 3x gnoll, 1x swarm, 1x crab
                return new ArrayList<>(Arrays.asList(RatH.class,
                        GnollH.class, GnollH.class, GnollH.class,
                        SwarmH.class,
                        CrabH.class));
            case 4: case 5:
                //1x gnoll, 1x swarm, 2x crab, 2x slime
                return new ArrayList<>(Arrays.asList(GnollH.class,
                        SwarmH.class,
                        CrabH.class, CrabH.class,
                        SlimeH.class, SlimeH.class));
        }
    }
    public static void swapMobAlts(ArrayList<Class<?extends Mob>> rotation){
        for (int i = 0; i < rotation.size(); i++) {
            /*
            if(Random.Int(8)==0){
                Class<? extends Mob> cl = rotation.get(i);
                if(cl == RatH.class){
                    cl = Albino.class;
                }
                rotation.set(i, cl);
            }

             */
        }
    }
}
