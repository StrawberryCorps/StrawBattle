package bzh.strawberry.strawbattle.listeners.entity;

import bzh.strawberry.strawbattle.StrawBattle;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/*
 * This file EntityDamage is part of a project StrawBattle.StrawBattle.
 * It was created on 23/01/2021 18:51 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class EntityDamage implements Listener {

    public EntityDamage() {
        StrawBattle.STRAW_BATTLE.getLogger().info("[LISTENER] Registered Listener : " + getClass().getName());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!StrawBattle.STRAW_BATTLE.running)
            event.setCancelled(true);
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) event.setCancelled(true);
            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
                    || event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION
                    || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK
                    || event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE)
                event.setDamage(0);
        }
    }

    @EventHandler
    public void onDamager(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getDamager() instanceof Fireball) {
                Fireball fireball = (Fireball) event.getDamager();
                if (fireball.getShooter() instanceof Player) {
                    Player shooter = (Player) fireball.getShooter();
                    if (!shooter.equals(player))
                        StrawBattle.STRAW_BATTLE.getStrawPlayer(player.getUniqueId()).setLastDamager(shooter);
                }
            }
            if (event.getDamager() instanceof Player) {
                StrawBattle.STRAW_BATTLE.getStrawPlayer(player.getUniqueId()).setLastDamager((Player) event.getDamager());
            }
        }
    }
}