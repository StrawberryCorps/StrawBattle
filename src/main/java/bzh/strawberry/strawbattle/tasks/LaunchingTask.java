package bzh.strawberry.strawbattle.tasks;

import bzh.strawberry.strawbattle.StrawBattle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/*
 * This file LaunchingTask is part of a project StrawBattle.StrawBattle.
 * It was created on 24/01/2021 00:03 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class LaunchingTask extends BukkitRunnable {

    private final StrawBattle strawBattle;
    private int cooldown;
    private boolean start;

    public LaunchingTask(StrawBattle strawBattle) {
        this.strawBattle = strawBattle;
        this.cooldown = 15;
        this.start = false;
    }

    @Override
    public void run() {
        this.start = true;
        if (cooldown == 0) {
            this.cancel();
            // START
            this.strawBattle.getStrawPlayers().forEach(strawPlayer -> strawPlayer.getPlayer().getInventory().clear());

            // LANCEMENT
        }

        this.strawBattle.getStrawPlayers().forEach(strawPlayer -> strawPlayer.getPlayer().setLevel(cooldown));
        cooldown--;
    }


    public void stop() {
        this.start = false;
        cancel();
        this.cooldown = 15;
    }

    public int getCooldown() { return this.cooldown; }

    public boolean isStarted() {
        return this.start;
    }
}