package com.shatteredpixel.shatteredpixeldungeon.custom.utils;

public class SeedUtil {

    //Here we regard 'A' as 0, 'Z' as 25.
    public static long directConvert(String code){
        long total = 0;
        for(char c: code.toCharArray()){
            c -= 'A';
            total *= 26;
            total += c;
        }
        if(total < 0){
            total += Long.MAX_VALUE;
        }
        return total % 5429503678976L;
    }
}
