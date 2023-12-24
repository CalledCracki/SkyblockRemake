package me.CarsCupcake.SkyblockRemake.abilities;

import me.CarsCupcake.SkyblockRemake.Skyblock.player.AccessoryBag.Powers.Powers;
import me.CarsCupcake.SkyblockRemake.Items.Bonuses;
import me.CarsCupcake.SkyblockRemake.Items.FullSetBonus;
import me.CarsCupcake.SkyblockRemake.Main;
import me.CarsCupcake.SkyblockRemake.Items.Pets.Pet;
import me.CarsCupcake.SkyblockRemake.Skyblock.SkyblockPlayer;
import me.CarsCupcake.SkyblockRemake.Skyblock.Stats;
import org.bukkit.scheduler.BukkitRunnable;

public class BruteForce implements FullSetBonus {
    private SkyblockPlayer player;
    private double statboost = 0;
    private BukkitRunnable run;
    @Override
    public void start() {
        player.setSpeedCap(250);
        int speed = playerspeedcalc();
        int multamount =  (speed / 25);
        statboost = 0.2 * multamount;
        player.setRawDamageMult(player.getRawDamageMult() + statboost);
        updateStatboost();
    }

    @Override
    public int getMaxPieces() {
        return 4;
    }

    @Override
    public void stop() {
        player.setSpeedCap(500);
        player.setRawDamageMult(player.getRawDamageMult() - statboost);
        try {
            run.cancel();
        }catch (Exception ignore){

        }

    }
    private void updateStatboost(){
        run = new BukkitRunnable() {
            @Override
            public void run() {
                int speed = playerspeedcalc();
                int multamount = (int) (speed / 25);
                double newstatboost = 0.2 * multamount;
                player.setRawDamageMult((player.getRawDamageMult() - statboost) + newstatboost);
                statboost = newstatboost;
            }
        };
        run.runTaskTimer(Main.getMain(),10,10);

    }

    @Override
    public int getPieces() {
        return 1;
    }

    @Override
    public void setPlayer(SkyblockPlayer player) {
        this.player = player;
    }

    @Override
    public Bonuses getBonus() {
        return Bonuses.BruteForce;
    }
    private  int playerspeedcalc() {
        int speed = (int) SkyblockPlayer.getSkyblockPlayer(player).getBaseStat(Stats.Speed);
        speed = (int) (speed + Main.getItemStat(player, Stats.Speed, player.getItemInHand()));
        speed = speed + (int)Main.getItemStat(player, Stats.Speed,player.getInventory().getHelmet());
        speed = speed +(int) Main.getItemStat(player, Stats.Speed,player.getInventory().getChestplate());
        speed = speed + (int)Main.getItemStat(player, Stats.Speed,player.getInventory().getLeggings());
        speed = speed +(int) Main.getItemStat(player, Stats.Speed,player.getInventory().getBoots());

        if (player.getPetEquip() != null) speed += player.getPetEquip().getStat(Stats.Speed);

        if (Powers.activepower.containsKey(player)) {
            Powers power = Powers.activepower.get(player);
            speed += power.CalculateStats(Stats.Speed, player);
        }

        if (speed > 500)
            speed = 500;
        return speed;
    }
}
