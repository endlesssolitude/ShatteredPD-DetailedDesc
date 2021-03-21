/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon;

import com.shatteredpixel.shatteredpixeldungeon.items.Dewdrop;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorruption;

public class Challenges {

	//Some of these internal IDs are outdated and don't represent what these challenges do
	public static final long NO_FOOD				= 1;
	public static final long NO_ARMOR			= 2;
	public static final long NO_HEALING			= 4;
	public static final long NO_HERBALISM		= 8;
	public static final long SWARM_INTELLIGENCE	= 16;
	public static final long DARKNESS			= 32;
	public static final long NO_SCROLLS		    = 64;
	public static final long CHAMPION_ENEMIES	= 128;

	public static final long TEST_MODE			= 1<<8;
	//hero.live for buff, mimic spawning
	public static final long MIMIC_DUNGEON 		= 1<<9;
	//Dungeon.newLevel, Hero.speed(for speed limit), Goo and Yog spawning
	public static final long ELITE_BOSSES_1		= 1<<10;
	public static final long ELITE_BOSSES_2 		= 1<<11;
	public static final long ELITE_BOSSES_3 		= 1<<12;
	public static final long ELITE_BOSSES_4 		= 1<<13;
	public static final long ELITE_BOSSES_5 		= 1<<14;
	public static final long ELITE_ENEMIES_1		= 1<<15;
	public static final long ELITE_ENEMIES_2		= 1<<16;
	public static final long ELITE_ENEMIES_3		= 1<<17;
	public static final long ELITE_ENEMIES_4		= 1<<18;
	public static final long ELITE_ENEMIES_5		= 1<<19;
	public static final long ELITE_BOSSES 			=
			ELITE_BOSSES_1 | ELITE_BOSSES_2 | ELITE_BOSSES_3 | ELITE_BOSSES_4 | ELITE_BOSSES_5;
	public static final long ELITE_ENEMIES			=
			ELITE_ENEMIES_1 | ELITE_ENEMIES_2 | ELITE_ENEMIES_3 | ELITE_ENEMIES_4 | ELITE_ENEMIES_5;

	public static final long EXPANSION_MISC		= 1<<20;

	public static final long MAX_VALUE           = (1<<21)-1;

	public static final String[] NAME_IDS = {
			"champion_enemies",
			"no_food",
			"no_armor",
			"no_healing",
			"no_herbalism",
			"swarm_intelligence",
			"darkness",
			"no_scrolls",

			"test_mode",

			"mimic_dungeon",
			"elite_bosses_1",
			"elite_bosses_2",
			"elite_bosses_3",
			"elite_bosses_4",
			"elite_bosses_5",
			"elite_enemies_1",
			"elite_enemies_2",
			"elite_enemies_3",
			"elite_enemies_4",
			"elite_enemies_5",

			"expansion_misc"

	};

	public static final long[] MASKS = {
			CHAMPION_ENEMIES, NO_FOOD, NO_ARMOR, NO_HEALING, NO_HERBALISM, SWARM_INTELLIGENCE, DARKNESS, NO_SCROLLS,
			TEST_MODE,
			MIMIC_DUNGEON,
			ELITE_BOSSES_1, ELITE_BOSSES_2, ELITE_BOSSES_3, ELITE_BOSSES_4, ELITE_BOSSES_5,
			ELITE_ENEMIES_1, ELITE_ENEMIES_2, ELITE_ENEMIES_3, ELITE_ENEMIES_4, ELITE_ENEMIES_5,

			EXPANSION_MISC
	};

	public static int activeChallenges(){
		int chCount = 0;
		for (long ch : Challenges.MASKS){
			if ((Dungeon.challenges & ch) != 0) chCount++;
		}
		return chCount;
	}

	public static boolean isItemBlocked( Item item ){

		if (Dungeon.isChallenged(NO_HERBALISM) && item instanceof Dewdrop){
			return true;
		}

		if(Dungeon.isChallenged(ELITE_ENEMIES)){
			if(item instanceof WandOfBlastWave || item instanceof WandOfCorruption){
				return true;
			}
		}

		return false;

	}

}