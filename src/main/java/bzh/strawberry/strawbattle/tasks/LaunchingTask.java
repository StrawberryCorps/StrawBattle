package bzh.strawberry.strawbattle.tasks;

import bzh.strawberry.strawbattle.StrawBattle;
import bzh.strawberry.strawbattle.managers.StrawMap;
import bzh.strawberry.strawbattle.managers.data.StrawPlayer;
import bzh.strawberry.strawbattle.utils.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/*
 * This file LaunchingTask is part of a project StrawBattle.StrawBattle.
 * It was created on 24/01/2021 00:03 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class LaunchingTask extends BukkitRunnable {

    private final StrawBattle strawBattle;
    private int cooldown;
    private boolean start;

    public LaunchingTask(StrawBattle strawBattle) {
        this.strawBattle = strawBattle;
        this.cooldown = 15;
        this.start = false;
    }

    @Override
    public void run() {
        this.start = true;

        if (this.cooldown == 5) {
            StrawMap strawMap = this.strawBattle.getStrawMap();
            this.strawBattle.getServer().getScheduler().runTaskAsynchronously(this.strawBattle, () -> Bukkit.createWorld(new WorldCreator(strawMap.getName())));
            for (StrawPlayer strawPlayer : this.strawBattle.getStrawPlayers()) {
                strawPlayer.getPlayer().sendMessage(this.strawBattle.getPrefix() + "§3La carte §b" + strawMap.getName() + " §3sera la carte pour cette partie.");
            }
            this.strawBattle.loadMap(strawMap);
        }

        if (this.cooldown == 0) {
            this.cancel();
            this.strawBattle.running = true;
            this.strawBattle.getStrawPlayers().forEach(strawPlayer -> strawPlayer.getPlayer().getInventory().clear());
            for (StrawPlayer strawPlayer : this.strawBattle.getStrawPlayers()) {
                if (!strawPlayer.isEliminate()) {
                    strawPlayer.getPlayer().setGameMode(GameMode.SURVIVAL);
                    strawPlayer.getPlayer().getInventory().clear();
                    strawPlayer.getPlayer().getInventory().setArmorContents(null);
                    strawPlayer.getPlayer().setHealth(strawPlayer.getPlayer().getMaxHealth());
                    strawPlayer.getPlayer().setFoodLevel(20);
                    strawPlayer.getPlayer().setLevel(0);
                    strawPlayer.getPlayer().setExp(0);
                    strawPlayer.getPlayer().getInventory().setItem(0, new ItemStackBuilder(Material.FIRE_CHARGE, 1, "§3StrawBall §9(Clic-droit)"));
                    strawPlayer.getPlayer().getInventory().setItem(1, new ItemStackBuilder(Material.BLAZE_ROD, 1, "§3Éjecteur §9(Clic-droit)").addEnchant(true, new ItemStackBuilder.EnchantmentBuilder(Enchantment.KNOCKBACK, 1)));

                    // téléportation sur la map

                }
            }
        }

        this.strawBattle.getStrawPlayers().forEach(strawPlayer -> strawPlayer.getPlayer().setLevel(cooldown));
        cooldown--;
    }


    public void stop() {
        if (this.cooldown <= 5) return;
        this.start = false;
        cancel();
        this.cooldown = 15;
    }

    public int getCooldown() { return this.cooldown; }

    public boolean isStarted() {
        return this.start;
    }
}