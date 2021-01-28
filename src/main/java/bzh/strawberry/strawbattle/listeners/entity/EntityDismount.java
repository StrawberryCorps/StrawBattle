package bzh.strawberry.strawbattle.listeners.entity;

import bzh.strawberry.strawbattle.StrawBattle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

/*
 * This file EntityDismount is part of a project StrawBattle.StrawBattle.
 * It was created on 23/01/2021 18:51 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class EntityDismount implements Listener {

    public EntityDismount() {
        StrawBattle.STRAW_BATTLE.getLogger().info("[LISTENER] Registered Listener : " + getClass().getName());
    }

    @EventHandler
    public void onEntityDismount(EntityDismountEvent event) {
        StrawBattle.STRAW_BATTLE.getServer().getScheduler().runTaskLater(StrawBattle.STRAW_BATTLE, () -> event.getDismounted().addPassenger(event.getEntity()), 5);
    }
}