package com.shatteredpixel.shatteredpixeldungeon.custom.visuals;

import com.badlogic.gdx.graphics.Pixmap;
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.Halo;
import com.watabou.noosa.Image;

public class HaloLowRes extends Image {
    private static final Object CACHE_KEY = Halo.class;

    protected static final int RADIUS	= 32;

    protected float radius = RADIUS;
    protected float brightness = 1;

    public HaloLowRes() {
        super();

        if (!TextureCache.contains( CACHE_KEY )) {
            Pixmap pixmap = new Pixmap(2*RADIUS+1, 2*RADIUS+1, Pixmap.Format.RGBA8888);
            pixmap.setColor( 0xFFFFFF08 );
            for (int i = 0; i < RADIUS; i+=2) {
                pixmap.fillCircle(RADIUS, RADIUS, (RADIUS - i));
            }
            TextureCache.add( CACHE_KEY, new SmartTexture( pixmap ) );
        }

        texture( CACHE_KEY );
    }

    public HaloLowRes( float radius, int color, float brightness ) {

        this();

        hardlight( color );
        alpha( this.brightness = brightness );
        radius( radius );
    }

    public HaloLowRes point(float x, float y ) {
        this.x = x - (width()/2f);
        this.y = y - (height()/2f);
        return this;
    }

    public void radius( float value ) {
        scale.set(  (this.radius = value) / RADIUS );
    }
}
