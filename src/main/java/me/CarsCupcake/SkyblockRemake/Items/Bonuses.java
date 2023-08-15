package me.CarsCupcake.SkyblockRemake.Items;

import me.CarsCupcake.SkyblockRemake.Items.farming.items.armor.CropieArmor;
import me.CarsCupcake.SkyblockRemake.Items.farming.items.armor.FermentoArmor;
import me.CarsCupcake.SkyblockRemake.Items.farming.items.armor.MelonArmor;
import me.CarsCupcake.SkyblockRemake.Items.farming.items.armor.SquashArmor;
import me.CarsCupcake.SkyblockRemake.Skyblock.SkyblockPlayer;
import me.CarsCupcake.SkyblockRemake.abilities.*;

public enum Bonuses {

	DisgustingHealing(new me.CarsCupcake.SkyblockRemake.abilities.DisgustingHealing()),
	TestBonus(new me.CarsCupcake.SkyblockRemake.abilities.TestBonus()),
	Dominus(new me.CarsCupcake.SkyblockRemake.abilities.Dominus()),
	HydraStrike(new me.CarsCupcake.SkyblockRemake.abilities.HydraStrike()),
	Fervor(new me.CarsCupcake.SkyblockRemake.abilities.Fervor()),
	ArcaneVision(new me.CarsCupcake.SkyblockRemake.abilities.ArcaneVision()),
	DctrSpaceHelmet(new DctrSpaceHelmet()),
	Maid(new Maid()),
	BruteForce(new BruteForce()),
	StaticCharge(new StaticCharge()),
	MagmaLordArmor(new MagmaLordArmor()),
	Spirit(new Spirit()),
	SuperiorBlood(new SuperiorBlood()),
	ProtectiveBlood(new ProtectiveBlood()),
	OldBlood(new OldBlood()),
	WiseBlood(new WiseBlood()),
	UnstableBlood(new UnstableBlood()),
	YoungBlood(new YoungBlood()),
	StrongBlood(new StrongBlood()),
	AdminArmor(new AdminArmorAbility()),
	CropierCrops(new MelonArmor()),
	Squashbuckle(new CropieArmor()),
	MentoFermento(new SquashArmor()),
	Feast(new FermentoArmor()),
	ExpertMiner(new ExpertMiner()),
	HolyBlood(new HolyBlood()),
	VivaciousDarkness(new FinalDestination());

	private final FullSetBonus b;
	Bonuses(FullSetBonus bonus) {
		b = bonus;
	}

	public FullSetBonus getBonus(SkyblockPlayer player) {
		if(b.getSetType() == FullSetBonus.SetType.Sneak) {
			SneakAbilityWrapper wrapper = new SneakAbilityWrapper(b);
			wrapper.setPlayer(player);
			return wrapper;
		}else return b.makeNew(player);
	}
	public boolean isSneak() {
		return b.getSetType() == FullSetBonus.SetType.Sneak;
	}
}
