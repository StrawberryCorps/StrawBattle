package bzh.strawberry.strawbattle.managers.data;

import bzh.strawberry.strawbattle.StrawBattle;
import bzh.strawberry.strawbattle.managers.StrawMap;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/*
 * This file StrawPlayer is part of a project StrawBattle.StrawBattle.
 * It was created on 23/01/2021 18:19 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class StrawPlayer {

    private final Player player;

    private boolean eliminate;
    private Player lastDamager;
    private StrawMap strawMapVoted;

    /**
     * Création d'une structure pour gérer les joueurs sur la partie de jeu
     * @param player le joueur
     */
    public StrawPlayer(Player player) {
        this.player = player;
    }

    /**
     * Pour avoir l'instance du joueur
     * @return le joueur
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Cette méthode permet d'éliminer un joueur du classement
     */
    public void eliminate() {
        this.eliminate = true;
        for (StrawPlayer strawPlayers : StrawBattle.STRAW_BATTLE.getStrawPlayers()) {
            if (!strawPlayers.getPlayer().getUniqueId().toString().equals(getPlayer().getUniqueId().toString())) {
                strawPlayers.getPlayer().hidePlayer(StrawBattle.STRAW_BATTLE, player);
                if (strawPlayers.getPlayer().getGameMode() == GameMode.SPECTATOR)
                    player.hidePlayer(StrawBattle.STRAW_BATTLE, strawPlayers.getPlayer());
            }
        }
    }

    /**
     * Pour savoir si un joueur est éliminé
     * @return true si le joueur est éliminé
     */
    public boolean isEliminate() {
        return eliminate;
    }

    /**
     * Connaitre le joueur qui a mis des dégats en dernier
     * @return le dernier joueur qui a mis des dégats sur le joueur
     */
    public Player getLastDamager() {
        return lastDamager;
    }

    /**
     * Permet de mettre a jour le dernier jour qui a mis des dégats
     * @param lastDamager
     */
    public void setLastDamager(Player lastDamager) {
        this.lastDamager = lastDamager;
    }
}
