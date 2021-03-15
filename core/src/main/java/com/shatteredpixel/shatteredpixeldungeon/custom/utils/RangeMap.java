package com.shatteredpixel.shatteredpixeldungeon.custom.utils;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;

//This util class is used for describing range
//for example, returns a range within + directions <=2 , pos centered. WONT OUTSIDE OF MAP.
//Add new methods once necessary.
public class RangeMap {
    // M : distance for + directions
    // D : distance for x directions
    // C : distance for 8 directions

    private static int[] build(int pos, int[][]mapXY){
        int xOfs = pos % Dungeon.level.width();
        int yOfs = pos / Dungeon.level.width();
        for(int i=0;i<mapXY[0].length;++i){
            mapXY[0][i] += xOfs;
            mapXY[1][i] += yOfs;
        }
        return MapXY.ToLength(mapXY, true);
    }

    public static int[] arrayCopy(int[]... arrays){
        int arrayLength = 0;
        int startIndex = 0;
        for(int[] file : arrays){
            arrayLength = arrayLength + file.length;
        }
        int[] fileArray = new int[arrayLength];
        for(int i = 0; i < arrays.length; i++){
            if(i > 0){
                startIndex = startIndex + arrays[i-1].length;
            }
            System.arraycopy(arrays[i], 0, fileArray, startIndex, arrays[i].length);
        }
        return fileArray;
    }

    public static int[] M0(int pos){
        return new int[]{pos};
    }

    public static int[] M1(int pos){
        return build(pos,
                MapXY.buildXYMap(
                        new int[]{
                                -1, 0, 0, 1, 1, 0, 0, -1
                        }
                )
        );
    }

    public static int[] D1(int pos){
        return build(pos,
                MapXY.buildXYMap(
                        new int[]{
                                -1, -1, 1, 1, 1, -1, -1, 1
                        }
                )
        );
    }

    public static int[] C1(int pos){
        return arrayCopy(M1(pos), D1(pos));
    }

    public static int[] M2(int pos){
        return arrayCopy(C1(pos),
                build(pos,
                        MapXY.buildXYMap(
                                new int[]{
                                        -2, 0, 2, 0, 0, -2, 0, 2
                                }
                        )
                )
        );
    }

    public static int[] centeredRect(int center, int w, int h){
        if(w<0 || h<0) return null;
        if(w*h>1000000) return null; //too large, might fail;
        int[] xyMap = new int[2*(2*w+1)*(2*h+1)];
        int count = 0;
        for(int i=-w;i<=w;++i){
            for(int j=-h;j<=h;++j){
                xyMap[2*count]=i;
                xyMap[2*count+1]=j;
            }
        }
        return build(center, MapXY.buildXYMap(xyMap));
    }
}
