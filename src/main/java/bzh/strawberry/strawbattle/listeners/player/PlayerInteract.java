package bzh.strawberry.strawbattle.listeners.player;

import bzh.strawberry.strawbattle.StrawBattle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

/*
 * This file PlayerInteract is part of a project StrawBattle.StrawBattle.
 * It was created on 23/01/2021 18:53 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class PlayerInteract implements Listener {

    private List<Player> reloadStrawball = new ArrayList<>();

    public PlayerInteract() {
        StrawBattle.STRAW_BATTLE.getLogger().info("[LISTENER] Registered Listener : " + getClass().getName());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (StrawBattle.STRAW_BATTLE.running && !StrawBattle.STRAW_BATTLE.finish) {
            if (event.getItem() == null || event.getItem().getType() == Material.AIR)
                return;

            if (event.getItem().getType().equals(Material.FIRE_CHARGE)) {
                event.setCancelled(true);
                if (reloadStrawball.contains(player))
                    return;

                Fireball fireball = player.launchProjectile(Fireball.class);
                fireball.setShooter(player);
                fireball.setVelocity(player.getLocation().getDirection().multiply(0.4));
                fireball.setFireTicks(0);
                fireball.setIsIncendiary(false);
                fireball.setYield(4);

                reloadStrawball.add(player);
                StrawBattle.STRAW_BATTLE.getServer().getScheduler().runTaskLaterAsynchronously(StrawBattle.STRAW_BATTLE, () -> reloadStrawball.remove(player), 40);
            }
        }
    }
}