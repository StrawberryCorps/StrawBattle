package bzh.strawberry.strawbattle.tasks;

import bzh.strawberry.strawbattle.StrawBattle;
import bzh.strawberry.strawbattle.managers.data.StrawPlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collection;

/*
 * This file EndingTask is part of a project StrawBattle.StrawBattle.
 * It was created on 28/01/2021 14:02 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class EndingTask extends BukkitRunnable {

    private final StrawBattle strawBattle;
    private int cooldown;
    private boolean start;

    public EndingTask(StrawBattle strawBattle) {
        this.strawBattle = strawBattle;
        this.cooldown = 15;
        this.start = false;
    }

    @Override
    public void run() {
        this.start = true;
        if (this.strawBattle.getLaunchingTask().isStarted())
            this.strawBattle.getLaunchingTask().cancel();
        if (this.cooldown == 0) {
            Collection<StrawPlayer> strawPlayers = new ArrayList<>(this.strawBattle.getStrawPlayers());
            strawPlayers.forEach(strawPlayer -> strawPlayer.getPlayer().kickPlayer(this.strawBattle.getPrefix() + "§3Merci d'avoir joué ! Redémarrage du serveur en cours..."));
            this.strawBattle.getServer().spigot().restart();
        }

        this.strawBattle.getStrawPlayers().forEach(strawPlayer -> strawPlayer.getPlayer().sendMessage(this.strawBattle.getPrefix() + "§3Arrêt du serveur dans §b" + cooldown + " §3seconde" + (cooldown > 1 ? "s" : "")));

        if (this.cooldown > 0)
            this.strawBattle.getStrawPlayers().forEach(strawPlayer -> strawPlayer.getPlayer().setLevel(cooldown));
        cooldown--;
    }

    public boolean isStarted() {
        return this.start;
    }
}