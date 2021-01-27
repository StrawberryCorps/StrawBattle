package bzh.strawberry.strawbattle.listeners.world;

import bzh.strawberry.strawbattle.StrawBattle;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;

/*
 * This file WorldInit is part of a project StrawBattle.StrawBattle.
 * It was created on 23/01/2021 18:44 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class WorldInit implements Listener {

    public WorldInit() {
        StrawBattle.STRAW_BATTLE.getLogger().info("[LISTENER] Registered Listener : " + getClass().getName());
    }

    @EventHandler
    public void onWorldInit(WorldInitEvent event) {
        World world = event.getWorld();
        world.setKeepSpawnInMemory(false);
    }
}