package bzh.strawberry.strawbattle.listeners.player;

import bzh.strawberry.strawbattle.StrawBattle;
import bzh.strawberry.strawbattle.managers.data.StrawPlayer;
import bzh.strawberry.strawbattle.utils.PlayerUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

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

        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0);

        if (player.getUniqueId().toString().equals("94ae5b55-8b29-4e41-822c-236aac87fcbf")
                || player.getUniqueId().toString().equals("09186f54-9910-418e-90f3-d7772e731f73")
                || player.getUniqueId().toString().equals("d11301c0-c842-4e2f-af1e-382ad85fe3c4")) {
            PlayerUtil.setTag(player, "straw", 0,"§4§lSTRAW §c", "");
            player.setPlayerListName("§4§lSTRAW §c" + player.getName());
            player.setDisplayName("§4§lSTRAW §c" + player.getName());
            player.setCustomNameVisible(true);
        } else {
            PlayerUtil.setTag(player, "joueur", 1, "", "");
            player.setPlayerListName("§7" + player.getName());
            player.setDisplayName("§7" + player.getName());
        }

        if (!StrawBattle.STRAW_BATTLE.running) {
            event.setJoinMessage(StrawBattle.STRAW_BATTLE.getPrefix() + "§b" + player.getDisplayName() + " §3a rejoint la partie §9(" + StrawBattle.STRAW_BATTLE.getStrawPlayers().size() + "/" + StrawBattle.STRAW_BATTLE.getServer().getMaxPlayers() + ")");
            player.teleport(StrawBattle.STRAW_BATTLE.getSpawnLocation());
            player.setGameMode(GameMode.SURVIVAL);
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