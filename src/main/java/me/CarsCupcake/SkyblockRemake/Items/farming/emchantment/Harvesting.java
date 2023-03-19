package me.CarsCupcake.SkyblockRemake.Items.farming.emchantment;

import me.CarsCupcake.SkyblockRemake.API.ItemEvents.GetStatFromItemEvent;
import me.CarsCupcake.SkyblockRemake.Items.ItemHandler;
import me.CarsCupcake.SkyblockRemake.Main;
import me.CarsCupcake.SkyblockRemake.Skyblock.Stats;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Harvesting extends Enchantment implements Listener {
    public Harvesting() {
        super(new NamespacedKey(Main.getMain(), "harvesting"));
    }

    @NotNull
    @Override
    public String getName() {
        return "Harvesting";
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @NotNull
    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.TOOL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack itemStack) {
        return false;
    }
    @EventHandler
    public void onGetFarmingFortune(GetStatFromItemEvent event){
        if(event.getStat() != Stats.FarmingFortune) return;
        if(ItemHandler.hasEnchantment(this, event.getItem()))
            event.addValue(12.5 * ItemHandler.getEnchantmentLevel(this, event.getItem()));
    }
}
