package com.shatteredpixel.shatteredpixeldungeon.custom.utils;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.watabou.utils.GameMath;
import com.watabou.utils.Point;
import com.watabou.utils.PointF;

import java.util.ArrayList;

public class PoleVectorConverter {
    private static float A2P = 0.0174533f;

    //We assume up is positive y but in game it is opposite.
    public static PointF poleToPointF(float angle, float range){
        return new PointF((float)(range*Math.cos(angle*A2P)), (float)(-range*Math.sin(angle*A2P)));
    }

    //just reach the target
    public static int findTargetCell(int from, float angle, float range){
        PointF vector = poleToPointF(angle, range);
        Point start = Dungeon.level.cellToPoint(from);

        float movX = Math.abs(vector.x);
        float movY = Math.abs(vector.y);

        //we regard it can't shoot to another tile, though it can shoot to +-1tile
        if(range<1.4142f){
            return from;
        }

        float stepX;
        float stepY;
        boolean edited = false;
        if(movX>movY){
            stepX = (vector.x>0?1f:-1f);
            stepY = movY/movX*(vector.y>0?1f:-1f);
        }else{
            stepY = (vector.y>0?1f:-1f);
            stepX = movX/movY*(vector.x>0?1f:-1f);
        }
        float eachStep = (float)Math.sqrt(stepX*stepX + stepY*stepY);

        float headX = start.x + stepX;
        float headY = start.y + stepY;

        while(insideMap(headX, headY) && (range-eachStep>-0.0001f)){
            headX += stepX;
            headY += stepY;
            range -= eachStep;
            edited = true;
        }
        if(!edited) return from;

        headX -= stepX;
        headY -= stepY;

        headX = GameMath.gate(0, headX, Dungeon.level.width());

        return Dungeon.level.pointToCell(new Point((int)headX, (int)headY));
    }

    //range is the max dist. Usually no use. Find most accurate solution
    public static int findBestTargetCell(int from, float angle, float range){
        ArrayList<PointF> path = new ArrayList<>();
        PointF vector = poleToPointF(angle, range);
        Point start = Dungeon.level.cellToPoint(from);

        float movX = Math.abs(vector.x);
        float movY = Math.abs(vector.y);

        //we regard it can't shoot to another tile, though it can shoot to +-1tile
        if(range<1.4142f){
            return from;
        }

        float stepX;
        float stepY;
        if(movX>movY){
            stepX = (vector.x>0?1f:-1f);
            stepY = movY/movX*(vector.y>0?1f:-1f);
        }else{
            stepY = (vector.y>0?1f:-1f);
            stepX = movX/movY*(vector.x>0?1f:-1f);
        }
        float eachStep = (float)Math.sqrt(stepX*stepX + stepY*stepY);

        float headX = start.x + stepX;
        float headY = start.y + stepY;

        while(insideMap(headX, headY) && (range-eachStep>-0.0001f)){
            path.add(new PointF(headX, headY));
            headX += stepX;
            headY += stepY;
            range -= eachStep;
        }

        if(path.isEmpty()){
            return from;
        }

        float minMistake = 0.5f;
        float left;
        int minErrY = start.y;
        int minErrX = start.x;

        if(movX>movY){
            for(PointF pf: path){
                left = pf.y - (int)pf.y;
                float realLeft = (left>0.5f?1f-left:left);
                if(realLeft/(Math.abs(pf.x - start.x)) < minMistake){
                    minMistake = realLeft/(Math.abs(pf.x - start.x));
                    minErrY = Math.round(pf.y - (left == realLeft? -left: realLeft));
                    minErrX = Math.round(pf.x);
                }
            }
        }else{
            for(PointF pf: path){
                left = pf.x - (int)pf.x;
                float realLeft = (left>0.5f?1f-left:left);
                if(realLeft/(Math.abs(pf.y - start.y)) < minMistake){
                    minMistake = realLeft/(Math.abs(pf.y - start.y));
                    minErrX = Math.round(pf.x - (left == realLeft? -left: realLeft));
                    minErrY = Math.round(pf.y);
                }
            }
        }

        minErrX = (int)GameMath.gate(0, minErrX, Dungeon.level.width()-1);

        return Dungeon.level.pointToCell(new Point(minErrX, minErrY));
    }

    private static boolean insideMap(float x, float y){
        return (x>0) && (x<Dungeon.level.width()) && (y<Dungeon.level.length()/Dungeon.level.width()) && (y>0);
    }

}
