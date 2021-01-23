package bzh.strawberry.strawbattle.managers.data;

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
    }

    /**
     * Pour savoir si un joueur est éliminé
     * @return true si le joueur est éliminé
     */
    public boolean isEliminate() {
        return eliminate;
    }
}
