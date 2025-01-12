package me.CarsCupcake.SkyblockRemake.Items.Attributes;

import me.CarsCupcake.SkyblockRemake.API.ItemEvents.GetStatFromItemEvent;
import me.CarsCupcake.SkyblockRemake.Items.ItemType;
import me.CarsCupcake.SkyblockRemake.Skyblock.SkyblockPlayer;
import me.CarsCupcake.SkyblockRemake.Skyblock.Stats;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class ManaPool extends Attribute implements Listener {
    public ManaPool(ItemType activeType, Integer level, SkyblockPlayer player) {
        super(activeType, level, player);
    }

    @Override
    public String name() {
        return "Mana Pool";
    }

    @Override
    public int maxLevel() {
        return 10;
    }

    @Override
    public boolean isAllowed() {
        return true;
    }

    @Override
    public List<String> lore() {
        return List.of("§7Grants §b+" + getBuff() + "✎ Intelligence");
    }
    private int getBuff(){
        return level*20;
    }

    @EventHandler
    public void onStatGet(GetStatFromItemEvent event) {
        if (event.getStat() != Stats.Inteligence) return;
        for (Attribute attribute : getAttributes(event.getItem())) {
            if (attribute instanceof ManaPool magicFind) {
                event.addValue(magicFind.getBuff());
            }
        }
    }
}
