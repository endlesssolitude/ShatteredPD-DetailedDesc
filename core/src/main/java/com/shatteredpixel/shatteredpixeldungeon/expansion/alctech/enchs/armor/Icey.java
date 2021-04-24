package com.shatteredpixel.shatteredpixeldungeon.expansion.alctech.enchs.armor;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.Game;
import com.watabou.noosa.Halo;
import com.watabou.utils.Random;

public class Icey extends Armor.Glyph{
    @Override
    public int proc(Armor armor, Char attacker, Char defender, int damage) {
        float radius = Math.min(2f + armor.buffedLvl() * 0.15f, 4f);

        if(Random.Int(2)==0) {
            defender.sprite.parent.add(new Halo(radius * DungeonTilemap.SIZE, 0xB080CCFF, 1f) {
                private float life = 0.7f;

                @Override
                public void update() {
                    if ((life -= Game.elapsed) < 0f) {
                        killAndErase();
                    } else {
                        am = life * (-1f);
                        aa = life * (+1f);
                    }
                    super.update();
                }

                @Override
                public void draw() {
                    Blending.setLightMode();
                    super.draw();
                    Blending.setNormalMode();
                }
            }.point(defender.sprite.center().x, defender.sprite.center().y));

            for (Char ch : Actor.chars()) {
                if (ch.alignment != Char.Alignment.ALLY && ch != defender) {
                    if (Dungeon.level.trueDistance(ch.pos, defender.pos) < radius) {
                        Buff.affect(ch, Chill.class, Random.Float(3f, 7f));
                    }
                }
            }
        }

        return damage;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing(0x0080FF);
    }
}
