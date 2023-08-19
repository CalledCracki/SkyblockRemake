package me.CarsCupcake.SkyblockRemake.Slayer.Blaze.Entities.T4;

import me.CarsCupcake.SkyblockRemake.API.Bundle;
import me.CarsCupcake.SkyblockRemake.API.HellionShield;
import me.CarsCupcake.SkyblockRemake.Items.ItemHandler;
import me.CarsCupcake.SkyblockRemake.Items.ItemManager;
import me.CarsCupcake.SkyblockRemake.Main;
import me.CarsCupcake.SkyblockRemake.Skyblock.Calculator;
import me.CarsCupcake.SkyblockRemake.Skyblock.FinalDamageDesider;
import me.CarsCupcake.SkyblockRemake.Skyblock.SkyblockEntity;
import me.CarsCupcake.SkyblockRemake.Skyblock.SkyblockPlayer;
import me.CarsCupcake.SkyblockRemake.Slayer.Blaze.Entities.Demons;
import me.CarsCupcake.SkyblockRemake.Slayer.Blaze.Entities.DemonsGoal;
import me.CarsCupcake.SkyblockRemake.Skyblock.Stats;
import me.CarsCupcake.SkyblockRemake.utils.Tools;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPigZombie;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Objects;


public class TyphoesT4 extends SkyblockEntity implements Demons, FinalDamageDesider {

	
	private int health = 10000000;
	private PigZombie entity;
	
	private BlazeSlayerT4 baseSlayer;
	private final String name = "§6ⓉⓎⓅⒽⓄⒺⓊⓈ";

	private BukkitRunnable tpRun;
	
	private ArmorStand timer;
	
