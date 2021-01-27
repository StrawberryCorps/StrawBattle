package bzh.strawberry.strawbattle.listeners.entity;

import bzh.strawberry.strawbattle.StrawBattle;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

/*
 * This file EntitySpawn is part of a project StrawBattle.StrawBattle.
 * It was created on 23/01/2021 18:51 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class EntitySpawn implements Listener {

    public EntitySpawn() {
        StrawBattle.STRAW_BATTLE.getLogger().info("[LISTENER] Registered Listener : " + getClass().getName());
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntityType() != EntityType.FIREBALL && event.getEntityType() != EntityType.ARMOR_STAND) event.setCancelled(true);
    }

}