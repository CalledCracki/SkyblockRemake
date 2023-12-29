package me.CarsCupcake.SkyblockRemake.Items;

import lombok.Getter;
import me.CarsCupcake.SkyblockRemake.Main;
import me.CarsCupcake.SkyblockRemake.Skyblock.SkyblockPlayer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

@Getter
public class CoinsItem implements ItemGenerator {
    private final double value;
    public CoinsItem(double value){
        this.value = value;
    }
    public CoinsItem(ItemStack s){
        value = s.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.getMain(), "coinsvalue"), PersistentDataType.DOUBLE);
    }
    public void add(SkyblockPlayer player){
        player.addCoins(value);
    }

    @Override
    public ItemStack createNewItemStack() {
        ItemStack item = Items.SkyblockItems.get("COINS_ITEM").createNewItemStack();
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(Main.getMain(), "coinsvalue"), PersistentDataType.DOUBLE, value);
        item.setItemMeta(meta);
        return item;
    }
    public static void init(){
        ItemManager manager = new ItemManager("Coins", "COINS_ITEM", ItemType.Non, null, null, null, null, 0,0,0,0, Material.GOLD_INGOT, ItemRarity.UNDEFINED);
        manager.addAbility(new CoinsItemAbility(), AbilityType.LeftOrRightClick, null,0,0);
        Items.SkyblockItems.put(manager.itemID, manager);

    }

    public static class CoinsItemAbility implements AbilityManager<PlayerInteractEvent> {
        @Override
        public boolean triggerAbility(PlayerInteractEvent event) {
            new CoinsItem(event.getItem()).add(SkyblockPlayer.getSkyblockPlayer(event.getPlayer()));
            return false;
        }

    }
}
