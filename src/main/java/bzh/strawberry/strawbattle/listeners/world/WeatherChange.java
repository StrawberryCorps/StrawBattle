package bzh.strawberry.strawbattle.listeners.world;

import bzh.strawberry.strawbattle.StrawBattle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

/*
 * This file WeatherChange is part of a project StrawBattle.StrawBattle.
 * It was created on 23/01/2021 18:44 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class WeatherChange implements Listener {

    public WeatherChange() {
        StrawBattle.STRAW_BATTLE.getLogger().info("[LISTENER] Registered Listener : " + getClass().getName());
    }

    @EventHandler
    public void onWorldLoad(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}