package bzh.strawberry.strawbattle.listeners.player;

import bzh.strawberry.strawbattle.StrawBattle;
import bzh.strawberry.strawbattle.managers.data.StrawPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * This file AsyncPlayerChat is part of a project StrawBattle.StrawBattle.
 * It was created on 28/01/2021 15:57 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class AsyncPlayerChat implements Listener {

    public AsyncPlayerChat() {
        StrawBattle.STRAW_BATTLE.getLogger().info("[LISTENER] Registered Listener : " + getClass().getName());
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        String message = event.getMessage();
        for (StrawPlayer strawPlayers : StrawBattle.STRAW_BATTLE.getStrawPlayers()) {
            String messageTmp = message.replaceAll(("(?i)" + strawPlayers.getPlayer().getName()), "§3§l" + strawPlayers.getPlayer().getName() + "§r");
            strawPlayers.getPlayer().sendMessage("§7" + event.getPlayer().getDisplayName() + " §7» §r" + messageTmp);
        }
    }
}