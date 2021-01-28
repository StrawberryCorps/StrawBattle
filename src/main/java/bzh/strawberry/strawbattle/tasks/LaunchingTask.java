package bzh.strawberry.strawbattle.tasks;

import bzh.strawberry.strawbattle.StrawBattle;
import bzh.strawberry.strawbattle.managers.StrawMap;
import bzh.strawberry.strawbattle.managers.data.StrawPlayer;
import bzh.strawberry.strawbattle.utils.ItemStackBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

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
            if (StrawBattle.STRAW_BATTLE.getStrawPlayers().stream().filter(strawPlayer -> !strawPlayer.isEliminate()).count() <= 1) {
                StrawBattle.STRAW_BATTLE.getStrawPlayers().forEach(strawPlayer -> strawPlayer.getPlayer().sendMessage(StrawBattle.STRAW_BATTLE.getPrefix() + "§3Annulation du lancement de la partie ! Nombre de joueurs insuffisant :("));
                this.stop();
                return;
            }

            StrawMap strawMap = this.strawBattle.getStrawMap();

            World world = Bukkit.createWorld(new WorldCreator(strawMap.getName()));
            world.setAutoSave(false);
            world.setKeepSpawnInMemory(false);
            strawMap.calculateSpawn();

            for (StrawPlayer strawPlayer : this.strawBattle.getStrawPlayers()) {
                strawPlayer.getPlayer().sendMessage(this.strawBattle.getPrefix() + "§3La carte §b" + strawMap.getName() + " §3sera la carte pour cette partie.");
            }
            this.strawBattle.loadMap(strawMap);
        }

        if (this.cooldown == 0) {
            if (StrawBattle.STRAW_BATTLE.getStrawPlayers().stream().filter(strawPlayer -> !strawPlayer.isEliminate()).count() <= 1) {
                StrawBattle.STRAW_BATTLE.getStrawPlayers().forEach(strawPlayer -> strawPlayer.getPlayer().sendMessage(StrawBattle.STRAW_BATTLE.getPrefix() + "§3Annulation du lancement de la partie ! Nombre de joueurs insuffisant :("));
                this.stop();
                return;
            }
            this.strawBattle.running = true;
            this.strawBattle.getStrawPlayers().forEach(strawPlayer -> strawPlayer.getPlayer().getInventory().clear());
            int pos = 0;
            for (StrawPlayer strawPlayer : this.strawBattle.getStrawPlayers()) {
                if (!strawPlayer.isEliminate()) {
                    strawPlayer.getPlayer().setGameMode(GameMode.SURVIVAL);
                    strawPlayer.getPlayer().getInventory().clear();
                    strawPlayer.getPlayer().getInventory().setArmorContents(null);
                    strawPlayer.getPlayer().setHealth(strawPlayer.getPlayer().getMaxHealth());
                    strawPlayer.getPlayer().setFoodLevel(20);
                    strawPlayer.getPlayer().setLevel(0);
                    strawPlayer.getPlayer().setExp(0);

                    strawPlayer.getPlayer().teleport(strawBattle.getStrawMap().getLocations().get(pos));
                    if (pos >= strawBattle.getStrawMap().getLocations().size())
                        pos = 0;
                    else pos++;
                }
            }
        }

        if (this.cooldown < 0 && this.cooldown >= -10) {
            for (StrawPlayer strawPlayer : this.strawBattle.getStrawPlayers()) {
                strawPlayer.getPlayer().sendMessage(this.strawBattle.getPrefix() + "§3Lancement de la partie dans §9" + (11 + this.cooldown) + " §3seconde" + ((11 + this.cooldown) > 1 ? "s" : ""));
            }
            if (cooldown == -10) {
                this.cancel();

                for (Block block : this.strawBattle.getStrawMap().getCuboid().blockList()) {
                    if (block != null && (block.getType().equals(Material.BEACON) || block.getType().name().contains("STAINED_GLASS")))
                        block.setType(Material.AIR);
                }

                for (StrawPlayer strawPlayer : this.strawBattle.getStrawPlayers()) {
                    if (!strawPlayer.isEliminate()) {
                        strawPlayer.getPlayer().playSound(strawPlayer.getPlayer().getLocation(), Sound.EVENT_RAID_HORN, 100, 3);
                        strawPlayer.getPlayer().sendMessage(this.strawBattle.getPrefix() + "§3Bon jeu !");
                        strawPlayer.getPlayer().getInventory().setItem(0, new ItemStackBuilder(Material.FIRE_CHARGE, 1, "§3StrawBall §9(Clic-droit)"));
                        strawPlayer.getPlayer().getInventory().setItem(1, new ItemStackBuilder(Material.BLAZE_ROD, 1, "§3Éjecteur §9(Clic-droit)").addEnchant(true, new ItemStackBuilder.EnchantmentBuilder(Enchantment.KNOCKBACK, 1)));
                    }
                }
            }
        }

        if (this.cooldown > 0)
            this.strawBattle.getStrawPlayers().forEach(strawPlayer -> strawPlayer.getPlayer().setLevel(cooldown));
        cooldown--;
    }


    public void stop() {
        this.start = false;
        cancel();
        this.cooldown = 15;
    }

    public int getCooldown() { return this.cooldown; }

    public boolean isStarted() {
        return this.start;
    }
}