package me.CarsCupcake.SkyblockRemake.cmd;

import me.CarsCupcake.SkyblockRemake.Skyblock.Slayer;
import me.CarsCupcake.SkyblockRemake.Slayer.blaze.Entities.T2.BlazeSlayerT2;
import me.CarsCupcake.SkyblockRemake.Slayer.blaze.Entities.T3.BlazeSlayerT3;
import me.CarsCupcake.SkyblockRemake.Slayer.blaze.Entities.T4.BlazeSlayerT4;
import me.CarsCupcake.SkyblockRemake.Slayer.enderman.EndermanT1;
import me.CarsCupcake.SkyblockRemake.Slayer.enderman.EndermanT4;
import me.CarsCupcake.SkyblockRemake.Slayer.zombie.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.CarsCupcake.SkyblockRemake.Slayer.blaze.Entities.T1.BlazeSlayerT1;
import me.CarsCupcake.SkyblockRemake.Skyblock.SkyblockPlayer;
import org.jetbrains.annotations.NotNull;

public class startslayer implements CommandExecutor{

	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("slayer")) {

			if (!(sender instanceof Player player)) {
				sender.sendMessage("Du kannst das net");
				return true;
			}
			if (args.length < 2) {
				player.sendMessage("Wrong args");
				return true;
			}
			if(Slayer.getSlayer(SkyblockPlayer.getSkyblockPlayer((Player) sender)) != null) {
				sender.sendMessage("§cYou already have a slayer summoned!");
				return true;
			}

			switch (args[0]) {
				case "zombie" -> {
					SkyblockPlayer p = SkyblockPlayer.getSkyblockPlayer(player);
					Slayer slayer;
					switch (args[1]){
						case "1" -> slayer = new ZombieT1(p);
						case "2" -> slayer = new ZombieT2(p);
						case "3" -> slayer = new ZombieT3(p);
						case "4" -> slayer = new ZombieT4(p);
						case "5" -> slayer = new ZombieT5(p);
						default -> slayer = null;
					}
					if(slayer == null){
						p.sendMessage("§cNot avaidable");
						return false;
					}
					Slayer.summonSlayer(player.getLocation(), slayer);
				}
				case "blaze" -> {
					if (args[1].equals("1")) {
						BlazeSlayerT1 slayer = new BlazeSlayerT1(SkyblockPlayer.getSkyblockPlayer(player));
						slayer.setOwner(SkyblockPlayer.getSkyblockPlayer(player));
						Slayer.summonSlayer(player.getLocation(), slayer);
					}
					if (args[1].equals("2")) {
						BlazeSlayerT2 slayer = new BlazeSlayerT2(SkyblockPlayer.getSkyblockPlayer(player));
						slayer.setOwner(SkyblockPlayer.getSkyblockPlayer(player));
						Slayer.summonSlayer(player.getLocation(), slayer);
					}
					if (args[1].equals("3")) {
						BlazeSlayerT3 slayer = new BlazeSlayerT3(SkyblockPlayer.getSkyblockPlayer(player));
						slayer.setOwner(SkyblockPlayer.getSkyblockPlayer(player));
						Slayer.summonSlayer(player.getLocation(), slayer);
					}
					if (args[1].equals("4")) {
						BlazeSlayerT4 slayer = new BlazeSlayerT4(SkyblockPlayer.getSkyblockPlayer(player));
						slayer.setOwner(SkyblockPlayer.getSkyblockPlayer(player));
						Slayer.summonSlayer(player.getLocation(), slayer);
					}
				}
				case "enderman" -> {
					if (args[1].equals("1")) {
						EndermanT1 slayer = new EndermanT1(SkyblockPlayer.getSkyblockPlayer(player));
						Slayer.summonSlayer(player.getLocation(), slayer);
					}
					if (args[1].equals("4")) {
						EndermanT4 slayer = new EndermanT4(SkyblockPlayer.getSkyblockPlayer(player));
						Slayer.summonSlayer(player.getLocation(), slayer);
					}
				}
			}
			}
		
		
		return false;

}
	}
