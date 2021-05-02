package com.shatteredpixel.shatteredpixeldungeon.custom.visuals;

import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.gltextures.TextureCache;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;

public class CellColorBlock extends Image {
    private float alpha;
    private float delay;
    private float fade;
    private float defAlpha;

    public CellColorBlock( int pos, float maxAlpha, float staticTime, float fadeTime, int ARGB8888) {
        super( TextureCache.createSolid( ARGB8888 ) );

        origin.set( 0.5f );

        point( DungeonTilemap.tileToWorld( pos ).offset(
                DungeonTilemap.SIZE / 2,
                DungeonTilemap.SIZE / 2 ) );

        scale.set(DungeonTilemap.SIZE);
        alpha = maxAlpha;

        delay = staticTime + fadeTime;
        fade = fadeTime;
        defAlpha = maxAlpha;
    }

    @Override
    public void update() {
        delay -= Game.elapsed;
        if(delay > fade){
            //do nothing
        }else if(delay > 0f){
            scale.set(DungeonTilemap.SIZE*delay/fade);
            alpha(delay/fade * defAlpha);
        }else{
            killAndErase();
        }
    }

    @Override
    public void draw() {
        Blending.setLightMode();
        super.draw();
        Blending.setNormalMode();
    }
}
