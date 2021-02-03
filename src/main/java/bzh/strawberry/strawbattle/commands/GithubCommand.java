package bzh.strawberry.strawbattle.commands;

import bzh.strawberry.strawbattle.StrawBattle;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/*
 * This file GithubCommand is part of a project StrawBattle.StrawBattle.
 * It was created on 23/01/2021 18:27 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class GithubCommand implements CommandExecutor {

    public GithubCommand() {
         StrawBattle.STRAW_BATTLE.getLogger().info("[COMMAND] Registered Command : " + getClass().getName());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String... strings) {
        TextComponent message = new TextComponent("§9[§3StrawberryCorp§9] §9Notre code source est disponible ici !");
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/StrawberryCorps/StrawBattle"));
        commandSender.spigot().sendMessage(message);
        return true;
    }
}