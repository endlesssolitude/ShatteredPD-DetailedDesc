package com.shatteredpixel.shatteredpixeldungeon.custom.visuals;

import com.badlogic.gdx.graphics.Pixmap;
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.Image;

public class HaloReverse extends Image {
    private static final Object CACHE_KEY = HaloReverse.class;

    protected static final int RADIUS	= 128;

    protected float radius = RADIUS;
    protected float brightness = 1;

    public static final int EDGE_TRANSPARENCY = 0xFF;

    public HaloReverse(int transparency) {
        super();
        transparency &= 0xFF;

        boolean needDraw = false;
        if (!TextureCache.contains( CACHE_KEY )) {
            needDraw = true;
        }else{
            texture( CACHE_KEY );
            if(Math.abs((texture.getPixel(0, RADIUS) & 0xFF) - transparency)>4){
                needDraw = true;
            }
        }

        if(needDraw){
            int len = 2*RADIUS;
            int color;
            int distance;
            Pixmap pixmap = new Pixmap(2*RADIUS+1, 2*RADIUS+1, Pixmap.Format.RGBA8888);
            for(int i=RADIUS;i>=0;--i){
                for(int j=i;j>=0;--j){
                    distance = (int) Math.sqrt((RADIUS-i)*(RADIUS-i)+(RADIUS-j)*(RADIUS-j));
                    if(distance <= RADIUS) {
                        color = 0xFFFFFF00 | (transparency*distance/RADIUS);
                        pixmap.drawPixel(i, j, color);
                        pixmap.drawPixel(len-i, j, color);
                        pixmap.drawPixel(i, len-j, color);
                        pixmap.drawPixel(len-i, len-j, color);
                        pixmap.drawPixel(j, i, color);
                        pixmap.drawPixel(len-j, i, color);
                        pixmap.drawPixel(j, len-i, color);
                        pixmap.drawPixel(len-j, len-i, color);
                    }
                }
            }
            TextureCache.add( CACHE_KEY, new SmartTexture( pixmap ) );
        }

        texture(CACHE_KEY);
    }

    public HaloReverse(float radius, int color, float brightness, int edgeTransparency ) {

        this(edgeTransparency);

        hardlight( color );
        alpha( this.brightness = brightness );
        radius( radius );

    }

    public HaloReverse point(float x, float y ) {
        this.x = x - (width()/2f);
        this.y = y - (height()/2f);
        return this;
    }

    public void radius( float value ) {
        scale.set(  (this.radius = value) / RADIUS );
    }
}
