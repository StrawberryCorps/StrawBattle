package bzh.strawberry.strawbattle.listeners.player;

import bzh.strawberry.strawbattle.StrawBattle;
import bzh.strawberry.strawbattle.managers.data.StrawPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * This file PlayerJoin is part of a project StrawBattle.StrawBattle.
 * It was created on 23/01/2021 18:53 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class PlayerJoin implements Listener {

    public PlayerJoin() {
        StrawBattle.STRAW_BATTLE.getLogger().info("[LISTENER] Registered Listener : " + getClass().getName());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        StrawBattle.STRAW_BATTLE.getStrawPlayers().add(new StrawPlayer(event.getPlayer()));
        if (!StrawBattle.STRAW_BATTLE.running && StrawBattle.STRAW_BATTLE.getStrawPlayers().size() >= StrawBattle.STRAW_BATTLE.getMinPlayers()) {
            if (!StrawBattle.STRAW_BATTLE.getLaunchingTask().isStarted()) StrawBattle.STRAW_BATTLE.getLaunchingTask().runTaskTimerAsynchronously(StrawBattle.STRAW_BATTLE, 0L, 20L);
        }

        if (!StrawBattle.STRAW_BATTLE.running) {
            event.setJoinMessage(StrawBattle.STRAW_BATTLE.getPrefix() + "§b" + player.getName() + " §3a rejoint la partie §9(" + StrawBattle.STRAW_BATTLE.getStrawPlayers().size() + "/" + StrawBattle.STRAW_BATTLE.getServer().getMaxPlayers() + ")");
        } else {
            player.setGameMode(GameMode.SPECTATOR);
            for(StrawPlayer strawPlayer : StrawBattle.STRAW_BATTLE.getStrawPlayers()) {
                strawPlayer.getPlayer().hidePlayer(StrawBattle.STRAW_BATTLE, player);
                if (strawPlayer.getPlayer().getGameMode() == GameMode.SPECTATOR)
                    player.hidePlayer(StrawBattle.STRAW_BATTLE, strawPlayer.getPlayer());
            }

            List<StrawPlayer> strawPlayers = StrawBattle.STRAW_BATTLE.getStrawPlayers().stream().filter(strawPlayer -> strawPlayer.getPlayer().getGameMode() != GameMode.SPECTATOR).collect(Collectors.toList());
            player.teleport(strawPlayers.get((int) (Math.random() * strawPlayers.size())).getPlayer().getLocation());
        }
    }
}