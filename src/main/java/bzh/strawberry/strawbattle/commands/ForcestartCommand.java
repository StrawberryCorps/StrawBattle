package bzh.strawberry.strawbattle.commands;

import bzh.strawberry.strawbattle.StrawBattle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
 * This file ForcestartCommand is part of a project StrawBattle.StrawBattle.
 * It was created on 23/01/2021 18:26 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class ForcestartCommand implements CommandExecutor {

    public ForcestartCommand() {
        StrawBattle.STRAW_BATTLE.getLogger().info("[COMMAND] Registered Command : " + getClass().getName());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String... strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if (!player.hasPermission("strawbattle.forcestart")) return false;
        if (StrawBattle.STRAW_BATTLE.getLaunchingTask().isStarted()) {
            player.sendMessage(StrawBattle.STRAW_BATTLE.getPrefix() + "§3La partie est déjà en cours !");
            return false;
        }

        if (StrawBattle.STRAW_BATTLE.getStrawPlayers().size() <= 1) {
            player.sendMessage(StrawBattle.STRAW_BATTLE.getPrefix() + "§cIl faut au minimum 2 joueurs pour lancer la partie !");
            return false;
        }

        player.sendMessage(StrawBattle.STRAW_BATTLE.getPrefix() + "§3Lancement de la partie ! Attention au départ");
        StrawBattle.STRAW_BATTLE.getLaunchingTask().runTaskTimer(StrawBattle.STRAW_BATTLE, 0, 20);
        return true;
    }
}