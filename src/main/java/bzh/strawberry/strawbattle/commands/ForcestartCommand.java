package bzh.strawberry.strawbattle.commands;

import bzh.strawberry.strawbattle.StrawBattle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
        return true;
    }
}