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

package com.shatteredpixel.shatteredpixeldungeon.journal;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.bomb.ArcaneBD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.bomb.BombDict;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.bomb.FireBD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.bomb.FlashBD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.bomb.FrostBD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.bomb.HolyBD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.bomb.NoiseBD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.bomb.RegrowthBD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.bomb.SharpBD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.bomb.ShockBD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.bomb.WollyBD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.catalyst.AlchemyCD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.catalyst.ArcaneCD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.food.BlandFCD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.food.BlandFD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.food.MeatD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.food.MeatPieD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.food.PastyD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.food.RationD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.potion.BrewD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.potion.ElixirD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.potion.POExpD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.potion.POFrostD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.potion.POHasteD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.potion.POHealingD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.potion.POID;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.potion.POLFD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.potion.POLeD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.potion.POMVD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.potion.POPGD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.potion.POPurityD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.potion.POSD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.potion.POTGD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.scroll.SOIdentifyD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.scroll.SOLullabyD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.scroll.SOMID;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.scroll.SOMMD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.scroll.SORCD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.scroll.SORageD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.scroll.SORechargingD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.scroll.SORetriD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.scroll.SOTerrorD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.scroll.SOTpD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.scroll.SOTransD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.scroll.SOUpgredeD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.alchemy.spell.SpellDetailD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.armor.ACD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.armor.ACED;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.armor.ARED;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.armor.AUED;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.armor.ClothArmorDict;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.armor.HuntressArmorDict;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.armor.LeatherArmorDict;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.armor.MageArmorDict;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.armor.MailArmorDict;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.armor.PlateArmorDict;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.armor.RogueArmorDict;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.armor.ScaleArmorDict;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.armor.WarriorArmorDict;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.artifact.AlcTooD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.artifact.ChaliceOfBloodDict;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.artifact.CloakOSD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.artifact.DriedRD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.artifact.EtherealCD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.artifact.HornOPD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.artifact.SandalsOND;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.artifact.TalismanOFD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.artifact.ThiveAD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.artifact.TimeKHD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.artifact.UnstableSpellBookDict;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.buff.DebuffA;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.buff.DebuffB;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.buff.DebuffC;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.buff.PobuffA;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.buff.PobuffB;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.buff.PobuffC;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.buff.PobuffD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.buff.PobuffE;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.GlobalIntro;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoAlchemy;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoArmor;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoArtifact;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoBomb;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoBones;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoFood;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoGold;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoHero;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoLevelChasm;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoLevelFeeling;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoLevelRoom;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoLevelShop;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoLevelTrap;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoLimitedDrop;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoLockedFloor;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoMob;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoMobProperty;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoMobSpawn;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoMobSpecial;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoPotion;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoPriority;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoRanking;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoRing;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoScroll;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoSeed;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoTier;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoWand;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoWandCursed;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoWeaponMelee;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.InfoWeaponRanged;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.document.ItemGeneral;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.AScroD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.AlbinoD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.ArmoredBruteD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.ArmoredStatueD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.BanditD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.BatD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.BeeD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.BlacksmithD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.BruteD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.CSlimeD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.CrabD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.CrystalMimicD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.DM100D;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.DM200D;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.DM201D;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.DM300D;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.DemonSpawnerD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.EleChaosD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.EleFireD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.EleFrostD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.EleShockD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.EyeD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.FRatD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.FishD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.FistaD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.FistbD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.FistcD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.GCrabD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.GhostD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.GhoulD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.GnollD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.GnollHD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.GoldenMimicD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.GolemD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.GooD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.GuardD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.ImageD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.ImpD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.KingD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.LarvaD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.MimicD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.MonkD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.NElementalD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.NecroD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.PImageD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.RatD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.RipperD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.RotHD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.RotLD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.SMonkD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.ScroD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.ShamanD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.SkeletonD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.SlimeD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.SnakeD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.SpinnerD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.StatueD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.SuccubusD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.SwarmD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.TenguD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.ThiefD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.WandMakerD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.WarlockD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.WraithD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.YogD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.divider.CH1BOSS;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.divider.CH1DIV;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.divider.CH2BOSS;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.divider.CH2DIV;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.divider.CH3BOSS;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.divider.CH3DIV;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.divider.CH4BOSS;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.divider.CH4DIV;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.divider.CH5BOSS;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.divider.CH5DIV;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.mob.divider.NORMALDIV;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.plant.SeedBlindweedD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.plant.SeedDreamfoilD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.plant.SeedEarthrootD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.plant.SeedFadeleafD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.plant.SeedFirebloomD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.plant.SeedIcecapD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.plant.SeedRotberryD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.plant.SeedSorrowmessD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.plant.SeedStarflowerD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.plant.SeedStormvineD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.plant.SeedSungrassD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.plant.SeedSwiftthistleD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.ring.RingAccD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.ring.RingEleD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.ring.RingEneD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.ring.RingEvaD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.ring.RingForceD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.ring.RingFurD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.ring.RingHasteD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.ring.RingMightD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.ring.RingShsD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.ring.RingTenD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.ring.RingWeaD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.trap.TrapA;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.trap.TrapB;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.trap.TrapC;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.trap.TrapD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.unclassified.AnkhD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.unclassified.BrokenSealD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.unclassified.DewvialD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.unclassified.DustD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.unclassified.GoldD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.unclassified.GuguguD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.wand.WandBWD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.wand.WandCD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.wand.WandCpD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.wand.WandDD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.wand.WandFD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.wand.WandFrostD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.wand.WandLD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.wand.WandLED;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.wand.WandMMD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.wand.WandPLD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.wand.WandRD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.wand.WandTD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.wand.WandWD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.AssBladeD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.BattleAxeD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.BolasD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.BowD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.CrosssbowD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.DaggarD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.DartD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.DartTippedD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.DirkD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.FishingSpearD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.FlailD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.ForceCubeD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.GShieldD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.GSwordD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.GauntletD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.GlaiveD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.GlovesD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.GreatAxeD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.HandAxeD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.HeavyBoomerangD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.JavelinD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.KunaiD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.LSwordD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.MaceD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.MageStaffD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.QStaffD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.RBladeD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.RShieldD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.SaiD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.ScimitarD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.ShortSwordDict;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.ShurikenD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.SpearD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.SwordD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.ThrowingClubD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.ThrowingHammerD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.ThrowingKnifeD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.ThrowingSpearD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.ThrowingStoneD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.TomahawkD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.TridentD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.WCD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.WCED;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.WRED;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.WSSwordD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.WUED;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.WarHammerD;
import com.shatteredpixel.shatteredpixeldungeon.custom.dictionary.weapon.WhipD;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public enum Catalog {
	
	ARMORS,
	ARTIFACTS,
	ALCHEMY,
	WEAPONS,
	PLANTS,
	WANDS,
	RINGS,
	MOBS,
	DOCUMENTS,
	UNCLASSIFIED;
	
	private LinkedHashMap<Class<? extends Item>, Boolean> seen = new LinkedHashMap<>();
	
	public Collection<Class<? extends Item>> items(){
		return seen.keySet();
	}
	
	public boolean allSeen(){
		for (Class<?extends Item> item : items()){
			if (!seen.get(item)){
				return false;
			}
		}
		return true;
	}
	
	static {
		//armors
		ARMORS.seen.put(ClothArmorDict.class, true);
		ARMORS.seen.put(LeatherArmorDict.class, true);
		ARMORS.seen.put(MailArmorDict.class, true);
		ARMORS.seen.put(ScaleArmorDict.class, true);
		ARMORS.seen.put(PlateArmorDict.class, true);
		ARMORS.seen.put(WarriorArmorDict.class, true);
		ARMORS.seen.put(MageArmorDict.class, true);
		ARMORS.seen.put(RogueArmorDict.class, true);
		ARMORS.seen.put(HuntressArmorDict.class, true);
		ARMORS.seen.put(ACED.class, true);
		ARMORS.seen.put(AUED.class, true);
		ARMORS.seen.put(ARED.class, true);
		ARMORS.seen.put(ACD.class, true);

		//artifacts
		ARTIFACTS.seen.put(AlcTooD.class, true);
		ARTIFACTS.seen.put(ChaliceOfBloodDict.class, true);
		ARTIFACTS.seen.put(CloakOSD.class, true);
		ARTIFACTS.seen.put(DriedRD.class, true);
		ARTIFACTS.seen.put(EtherealCD.class, true);
		ARTIFACTS.seen.put(HornOPD.class, true);
		ARTIFACTS.seen.put(SandalsOND.class, true);
		ARTIFACTS.seen.put(TalismanOFD.class, true);
		ARTIFACTS.seen.put(ThiveAD.class, true);
		ARTIFACTS.seen.put(TimeKHD.class, true);
		ARTIFACTS.seen.put(UnstableSpellBookDict.class, true);

		//alchemy, so many...
		//bomb
		ALCHEMY.seen.put(BombDict.class, true);
		ALCHEMY.seen.put(FrostBD.class, true);
		ALCHEMY.seen.put(FireBD.class, true);
		ALCHEMY.seen.put(WollyBD.class, true);
		ALCHEMY.seen.put(NoiseBD.class, true);
		ALCHEMY.seen.put(FlashBD.class, true);
		ALCHEMY.seen.put(ShockBD.class, true);
		ALCHEMY.seen.put(RegrowthBD.class, true);
		ALCHEMY.seen.put(HolyBD.class, true);
		ALCHEMY.seen.put(ArcaneBD.class, true);
		ALCHEMY.seen.put(SharpBD.class, true);
		//cats
		ALCHEMY.seen.put(AlchemyCD.class, true);
		ALCHEMY.seen.put(ArcaneCD.class, true);
		//food
		ALCHEMY.seen.put(RationD.class, true);
		ALCHEMY.seen.put(MeatD.class, true);
		ALCHEMY.seen.put(MeatPieD.class, true);
		ALCHEMY.seen.put(PastyD.class, true);
		ALCHEMY.seen.put(BlandFD.class, true);
		ALCHEMY.seen.put(BlandFCD.class, true);
		//potions, enhanced ones implemented
		ALCHEMY.seen.put(POExpD.class, true);
		ALCHEMY.seen.put(POFrostD.class, true);
		ALCHEMY.seen.put(POHasteD.class, true);
		ALCHEMY.seen.put(POHealingD.class, true);
		ALCHEMY.seen.put(POID.class, true);
		ALCHEMY.seen.put(POLeD.class, true);
		ALCHEMY.seen.put(POLFD.class, true);
		ALCHEMY.seen.put(POMVD.class, true);
		ALCHEMY.seen.put(POPGD.class, true);
		ALCHEMY.seen.put(POPurityD.class, true);
		ALCHEMY.seen.put(POSD.class, true);
		ALCHEMY.seen.put(POTGD.class, true);
		//scrolls,exotics,stones
		ALCHEMY.seen.put(SOIdentifyD.class, true);
		ALCHEMY.seen.put(SOLullabyD.class, true);
		ALCHEMY.seen.put(SOMID.class, true);
		ALCHEMY.seen.put(SOMMD.class, true);
		ALCHEMY.seen.put(SORageD.class, true);
		ALCHEMY.seen.put(SORCD.class, true);
		ALCHEMY.seen.put(SORechargingD.class, true);
		ALCHEMY.seen.put(SORetriD.class, true);
		ALCHEMY.seen.put(SOTerrorD.class, true);
		ALCHEMY.seen.put(SOTpD.class, true);
		ALCHEMY.seen.put(SOTransD.class, true);
		ALCHEMY.seen.put(SOUpgredeD.class, true);
		//brew,elixir,2 to show all
		ALCHEMY.seen.put(BrewD.class, true);
		ALCHEMY.seen.put(ElixirD.class, true);
		//spells, 2, 1 general//useless, 1 detail
		ALCHEMY.seen.put(SpellDetailD.class, true);
		//MOBS
		//ch1
		MOBS.seen.put(CH1DIV.class, true);
		MOBS.seen.put(RatD.class,true);
		MOBS.seen.put(AlbinoD.class,true);
		MOBS.seen.put(SnakeD.class,true);
		MOBS.seen.put(GnollD.class,true);
		MOBS.seen.put(FRatD.class,true);
		MOBS.seen.put(CrabD.class,true);
		MOBS.seen.put(GnollHD.class,true);
		MOBS.seen.put(SwarmD.class,true);
		MOBS.seen.put(SlimeD.class,true);
		MOBS.seen.put(CSlimeD.class,true);
		MOBS.seen.put(GCrabD.class,true);
		//1 boss
		MOBS.seen.put(CH1BOSS.class, true);
		MOBS.seen.put(GooD.class,true);
		//ch2
		MOBS.seen.put(CH2DIV.class, true);
		MOBS.seen.put(ThiefD.class,true);
		MOBS.seen.put(BanditD.class,true);
		MOBS.seen.put(SkeletonD.class,true);
		MOBS.seen.put(DM100D.class,true);
		MOBS.seen.put(GuardD.class,true);
		MOBS.seen.put(NecroD.class,true);
		MOBS.seen.put(RotHD.class,true);
		MOBS.seen.put(RotLD.class,true);
		MOBS.seen.put(NElementalD.class,true);
		//boss
		MOBS.seen.put(CH2BOSS.class, true);
		MOBS.seen.put(TenguD.class,true);
		//ch3
		MOBS.seen.put(CH3DIV.class, true);
		MOBS.seen.put(BatD.class,true);
		MOBS.seen.put(BruteD.class,true);
		MOBS.seen.put(ArmoredBruteD.class, true);
		MOBS.seen.put(ShamanD.class, true);
		MOBS.seen.put(SpinnerD.class,true);
		MOBS.seen.put(DM200D.class,true);
		MOBS.seen.put(DM201D.class,true);
		//boss
		MOBS.seen.put(CH3BOSS.class, true);
		MOBS.seen.put(DM300D.class,true);
		//ch4
		MOBS.seen.put(CH4DIV.class, true);
		MOBS.seen.put(GhoulD.class,true);
		MOBS.seen.put(WarlockD.class,true);
		MOBS.seen.put(EleFireD.class,true);
		MOBS.seen.put(EleFrostD.class,true);
		MOBS.seen.put(EleShockD.class,true);
		MOBS.seen.put(EleChaosD.class,true);
		MOBS.seen.put(MonkD.class,true);
		MOBS.seen.put(SMonkD.class,true);
		MOBS.seen.put(GolemD.class,true);
		//boss
		MOBS.seen.put(CH4BOSS.class, true);
		MOBS.seen.put(KingD.class,true);
		//ch5
		MOBS.seen.put(CH5DIV.class, true);
		MOBS.seen.put(DemonSpawnerD.class,true);
		MOBS.seen.put(RipperD.class,true);
		MOBS.seen.put(SuccubusD.class,true);
		MOBS.seen.put(EyeD.class,true);
		MOBS.seen.put(ScroD.class,true);
		MOBS.seen.put(AScroD.class,true);
		//boss
		MOBS.seen.put(CH5BOSS.class, true);
		MOBS.seen.put(FistaD.class,true);
		MOBS.seen.put(FistbD.class,true);
		MOBS.seen.put(FistcD.class,true);
		MOBS.seen.put(LarvaD.class,true);
		MOBS.seen.put(YogD.class,true);
		//normal
		MOBS.seen.put(NORMALDIV.class, true);
		MOBS.seen.put(StatueD.class,true);
		MOBS.seen.put(ArmoredStatueD.class, true);
		MOBS.seen.put(FishD.class,true);
		MOBS.seen.put(MimicD.class,true);
		MOBS.seen.put(CrystalMimicD.class,true);
		MOBS.seen.put(GoldenMimicD.class,true);
		MOBS.seen.put(WraithD.class,true);
		MOBS.seen.put(BeeD.class,true);
		MOBS.seen.put(GhostD.class,true);
		MOBS.seen.put(WandMakerD.class,true);
		MOBS.seen.put(BlacksmithD.class,true);
		MOBS.seen.put(ImpD.class,true);
		MOBS.seen.put(ImageD.class,true);
		MOBS.seen.put(PImageD.class,true);
		//plants
		PLANTS.seen.put(SeedBlindweedD.class,true);
		PLANTS.seen.put(SeedDreamfoilD.class,true);
		PLANTS.seen.put(SeedEarthrootD.class,true);
		PLANTS.seen.put(SeedFadeleafD.class,true);
		PLANTS.seen.put(SeedFirebloomD.class,true);
		PLANTS.seen.put(SeedIcecapD.class,true);
		PLANTS.seen.put(SeedRotberryD.class,true);
		PLANTS.seen.put(SeedSorrowmessD.class,true);
		PLANTS.seen.put(SeedStarflowerD.class,true);
		PLANTS.seen.put(SeedStormvineD.class,true);
		PLANTS.seen.put(SeedSungrassD.class,true);
		PLANTS.seen.put(SeedSwiftthistleD.class,true);
		//ring
		RINGS.seen.put(RingAccD.class,true);
		RINGS.seen.put(RingEleD.class,true);
		RINGS.seen.put(RingEneD.class,true);
		RINGS.seen.put(RingEvaD.class,true);
		RINGS.seen.put(RingForceD.class,true);
		RINGS.seen.put(RingFurD.class,true);
		RINGS.seen.put(RingHasteD.class,true);
		RINGS.seen.put(RingMightD.class,true);
		RINGS.seen.put(RingShsD.class,true);
		RINGS.seen.put(RingTenD.class,true);
		RINGS.seen.put(RingWeaD.class,true);
		//wand
		WANDS.seen.put(WandBWD.class,true);
		WANDS.seen.put(WandCD.class,true);
		WANDS.seen.put(WandCpD.class,true);
		WANDS.seen.put(WandDD.class,true);
		WANDS.seen.put(WandFD.class,true);
		WANDS.seen.put(WandFrostD.class,true);
		WANDS.seen.put(WandLD.class,true);
		WANDS.seen.put(WandLED.class,true);
		WANDS.seen.put(WandMMD.class,true);
		WANDS.seen.put(WandPLD.class,true);
		WANDS.seen.put(WandRD.class,true);
		WANDS.seen.put(WandTD.class,true);
		WANDS.seen.put(WandWD.class,true);
		//weapon,melee
		WEAPONS.seen.put(WSSwordD.class,true);
		WEAPONS.seen.put(GlovesD.class,true);
		WEAPONS.seen.put(DaggarD.class,true);
		WEAPONS.seen.put(MageStaffD.class,true);
		WEAPONS.seen.put(ShortSwordDict.class, true);
		WEAPONS.seen.put(HandAxeD.class,true);
		WEAPONS.seen.put(SpearD.class,true);
		WEAPONS.seen.put(QStaffD.class,true);
		WEAPONS.seen.put(DirkD.class,true);
		WEAPONS.seen.put(SwordD.class,true);
		WEAPONS.seen.put(MaceD.class,true);
		WEAPONS.seen.put(ScimitarD.class,true);
		WEAPONS.seen.put(RShieldD.class,true);
		WEAPONS.seen.put(SaiD.class,true);
		WEAPONS.seen.put(WhipD.class,true);
		WEAPONS.seen.put(LSwordD.class,true);
		WEAPONS.seen.put(BattleAxeD.class,true);
		WEAPONS.seen.put(FlailD.class,true);
		WEAPONS.seen.put(RBladeD.class,true);
		WEAPONS.seen.put(AssBladeD.class,true);
		WEAPONS.seen.put(CrosssbowD.class,true);
		WEAPONS.seen.put(GSwordD.class,true);
		WEAPONS.seen.put(WarHammerD.class,true);
		WEAPONS.seen.put(GlaiveD.class,true);
		WEAPONS.seen.put(GreatAxeD.class,true);
		WEAPONS.seen.put(GShieldD.class,true);
		WEAPONS.seen.put(GauntletD.class,true);
		//ench & curse
		WEAPONS.seen.put(WCED.class,true);
		WEAPONS.seen.put(WUED.class,true);
		WEAPONS.seen.put(WRED.class,true);
		WEAPONS.seen.put(WCD.class,true);
		//missile
		WEAPONS.seen.put(DartD.class,true);
		WEAPONS.seen.put(DartTippedD.class,true);
		WEAPONS.seen.put(ThrowingStoneD.class,true);
		WEAPONS.seen.put(ThrowingKnifeD.class,true);
		WEAPONS.seen.put(FishingSpearD.class,true);
		WEAPONS.seen.put(ShurikenD.class,true);
		WEAPONS.seen.put(ThrowingClubD.class,true);
		WEAPONS.seen.put(ThrowingSpearD.class,true);
		WEAPONS.seen.put(KunaiD.class,true);
		WEAPONS.seen.put(BolasD.class,true);
		WEAPONS.seen.put(JavelinD.class,true);
		WEAPONS.seen.put(HeavyBoomerangD.class,true);
		WEAPONS.seen.put(TomahawkD.class,true);
		WEAPONS.seen.put(ThrowingHammerD.class,true);
		WEAPONS.seen.put(TridentD.class,true);
		WEAPONS.seen.put(ForceCubeD.class,true);
		WEAPONS.seen.put(BowD.class,true);

		//Documents
		DOCUMENTS.seen.put(GlobalIntro.class, true);
		DOCUMENTS.seen.put(ItemGeneral.class, true);
		DOCUMENTS.seen.put(InfoTier.class, true);
		DOCUMENTS.seen.put(InfoWeaponMelee.class, true);
		DOCUMENTS.seen.put(InfoWeaponRanged.class, true);
		DOCUMENTS.seen.put(InfoArmor.class, true);
		DOCUMENTS.seen.put(InfoWand.class, true);
		DOCUMENTS.seen.put(InfoWandCursed.class, true);
		DOCUMENTS.seen.put(InfoRing.class, true);
		DOCUMENTS.seen.put(InfoArtifact.class, true);
		DOCUMENTS.seen.put(InfoScroll.class, true);
		DOCUMENTS.seen.put(InfoPotion.class, true);
		DOCUMENTS.seen.put(InfoLimitedDrop.class, true);
		DOCUMENTS.seen.put(InfoSeed.class, true);
		DOCUMENTS.seen.put(InfoBomb.class, true);
		DOCUMENTS.seen.put(InfoFood.class, true);
		DOCUMENTS.seen.put(InfoGold.class, true);
		DOCUMENTS.seen.put(InfoAlchemy.class, true);
		DOCUMENTS.seen.put(InfoPriority.class, true);
		DOCUMENTS.seen.put(InfoHero.class, true);
		DOCUMENTS.seen.put(InfoMob.class, true);
		DOCUMENTS.seen.put(InfoMobProperty.class, true);
		DOCUMENTS.seen.put(InfoMobSpawn.class, true);
		DOCUMENTS.seen.put(InfoMobSpecial.class, true);
		DOCUMENTS.seen.put(InfoLevelTrap.class, true);
		DOCUMENTS.seen.put(InfoLevelRoom.class, true);
		DOCUMENTS.seen.put(InfoLevelShop.class, true);
		DOCUMENTS.seen.put(InfoLevelFeeling.class, true);
		DOCUMENTS.seen.put(InfoLevelChasm.class, true);
		DOCUMENTS.seen.put(InfoLockedFloor.class, true);
		DOCUMENTS.seen.put(InfoRanking.class, true);
		DOCUMENTS.seen.put(InfoBones.class, true);

		//unclassified
		//buffs
		UNCLASSIFIED.seen.put(DebuffA.class, true);
		UNCLASSIFIED.seen.put(DebuffB.class, true);
		UNCLASSIFIED.seen.put(DebuffC.class, true);
		UNCLASSIFIED.seen.put(PobuffA.class, true);
		UNCLASSIFIED.seen.put(PobuffB.class, true);
		UNCLASSIFIED.seen.put(PobuffC.class, true);
		UNCLASSIFIED.seen.put(PobuffD.class, true);
		UNCLASSIFIED.seen.put(PobuffE.class, true);
		//traps
		UNCLASSIFIED.seen.put(TrapA.class, true);
		UNCLASSIFIED.seen.put(TrapB.class, true);
		UNCLASSIFIED.seen.put(TrapC.class, true);
		UNCLASSIFIED.seen.put(TrapD.class, true);
		//miscs
		UNCLASSIFIED.seen.put(AnkhD.class,true);
		UNCLASSIFIED.seen.put(BrokenSealD.class,true);
		UNCLASSIFIED.seen.put(DewvialD.class,true);
		UNCLASSIFIED.seen.put(GoldD.class,true);
		UNCLASSIFIED.seen.put(DustD.class,true);
		UNCLASSIFIED.seen.put(GuguguD.class,true);
	}

	public static LinkedHashMap<Catalog, Badges.Badge> catalogBadges = new LinkedHashMap<>();
	static {
		catalogBadges.put(WEAPONS, Badges.Badge.ALL_WEAPONS_IDENTIFIED);
		catalogBadges.put(ARMORS, Badges.Badge.ALL_ARMOR_IDENTIFIED);
		catalogBadges.put(WANDS, Badges.Badge.ALL_WANDS_IDENTIFIED);
		catalogBadges.put(RINGS, Badges.Badge.ALL_RINGS_IDENTIFIED);
		catalogBadges.put(ARTIFACTS, Badges.Badge.ALL_ARTIFACTS_IDENTIFIED);
		catalogBadges.put(ALCHEMY, Badges.Badge.ALL_POTIONS_IDENTIFIED);
		catalogBadges.put(MOBS, Badges.Badge.ALL_SCROLLS_IDENTIFIED);
	}
	
	public static boolean isSeen(Class<? extends Item> itemClass){
		for (Catalog cat : values()) {
			if (cat.seen.containsKey(itemClass)) {
				return cat.seen.get(itemClass);
			}
		}
		return false;
	}
	
	public static void setSeen(Class<? extends Item> itemClass){
		for (Catalog cat : values()) {
			if (cat.seen.containsKey(itemClass) && !cat.seen.get(itemClass)) {
				cat.seen.put(itemClass, true);
				Journal.saveNeeded = true;
			}
		}
		Badges.validateItemsIdentified();
	}
	
	private static final String CATALOG_ITEMS = "catalog_items";
	
	public static void store( Bundle bundle ){
		
		Badges.loadGlobal();
		
		ArrayList<Class> seen = new ArrayList<>();
		
		//if we have identified all items of a set, we use the badge to keep track instead.
		if (!Badges.isUnlocked(Badges.Badge.ALL_ITEMS_IDENTIFIED)) {
			for (Catalog cat : values()) {
				if (!Badges.isUnlocked(catalogBadges.get(cat))) {
					for (Class<? extends Item> item : cat.items()) {
						if (cat.seen.get(item)) seen.add(item);
					}
				}
			}
		}
		
		bundle.put( CATALOG_ITEMS, seen.toArray(new Class[0]) );
		
	}
	
	public static void restore( Bundle bundle ){
		
		Badges.loadGlobal();
		
		//logic for if we have all badges
		if (Badges.isUnlocked(Badges.Badge.ALL_ITEMS_IDENTIFIED)){
			for ( Catalog cat : values()){
				for (Class<? extends Item> item : cat.items()){
					cat.seen.put(item, true);
				}
			}
			return;
		}
		
		//catalog-specific badge logic
		for (Catalog cat : values()){
			if (Badges.isUnlocked(catalogBadges.get(cat))){
				for (Class<? extends Item> item : cat.items()){
					cat.seen.put(item, true);
				}
			}
		}
		
		//general save/load
		//includes "catalogs" for pre-0.8.2 saves
		if (bundle.contains("catalogs") || bundle.contains(CATALOG_ITEMS)) {
			List<Class> seenClasses = new ArrayList<>();
			if (bundle.contains(CATALOG_ITEMS)) {
				seenClasses = Arrays.asList(bundle.getClassArray(CATALOG_ITEMS));
			}
			List<String> seenItems = new ArrayList<>();
			if (bundle.contains("catalogs")) {
				Journal.saveNeeded = true; //we want to overwrite with the newer storage format
				seenItems = Arrays.asList(bundle.getStringArray("catalogs"));
			}
			
			for (Catalog cat : values()) {
				for (Class<? extends Item> item : cat.items()) {
					if (seenClasses.contains(item) || seenItems.contains(item.getSimpleName())) {
						cat.seen.put(item, true);
					}
				}
			}
		}
	}
	
}
