package com.shatteredpixel.shatteredpixeldungeon.custom.visuals.effects;

import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.BallisticaReal;
import com.shatteredpixel.shatteredpixeldungeon.effects.Effects;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.utils.PointF;

import java.util.LinkedHashMap;

public class RotateBeam extends Image {
    private float chargeTime;
    private float angSpeed;
    private float rotate;
    private float fadeTime;
    private float range;
    private float startAngle;
    private PointF from;
    private float time = 0f;
    private float[] total;
    private int type = 0;

    private LinkedHashMap<Integer, Float> hasCollided = new LinkedHashMap<>();

    public RotateBeam(Effects.Type asset, PointF from, float start, float rotate, float asp, float range, float charge, float fade){
        super( Effects.get( asset ) );
        this.from = from;
        this.startAngle = start;
        this.rotate = rotate;
        this.angSpeed = asp;
        this.range = range;
        this.chargeTime = charge;
        this.fadeTime = fade;

        origin.set( 0, height / 2 );

        x = from.x*DungeonTilemap.SIZE - origin.x;
        y = from.y*DungeonTilemap.SIZE - origin.y;

        total = new float[3];
        total[0] = chargeTime;
        total[1] = total[0]+Math.abs(this.rotate/ angSpeed);
        total[2] = total[1]+fadeTime;

        angle = startAngle;
    }

    public static void setCollide(OnCollide collide) {
        RotateBeam.collide = collide;
    }

    public RotateBeam setProperty(int property){
        this.type = property;
        updateScale();
        return this;
    }

    @Override
    public void update(){
        super.update();
        time += Game.elapsed;

        if(time >= total[2]){
            killAndErase();
        }else if(time > total[1]){
            float p = (total[2]-time)/fadeTime;
            alpha(p);
            scale.y = p;
        }else if(time > total[0]){
            angle = (angSpeed * (time-total[0]) + startAngle);
            alpha(1f);
        }else{
            float p = time/total[0];
            alpha(p);
            scale.y = p;
        }
        if(time-total[1]<0.05f && time-total[0]>-0.05f) updateScale();
    }

    private void updateScale(){
        BallisticaReal ba = new BallisticaReal(from, angle, range, type);
        float dx = ba.collisionF.x - from.x;
        float dy = ba.collisionF.y - from.y;
        float distance = (float) Math.sqrt(dx*dx + dy*dy);
        scale.x = Math.min(distance, range)*DungeonTilemap.SIZE/width;

        for(int i: ba.subPath(1, ba.dist)){
            collide.cellProc(i);
            Char ch = Actor.findChar(i);
            if(ch != null){
                if(!hasCollided.containsKey(ch.id())){
                    collide.onHitProc(ch);
                    hasCollided.put(ch.id(), angle);
                }
            }
        }

        //remove token if beam can't shoot that Char.
        //Normally Chars are sparse, so remove one each flash is just enough.
        if(!hasCollided.isEmpty()) {
            int removeFirst = hasCollided.keySet().iterator().next();
            if (Math.abs(angle - hasCollided.values().iterator().next()) > 180) {
                hasCollided.remove(removeFirst);
            }
        }

    }

    @Override
    public void draw() {
        Blending.setLightMode();
        super.draw();
        Blending.setNormalMode();
    }

    private static OnCollide collide;

    public interface OnCollide {
        //when hit char
        int onHitProc(Char ch);
        //when hit cell
        int cellProc(int i);
    }

}