	public boolean isInvincible = false;
	private BukkitRunnable aoeRunner;
	private HellionShield shield = HellionShield.Ashen;
	private int hits = 8;
	private BukkitRunnable fireTrail;
	private final HashMap<Block, Bundle<Material, Integer>> blockData = new HashMap<>();
	@Override
	public int getMaxHealth() {
		return 10000000;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public LivingEntity getEntity() {
		return entity;
	}



	@Override
	public int getDamage() {
		return 30000;
	}
	private void startTimer() {
		tpRun = new BukkitRunnable() {
			
			@Override
			public void run() {
				timer.teleport(entity.getLocation().add(0,0.25,0));
				
			}
		};
		tpRun.runTaskTimer(Main.getMain(), 0, 1);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void spawn(Location loc) {
		entity = loc.getWorld().spawn(loc, PigZombie.class, s ->{
			s.setAdult();
			s.getEquipment().setHelmet(Tools.CustomHeadTexture("https://textures.minecraft.net/texture/e2f29945aa53cd95a0978a62ef1a8c1978803395a8ad5c0921d9cbe5e196bb8b"));
			s.getEquipment().setHelmetDropChance(0);
			s.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
			s.getEquipment().setChestplateDropChance(0);
			s.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
			s.getEquipment().setBootsDropChance(0);
			s.getEquipment().setItemInHand(new ItemStack(Material.BLAZE_ROD));
			s.getEquipment().setItemInHandDropChance(0);
			ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS);
			LeatherArmorMeta meta = (boolean)item.hasItemMeta() ? (LeatherArmorMeta) item.getItemMeta() : (LeatherArmorMeta) Bukkit.getItemFactory().getItemMeta(item.getType());
			meta.setColor(Color.fromRGB(90, 3, 3));
			item.setItemMeta(meta);
			s.getEquipment().setLeggings(item);
			s.getEquipment().setLeggingsDropChance(0);
			s.setCustomNameVisible(true);
			s.setRemoveWhenFarAway(false);
			isInvincible = true;

	((CraftPigZombie)s).getHandle().setGoalTarget(baseSlayer.owner.getHandle(), TargetReason.PIG_ZOMBIE_TARGET, true);

		
			
		});
		
		((CraftPigZombie)entity).getHandle().bP.a(0,new DemonsGoal<>(this, ((CraftPigZombie)entity).getHandle(), EntityHuman.class, 5,1,1));
		
		Attributable zombieAt = (Attributable) entity;
		AttributeInstance attributeSpeed = zombieAt.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
		attributeSpeed.setBaseValue(0.35);
		startAoe();
	SkyblockEntity.livingEntity.addEntity(entity, this);
	updateNameTag();
	timer = entity.getWorld().spawn(loc.add(0,0.25,0), ArmorStand.class, s ->{
		s.setGravity(false);
		s.setInvisible(true);
		s.setInvulnerable(true);
		s.setCustomNameVisible(true);
	
	});
	startTimer();
	fireTrail = new BukkitRunnable() {
		HashMap<SkyblockPlayer, Integer> cooldown = new HashMap<>();
		@Override
		public void run() {
			Block current = entity.getLocation().getBlock();
			if(!blockData.containsKey(current) && entity.isOnGround()){
				blockData.put(current, new Bundle<>(current.getType(), 81));
				current.setType(Material.FIRE);
			}else {
				Bundle<Material, Integer> i = blockData.get(current);
				i.setLast(81);
				blockData.put(current, i);
			}
			for (Block b : blockData.keySet()){
				Bundle<Material, Integer> i = blockData.get(current);
				i.setLast(i.getLast() - 1);
				if(i.getLast() <= 0){
					b.setType(blockData.get(b).getFirst());
					blockData.remove(b);
				}else
					blockData.put(b, i);

				if(b.equals(baseSlayer.owner.getLocation().getBlock()) && !cooldown.containsKey(baseSlayer.owner)) {
					cooldown.put(baseSlayer.owner, 20);
					Calculator c = new Calculator();
					c.entityToPlayerDamage(TyphoesT4.this, baseSlayer.owner, new Bundle<>(0, (int)(Main.getPlayerStat(baseSlayer.owner, Stats.Health) * 0.6)));
					c.damagePlayer(baseSlayer.owner);
					c.showDamageTag(baseSlayer.owner);
				}

			}
			for (SkyblockPlayer player : cooldown.keySet())
				if (cooldown.get(player) < 1)
					cooldown.remove(player);
			    else
					cooldown.put(player, cooldown.get(player) - 1);

		}

		@Override
		public void cancel(){
			super.cancel();
			for(Block b : blockData.keySet()){
				b.setType(blockData.get(b).getFirst());
			}
			blockData.clear();
		}
	};
	fireTrail.runTaskTimer(Main.getMain(), 1 ,1);

	
	}

	

	public void startAttack(SkyblockPlayer player) {
		entity.setAngry(true);
		entity.setTarget(player);
		
	}
	public void updateTimer() {
		String str;
		if(isInvincible)
			str = "§4§lIMMUNE ";
		else
			str = shield.getName() + " " + hits + " ";
		timer.setCustomName(str +"§c" +shortInteger(baseSlayer.time));
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	private String shortInteger(int duration) {
		String string = "";
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		if (duration / 60 / 60 / 24 >= 1) {
			duration -= duration / 60 / 60 / 24 * 60 * 60 * 24;
		}
		if (duration / 60 / 60 >= 1) {
			hours = duration / 60 / 60;
			duration -= duration / 60 / 60 * 60 * 60;
		}
		if (duration / 60 >= 1) {
			minutes = duration / 60;
			duration -= duration / 60 * 60;
		}
		if (duration >= 1)
			seconds = duration;
		if (hours == 0) {
			string = String.valueOf(string) + "";
		} else {
			if (hours <= 9) {
				string = String.valueOf(string) + "0" + hours + ":";
			} else {
				string = String.valueOf(string) + hours + ":";
			}
		}
		if (minutes == 0) {
			string = String.valueOf(string) + "";
		} else {
			if (minutes <= 9) {
				string = String.valueOf(string) + "0" + minutes + ":";
			} else {
				string = String.valueOf(string) + minutes + ":";
			}
		}
		
			if (seconds <= 9) {
				string = String.valueOf(string) + "0" + seconds ;
			} else {
				string = String.valueOf(string) + seconds ;
			}
		
		return string;
	}



	@Override
	public HashMap<ItemManager,Integer> getGarantuedDrops(SkyblockPlayer player) {

		return null;
	}
	public void setInvinceble(boolean bol) {
		isInvincible = bol;
		updateTimer();
		if(!isInvincible)
			startAttack(baseSlayer.owner);
	}

	@Override
	public void updateNameTag() {
		if (health > 999) {
			if (health > 9999) {
				if (health > 999999) {
					if (health > 9999999) {
						
						entity.setCustomName("§c" + Character.toChars(9760)[0] + " §b" + name
									+ " §a" + health / 1000000 + "m§c§?§");
						
					} else {
						
						entity.setCustomName("§c" + Character.toChars(9760)[0] + " §b" + name
									+ " §a"
									+ Tools.round(
											(float) ((float) health / 1000000), 1)
									+ "m§c§?§");
						
					}
				} else {
					
					entity.setCustomName("§c" + Character.toChars(9760)[0] + " §b" + name + " §a"
								+ health / 1000 + "k§c§?§");
					;

				}
			} else {
				
					entity.setCustomName("§c" + Character.toChars(9760)[0] + " §b" + name + " §a"
							+ Tools.round((float) ((float) health / 1000), 1)
							+ "k§c§?§");
				
			}
		} else 
			entity.setCustomName("§c" + Character.toChars(9760)[0] + " §b" + name + " §a"
					+ health + "§c§?§");
		
	}
	
	public void setBlazeSlayer(BlazeSlayerT4 slayer) {
		baseSlayer = slayer;
	}

	@Override
	public void kill() {
		
		if(baseSlayer != null)
		baseSlayer.setTyphoeusKilled();
		tpRun.cancel();
		timer.remove();
		aoeRunner.cancel();
		fireTrail.cancel();
		
	}
	@Override
	public boolean hasNoKB() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void damage(double damage, SkyblockPlayer player) {
		if(!isInvincible)
			health -= damage;
		if(health > 0) {
			if(!ItemHandler.hasPDC("attuned", player.getItemInHand(), PersistentDataType.STRING)) {
				player.sendMessage("§cYour hit was reduced by Hellion Shield");
				player.sendMessage("§cStrike using the " + shield.getName() + " §cattunement on your dagger!");
				return;
			}
			HellionShield hellionShield = getShieldFromString(ItemHandler.getPDC("attuned", player.getItemInHand(),
					PersistentDataType.STRING));
			if(hellionShield != shield) {
				player.sendMessage("§cYour hit was reduced by Hellion Shield");
				player.sendMessage("§cStrike using the " + shield.getName() + " §cattunement on your dagger!");
				return;
			}
			hits--;
			if(hits == 0){
				hits = 8;
				shield = getNext();
				if(baseSlayer.isQuaziiAlive()){
					setInvinceble(true);
					baseSlayer.getQuazii().setInvinceble(false);
				}
			}
			updateTimer();

		}
		
	}

	@Override
	public int getTrueDamage() {
		// TODO Auto-generated method stub
		return 0;
	}
	private void startAoe() {
		aoeRunner = new BukkitRunnable() {
			
			@Override
			public void run() {
				baseSlayer.owner.damage(0.1);
				Calculator calculator = new Calculator();
				calculator.entityToPlayerDamage(TyphoesT4.this, baseSlayer.owner, new Bundle<>(0, getDamage()/2));
				calculator.damagePlayer(baseSlayer.owner);
			}
		};
		aoeRunner.runTaskTimer(Main.getMain(), 20, 20);
	}

	@Override
	public boolean isInvinceble() {
		return isInvincible;
	}

	@Override
	public double getFinalDamage(SkyblockPlayer player, double damage) {
		if(isInvincible)
			return 0;

		if(!ItemHandler.hasPDC("attuned", player.getItemInHand(), PersistentDataType.STRING))
			return damage * 0.01;
		HellionShield hellionShield = getShieldFromString(ItemHandler.getPDC("attuned", player.getItemInHand(),
				PersistentDataType.STRING));
		if(hellionShield == shield)
			return damage;

		return 0.01 * damage;
	}
	private HellionShield getShieldFromString(String str){
		switch (str){
			case "ashen" -> {
				return HellionShield.Ashen;
			}
			case "auric" -> {
				return HellionShield.Auric;
			}
			case "crystal" -> {
				return HellionShield.Crystal;
			}
			case "spirit" -> {
				return HellionShield.Spirit;
			}
			default -> {

				return null;
			}
		}
	}

	private HellionShield getNext(){
		if (Objects.requireNonNull(shield) == HellionShield.Ashen) {
			return HellionShield.Auric;
		}
		return HellionShield.Ashen;
	}
}
