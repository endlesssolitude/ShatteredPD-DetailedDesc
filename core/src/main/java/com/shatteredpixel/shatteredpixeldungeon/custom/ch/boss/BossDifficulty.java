package com.shatteredpixel.shatteredpixeldungeon.custom.ch.boss;

import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;

public enum BossDifficulty {
    NONE("none"),
    EASY("easy"),
    NORMAL("normal"),
    HARD("hard"),
    LUNATIC("lunatic");

    public String name_key;

    BossDifficulty(String key){
        this.name_key = key;
    }

    public static int getDifficulty(int whichBoss){
        return (Statistics.enhance_boss_settings >> (4*whichBoss)) & 0xf;
    }

    public static BossDifficulty getEnumDifficulty(int whichBoss){
        int ordinal = getDifficulty(whichBoss);
        for(BossDifficulty bd: BossDifficulty.values()){
            if(bd.ordinal() == ordinal){
                return bd;
            }
        }
        return NONE;
    }

    public static String getNameKey(int difficulty){
        for(BossDifficulty bd: BossDifficulty.values()){
            if(bd.ordinal() == difficulty){
                return bd.name_key;
            }
        }
        return "";
    }

    public static String getName(int difficulty){
        return M.L(BossDifficulty.class, getNameKey(difficulty));
    }

    public static String baseRoot(int whichBoss){
        switch (whichBoss){
            case 0: return "data/hardboss/goo/";
            case 1: return "data/hardboss/tengu/";
            case 2: return "data/hardboss/dm300/";
            case 3: return "data/hardboss/dwarfking/";
            case 4: default: return "data/hardboss/yogreal/";
        }
    }

    public static String fullPath(int whichBoss){
        return baseRoot(whichBoss) + getNameKey(getDifficulty(whichBoss));
    }

}
