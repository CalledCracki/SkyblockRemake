package me.CarsCupcake.SkyblockRemake.Slayer.Blaze.Entitys.T1;

import java.util.HashMap;

import me.CarsCupcake.SkyblockRemake.API.Bundle;
import me.CarsCupcake.SkyblockRemake.Skyblock.Calculator;
import me.CarsCupcake.SkyblockRemake.Slayer.Blaze.Entitys.Demons;
import me.CarsCupcake.SkyblockRemake.Slayer.Blaze.Entitys.DemonsGoal;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPigZombie;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.CarsCupcake.SkyblockRemake.Main;
import me.CarsCupcake.SkyblockRemake.utils.Tools;
import me.CarsCupcake.SkyblockRemake.Items.ItemManager;
import me.CarsCupcake.SkyblockRemake.Skyblock.SkyblockEntity;
import me.CarsCupcake.SkyblockRemake.Skyblock.SkyblockPlayer;
import net.minecraft.world.entity.player.EntityHuman;


public class TyphoesT1 extends SkyblockEntity implements Demons {


    private int health = 500000;
    private PigZombie entity;

    private BlazeSlayerT1 baseSlayer;
    private final String name = "§6ⓉⓎⓅⒽⓄⒺⓊⓈ";

    private BukkitRunnable tpRun;

    private ArmorStand timer;

    public boolean isInvincible = false;
    private BukkitRunnable aoeRunner;

    @Override
    public int getMaxHealth() {
        return 500000;
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
        return 2500;
    }

    private void startTimer() {
        tpRun = new BukkitRunnable() {

            @Override
            public void run() {
                timer.teleport(entity.getLocation().add(0, 0.25, 0));

            }
        };
        tpRun.runTaskTimer(Main.getMain(), 0, 1);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void spawn(Location loc) {
        entity = loc.getWorld().spawn(loc, PigZombie.class, s -> {
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
            LeatherArmorMeta meta = item.hasItemMeta() ? (LeatherArmorMeta) item.getItemMeta() : (LeatherArmorMeta) Bukkit.getItemFactory().getItemMeta(item.getType());
            meta.setColor(Color.fromRGB(90, 3, 3));
            item.setItemMeta(meta);
            s.getEquipment().setLeggings(item);
            s.getEquipment().setLeggingsDropChance(0);
            s.setCustomNameVisible(true);
            s.setRemoveWhenFarAway(false);
            isInvincible = true;

            ((CraftPigZombie) s).getHandle().setGoalTarget(baseSlayer.owner.getHandle(), TargetReason.PIG_ZOMBIE_TARGET, true);


        });

        ((CraftPigZombie) entity).getHandle().bP.a(0, new DemonsGoal<>(this, ((CraftPigZombie) entity).getHandle(), EntityHuman.class, 5, 1, 1));

        Attributable zombieAt = entity;
        AttributeInstance attributeSpeed = zombieAt.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        attributeSpeed.setBaseValue(0.35);
        startAoe();
        SkyblockEntity.livingEntity.addEntity(entity, this);
        updateNameTag();
        timer = entity.getWorld().spawn(loc.add(0, 0.25, 0), ArmorStand.class, s -> {
            s.setGravity(false);
            s.setInvisible(true);
            s.setInvulnerable(true);
            s.setCustomNameVisible(true);

        });
        startTimer();

    }


    public void startAttack(SkyblockPlayer player) {
        entity.setAngry(true);
        entity.attack(player);

    }

    public void updateTimer() {
        String str = "";
        if (isInvincible)
            str = "§4§lIMMUNE ";
        timer.setCustomName(str + "§c" + shortInteger(baseSlayer.time));
    }

    @Override
    public String getName() {
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
            string = string + "";
        } else {
            if (hours <= 9) {
                string = string + "0" + hours + ":";
            } else {
                string = string + hours + ":";
            }
        }
        if (minutes == 0) {
            string = string + "";
        } else {
            if (minutes <= 9) {
                string = string + "0" + minutes + ":";
            } else {
                string = string + minutes + ":";
            }
        }

        if (seconds <= 9) {
            string = string + "0" + seconds;
        } else {
            string = string + seconds;
        }

        return string;
    }


    @Override
    public HashMap<ItemManager, Integer> getDrops(SkyblockPlayer player) {

        return null;
    }

    public void setInvinceble(boolean bol) {
        isInvincible = bol;
        updateTimer();
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
                                ((float) health / 1000000), 1)
                                + "m§c§?§");

                    }
                } else {

                    entity.setCustomName("§c" + Character.toChars(9760)[0] + " §b" + name + " §a"
                            + health / 1000 + "k§c§?§");


                }
            } else {

                entity.setCustomName("§c" + Character.toChars(9760)[0] + " §b" + name + " §a"
                        + Tools.round(((float) health / 1000), 1)
                        + "k§c§?§");

            }
        } else
            entity.setCustomName("§c" + Character.toChars(9760)[0] + " §b" + name + " §a"
                    + health + "§c§?§");

    }

    public void setBlazeSlayer(BlazeSlayerT1 slayer) {
        baseSlayer = slayer;
    }

    @Override
    public void kill() {

        if (baseSlayer != null)
            baseSlayer.setTyphoeusKilled();
        tpRun.cancel();
        timer.remove();
        aoeRunner.cancel();

    }

    @Override
    public boolean hasNoKB() {
        return true;
    }

    @Override
    public void damage(double damage, SkyblockPlayer player) {
        if (!isInvincible)
            health -= damage;

    }

    @Override
    public int getTrueDamage() {
        return 0;
    }

    private void startAoe() {
        aoeRunner = new BukkitRunnable() {

            @Override
            public void run() {
                baseSlayer.owner.damage(0.1);
                Calculator calculator = new Calculator();
                calculator.entityToPlayerDamage(TyphoesT1.this, baseSlayer.owner, new Bundle<>(0, getDamage() / 2));
                calculator.damagePlayer(baseSlayer.owner);
            }
        };
        aoeRunner.runTaskTimer(Main.getMain(), 20, 20);
    }

    @Override
    public boolean isInvinceble() {
        return isInvincible;
    }
}
