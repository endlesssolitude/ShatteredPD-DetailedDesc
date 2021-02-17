package com.shatteredpixel.shatteredpixeldungeon.custom.utils;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.watabou.utils.Point;
import com.watabou.utils.PointF;

public class GameMathExtension {
    public static int gate(int min, int value, int max){
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }

    public static int[] NEIGHBOURS12(){
        int w = Dungeon.level.width();
        return new int[]{-1, -2, 1, 2, w, -w, -2*w, 2*w, w+1, w-1, -w+1, -w-1};
    }

    public static int [] NEIGHBOURS20(){
        int w = Dungeon.level.width();
        return new int[]{
                1, -1, w, -w, 1+w, 1-w, -1+w, -1-w, 2, -2, -2*w, 2*w,
                -2*w+1, -2*w-1, 2*w+1, 2*w-1, 2+w, 2-w, -2+w, -2-w
        };
    }

    public static float angle(int from, int to){
        float angle = PointF.angle(new PointF(pointToF(Dungeon.level.cellToPoint(from))),
                new PointF(pointToF(Dungeon.level.cellToPoint(to))));
        angle /= -PointF.G2R;
        return angle;
    }
    protected static PointF pointToF(Point p){
        return new PointF(p.x, p.y);
    }
}
