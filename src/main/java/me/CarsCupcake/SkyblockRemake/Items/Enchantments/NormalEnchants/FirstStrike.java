package me.CarsCupcake.SkyblockRemake.Items.Enchantments.NormalEnchants;

import me.CarsCupcake.SkyblockRemake.Items.AbilityLore;
import me.CarsCupcake.SkyblockRemake.Items.Enchantments.CustomEnchantment;
import me.CarsCupcake.SkyblockRemake.Items.Enchantments.SkyblockEnchants;
import me.CarsCupcake.SkyblockRemake.Items.ItemHandler;
import me.CarsCupcake.SkyblockRemake.Items.ItemType;
import me.CarsCupcake.SkyblockRemake.Main;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FirstStrike extends CustomEnchantment {
    public FirstStrike() {
        super(new NamespacedKey(Main.getMain(), "fist_strike"));
    }

    @Override
    public ItemType[] getAllowedTypes() {
        return ItemType.Type.Sword.getTypeList().toArray(new ItemType[0]);
    }

    @Override
    public @NotNull AbilityLore getLore() {
        return new AbilityLore("§7Increases melee damage dealt by", "§a%p% §7for the first hit on a", "§7mob.")
                .addPlaceholder("%p%", (player, itemStack) -> (ItemHandler.getEnchantmentLevel(this, itemStack) * 25) + "%");
    }

    @NotNull
    @Override
    public String getName() {
        return "First Strike";
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public List<CustomEnchantment> conflictEnchants() {
        return List.of(SkyblockEnchants.TRIPLE_STRIKE);
    }
}
