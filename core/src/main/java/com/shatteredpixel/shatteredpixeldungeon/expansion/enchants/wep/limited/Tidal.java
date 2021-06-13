package com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.wep.limited;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.StormCloud;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.GME;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.HitBack;
import com.shatteredpixel.shatteredpixeldungeon.custom.utils.RangeMap;
import com.shatteredpixel.shatteredpixeldungeon.expansion.enchants.baseclasses.CountInscription;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.noosa.audio.Sample;

public class Tidal extends CountInscription {
    {
        defaultTriggers = 80;
    }
    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage) {
        int dist = Math.min(weapon.reachFactor(attacker) + 1, 4);
        int[] targets = RangeMap.manhattanRing(attacker.pos, 0, dist);
        int w = Dungeon.level.width();
        int dx = defender.pos % w - attacker.pos % w;
        int dy = defender.pos / w - attacker.pos / w;
        for(int i: targets){
            if((i%w-attacker.pos%w)*dx + (i/w-attacker.pos/w)*dy >= 0){
                Dungeon.level.setCellToWater(true, i);
                Char ch = Actor.findChar(i);
                if(ch != null && ch != attacker){
                    WandOfBlastWave.throwChar(ch, HitBack.hitBack(attacker, ch), dist);
                    ch.damage(GME.accurateRound(damage*.4f), attacker);
                }
            }
        }
        Sample.INSTANCE.play(Assets.Sounds.WATER);

        consume(weapon, attacker);
        return damage;
    }

    @Override
    public void useUp(Weapon w, Char attacker) {
        super.useUp(w, attacker);
        GameScene.add(Blob.seed(attacker.pos, 500, StormCloud.class));
    }
}
