package bzh.strawberry.strawbattle.listeners.player;

import bzh.strawberry.strawbattle.StrawBattle;
import bzh.strawberry.strawbattle.exception.StrawBattleException;
import bzh.strawberry.strawbattle.managers.StrawBall;
import bzh.strawberry.strawbattle.managers.data.StrawPlayer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/*
 * This file PlayerDeath is part of a project StrawBattle.StrawBattle.
 * It was created on 23/01/2021 18:53 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class PlayerDeath implements Listener {

    public PlayerDeath() {
        StrawBattle.STRAW_BATTLE.getLogger().info("[LISTENER] Registered Listener : " + getClass().getName());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.getGameMode() != GameMode.SPECTATOR) {
            player.spigot().respawn();
            player.getWorld().strikeLightningEffect(player.getLocation()).setSilent(true);
            StrawPlayer strawPlayer = StrawBattle.STRAW_BATTLE.getStrawPlayer(player.getUniqueId());
            if (strawPlayer.getLastDamager() != null && !strawPlayer.getLastDamager().equals(player)) {
                event.setDeathMessage(StrawBattle.STRAW_BATTLE.getPrefix() + "§b" + player.getName() + " §3a été tué par §b" + strawPlayer.getLastDamager().getName());
            } else if (player.getLastDamageCause() != null && player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
                if (entityDamageByEntityEvent.getDamager() instanceof Player) {
                    Player playerAttack = (Player) entityDamageByEntityEvent.getDamager();
                    event.setDeathMessage(StrawBattle.STRAW_BATTLE.getPrefix() + "§b" + player.getName() + " §3a été tué par §b" + playerAttack.getName());
                } else {
                    event.setDeathMessage(StrawBattle.STRAW_BATTLE.getPrefix() + "§b" + player.getName() + " §3est mort");
                }
            } else {
                event.setDeathMessage(StrawBattle.STRAW_BATTLE.getPrefix() + "§b" + player.getName() + " §3est mort");
            }

            player.playSound(player.getLocation(), Sound.ENTITY_RAVAGER_CELEBRATE, 100, 2);
            StrawBattle.STRAW_BATTLE.getStrawPlayer(player.getUniqueId()).eliminate();
            checkWin(player);
        } else {
            event.setDeathMessage(null);
        }
    }

    private void checkWin(Player player) {
        long inGame = StrawBattle.STRAW_BATTLE.getStrawPlayers().stream().filter(strawPlayer -> !strawPlayer.isEliminate()).count();
        if (inGame <= 1) {
            StrawBattle.STRAW_BATTLE.finish = true;

            StrawPlayer strawPlayerWinner = StrawBattle.STRAW_BATTLE.getStrawPlayers().stream().filter(strawPlayer -> !strawPlayer.isEliminate()).findFirst().get();
            if (strawPlayerWinner != null) {
                for (StrawPlayer strawPlayers : StrawBattle.STRAW_BATTLE.getStrawPlayers()) {
                    strawPlayers.getPlayer().showPlayer(StrawBattle.STRAW_BATTLE, player);
                    player.showPlayer(StrawBattle.STRAW_BATTLE, strawPlayers.getPlayer());
                    strawPlayers.getPlayer().sendMessage(StrawBattle.STRAW_BATTLE.getPrefix() + "§b" + strawPlayerWinner.getPlayer().getName() + " §3a gagné la partie !");
                    strawPlayers.getPlayer().setGameMode(GameMode.ADVENTURE);
                    strawPlayers.getPlayer().teleport(StrawBattle.STRAW_BATTLE.getSpawnLocation());
                }
            }
            if (!StrawBattle.STRAW_BATTLE.getEndingTask().isStarted())
                StrawBattle.STRAW_BATTLE.getEndingTask().runTaskTimer(StrawBattle.STRAW_BATTLE, 0, 20);
        }
    }
}