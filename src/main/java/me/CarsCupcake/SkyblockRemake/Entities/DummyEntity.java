package me.CarsCupcake.SkyblockRemake.Entities;

import me.CarsCupcake.SkyblockRemake.Items.ItemManager;
import me.CarsCupcake.SkyblockRemake.Main;
import me.CarsCupcake.SkyblockRemake.Skyblock.SkyblockEntity;
import me.CarsCupcake.SkyblockRemake.Skyblock.SkyblockPlayer;
import me.CarsCupcake.SkyblockRemake.utils.Tools;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;

import java.util.HashMap;

public class DummyEntity extends SkyblockEntity {
    private LivingEntity entity;
    private final Class<? extends LivingEntity> type;
    private final int maxHealth;
    private final int health;
    private double lastDamage = 0;
    public DummyEntity(int health){
        this(health, Zombie.class);
    }
    public DummyEntity(int health, Class<? extends LivingEntity> entity){
        maxHealth = health;
        this.health = health;
        type = entity;
    }
    @Override
    public int getMaxHealth() {
        return maxHealth;
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
        return 0;
    }

    @Override
    public void spawn(Location loc) {
        entity = loc.getWorld().spawn(loc, type, e -> {
            e.setCustomNameVisible(true);
            e.setAI(false);
            e.setSilent(true);
        });
        SkyblockEntity.livingEntity.addEntity(entity, this);
        Main.updateentitystats(entity);
    }


    @Override
    public String getName() {
        return "Dummy";
    }

    @Override
    public void updateNameTag() {
        entity.setCustomName("§aLast Damage: §c" + Tools.addDigits(lastDamage));
    }

    @Override
    public void damage(double damage, SkyblockPlayer player) {
        lastDamage = damage;
    }

    @Override
    public boolean hasNoKB() {
        return false;
    }

    @Override
    public int getTrueDamage() {
        return 0;
    }
}
