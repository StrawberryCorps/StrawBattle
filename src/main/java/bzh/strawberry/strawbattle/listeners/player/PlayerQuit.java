package bzh.strawberry.strawbattle.listeners.player;

import bzh.strawberry.strawbattle.StrawBattle;
import bzh.strawberry.strawbattle.managers.data.StrawPlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/*
 * This file PlayerQuit is part of a project StrawBattle.StrawBattle.
 * It was created on 23/01/2021 18:53 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class PlayerQuit implements Listener {

    public PlayerQuit() {
        StrawBattle.STRAW_BATTLE.getLogger().info("[LISTENER] Registered Listener : " + getClass().getName());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(null);
        if (!StrawBattle.STRAW_BATTLE.running && !StrawBattle.STRAW_BATTLE.finish) {
            event.setQuitMessage(StrawBattle.STRAW_BATTLE.getPrefix() + "§b" + player.getDisplayName() + " §3a quitté la partie §9(" + StrawBattle.STRAW_BATTLE.getStrawPlayers().size() + "/" + StrawBattle.STRAW_BATTLE.getServer().getMaxPlayers() + ")");
            if (StrawBattle.STRAW_BATTLE.getStrawPlayers().size() - 1 <= StrawBattle.STRAW_BATTLE.getMinPlayers()) {
                if (StrawBattle.STRAW_BATTLE.getLaunchingTask().isStarted())
                    StrawBattle.STRAW_BATTLE.getLaunchingTask().stop();
            }
        } else if (StrawBattle.STRAW_BATTLE.running && !StrawBattle.STRAW_BATTLE.finish && !StrawBattle.STRAW_BATTLE.getStrawPlayer(player.getUniqueId()).isEliminate()) {
            event.setQuitMessage(StrawBattle.STRAW_BATTLE.getPrefix() + "§b" + player.getDisplayName() + " §3est mort en quittant la partie");
            checkWin(player);
        }
        StrawBattle.STRAW_BATTLE.getStrawPlayers().remove(StrawBattle.STRAW_BATTLE.getStrawPlayer(event.getPlayer().getUniqueId()));
    }

    private void checkWin(Player player) {
        long inGame = StrawBattle.STRAW_BATTLE.getStrawPlayers().stream().filter(strawPlayer -> !strawPlayer.isEliminate()).count();
        if (inGame <= 1) {
            StrawBattle.STRAW_BATTLE.finish = true;

            StrawPlayer strawPlayerWinner = StrawBattle.STRAW_BATTLE.getStrawPlayers().stream().filter(strawPlayer -> !strawPlayer.isEliminate()).findFirst().get();
            for (StrawPlayer strawPlayers : StrawBattle.STRAW_BATTLE.getStrawPlayers()) {
                strawPlayers.getPlayer().showPlayer(StrawBattle.STRAW_BATTLE, player);
                player.showPlayer(StrawBattle.STRAW_BATTLE, strawPlayers.getPlayer());
                strawPlayers.getPlayer().sendMessage(StrawBattle.STRAW_BATTLE.getPrefix() + "§b" + strawPlayerWinner.getPlayer().getDisplayName() + " §3a gagné la partie !");
                strawPlayers.getPlayer().setGameMode(GameMode.ADVENTURE);
                strawPlayers.getPlayer().teleport(StrawBattle.STRAW_BATTLE.getSpawnLocation());
            }
            if (!StrawBattle.STRAW_BATTLE.getEndingTask().isStarted())
                StrawBattle.STRAW_BATTLE.getEndingTask().runTaskTimer(StrawBattle.STRAW_BATTLE, 0, 20);
        }
    }
}