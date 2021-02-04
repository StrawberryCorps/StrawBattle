package bzh.strawberry.strawbattle.listeners.player;

import bzh.strawberry.strawbattle.StrawBattle;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

/*
 * This file PlayerGameModeChange is part of a project StrawBattle.StrawBattle.
 * It was created on 23/01/2021 18:53 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class PlayerGameModeChange implements Listener {

    public PlayerGameModeChange() {
        StrawBattle.STRAW_BATTLE.getLogger().info("[LISTENER] Registered Listener : " + getClass().getName());
    }

    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        if (event.getNewGameMode() == GameMode.SPECTATOR)
            StrawBattle.STRAW_BATTLE.getStrawPlayers().forEach(strawPlayer -> strawPlayer.getPlayer().hidePlayer(StrawBattle.STRAW_BATTLE, player));
        else
            StrawBattle.STRAW_BATTLE.getStrawPlayers().forEach(strawPlayer -> strawPlayer.getPlayer().showPlayer(StrawBattle.STRAW_BATTLE, player));
    }
}