package bzh.strawberry.strawbattle.listeners.world;

import bzh.strawberry.strawbattle.StrawBattle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

/*
 * This file WorldLoad is part of a project StrawBattle.StrawBattle.
 * It was created on 23/01/2021 18:44 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class WorldLoad implements Listener {

    public WorldLoad() {
        StrawBattle.STRAW_BATTLE.getLogger().info("[LISTENER] Registered Listener : " + getClass().getName());
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        event.getWorld().setTime(6000);
        if (event.getWorld().hasStorm()) event.getWorld().setWeatherDuration(0);
        event.getWorld().setGameRuleValue("DO_FIRE_TICK", "false");
        event.getWorld().setGameRuleValue("DO_WEATHER_CYCLE", "false");
        event.getWorld().setGameRuleValue("DO_DAYLIGHT_CYCLE", "false");
        event.getWorld().setGameRuleValue("ANNOUNCE_ADVANCEMENTS", "false");
        event.getWorld().setAutoSave(false);
    }
}