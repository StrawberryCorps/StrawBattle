package bzh.strawberry.strawbattle.managers;

import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

/*
 * This file StrawBall is part of a project StrawBattle.StrawBattle.
 * It was created on 24/01/2021 00:39 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class StrawBall {

    private final Fireball fireball;
    private final Player player;

    public StrawBall(Fireball fireball, Player player) {
        this.fireball = fireball;
        this.player = player;
    }

    public Fireball getFireball() {
        return fireball;
    }

    public Player getPlayer() {
        return player;
    }
}