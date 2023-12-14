package me.CarsCupcake.SkyblockRemake.Skyblock.Jerry.Deliveries;

import me.CarsCupcake.SkyblockRemake.configs.ConfigFile;
import me.CarsCupcake.SkyblockRemake.Items.ItemManager;
import me.CarsCupcake.SkyblockRemake.Items.Items;
import me.CarsCupcake.SkyblockRemake.Main;
import me.CarsCupcake.SkyblockRemake.Skyblock.Jerry.IDelivery;
import me.CarsCupcake.SkyblockRemake.Skyblock.SkyblockPlayer;
import me.CarsCupcake.SkyblockRemake.utils.Inventories.Items.ItemBuilder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ItemDelivery implements IDelivery {
    private final ItemManager item;
    private final SkyblockPlayer player;
    private final int amount;
    private int filevar = -1;
    public ItemDelivery(@NotNull ItemManager manager, int count,@NotNull SkyblockPlayer skyblockPlayer){
        player = skyblockPlayer;
        item = manager;
        amount = count;
    }
    public ItemDelivery(){
        item = null;
        player = null;
        amount = 0;
    }

    @Override
    public ItemStack getShowItem() {
        return new ItemBuilder(item.material)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .setName(item.getRarity().getPrefix() + item.name)
                .addLoreRow("§7Here is a delivery for you :)")
                .addLoreRow(" ")
                .addLoreRow("§eClick to collect!")
                .build();
    }

    @Override
    public void claim() {
        ItemStack itemStack = item.createNewItemStack();
        itemStack.setAmount(amount);
        player.getInventory().addItem(Main.itemUpdater(itemStack, player));
        IDelivery.config.reload();
        IDelivery.config.get().set(player.getUniqueId() + ".item." + filevar, null);
        IDelivery.config.save();
        IDelivery.config.reload();

    }

    @Override
    public void save() {
        IDelivery.config.reload();
        ConfigFile file = IDelivery.config;
        int var = 1;
        if (file.get().getConfigurationSection(player.getUniqueId() + ".item") != null && file.get().contains(player.getUniqueId() + ".item"))
            var = file.get().getConfigurationSection(player.getUniqueId() + ".item").getKeys(false).size() + 1;
        file.get().set(player.getUniqueId() + ".item." + var +  ".id", item.itemID);
        file.get().set(player.getUniqueId() + ".item." + var +  ".amount", amount);
        filevar = var;
        IDelivery.config.save();
        IDelivery.config.reload();
    }

    @Override
    public @NotNull ArrayList<IDelivery> getDeliverys(SkyblockPlayer player) {
        ArrayList<IDelivery> deliveries = new ArrayList<>();
        IDelivery.config.reload();
        ConfigFile file = IDelivery.config;
        if(file.get().getConfigurationSection(player.getUniqueId() + ".item") != null)
            file.get().getConfigurationSection(player.getUniqueId() + ".item").getKeys(false).forEach(var -> {
                ItemDelivery delivery = new ItemDelivery(Items.SkyblockItems.get(file.get().getString(player.getUniqueId() + ".item." + var + ".id")), file.get().getInt(player.getUniqueId() + ".item." + var + ".amount"), player);
                delivery.setFileVar(Integer.parseInt(var));
                deliveries.add(delivery);


            });
        return deliveries;
    }
    public void setFileVar(int i){
        filevar = i;
    }
}
