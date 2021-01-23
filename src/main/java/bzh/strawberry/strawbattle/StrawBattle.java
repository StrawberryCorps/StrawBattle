package bzh.strawberry.strawbattle;

import bzh.strawberry.strawbattle.commands.ForcestartCommand;
import bzh.strawberry.strawbattle.commands.GithubCommand;
import bzh.strawberry.strawbattle.listeners.block.BlockBreak;
import bzh.strawberry.strawbattle.listeners.block.BlockPlace;
import bzh.strawberry.strawbattle.listeners.entity.EntitySpawn;
import bzh.strawberry.strawbattle.listeners.player.*;
import bzh.strawberry.strawbattle.managers.data.StrawPlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/*
 * This file StrawBattle is part of a project StrawBattle.StrawBattle.
 * It was created on 23/01/2021 17:48 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class StrawBattle extends JavaPlugin {

    public static StrawBattle STRAW_BATTLE;

    private Collection<StrawPlayer> strawPlayers;

    @Override
    public void onEnable() {
        STRAW_BATTLE = this;
        long begin = System.currentTimeMillis();
        this.getLogger().info("######################## [" + this.getDescription().getName() + " - " + this.getDescription().getVersion() + "] #################################");

        this.getLogger().info("Starting to load the commands...");
        Objects.requireNonNull(this.getCommand("github")).setExecutor(new GithubCommand());
        Objects.requireNonNull(this.getCommand("forcestart")).setExecutor(new ForcestartCommand());
        this.getLogger().info("Starting to load the commands... -> DONE");

        this.getLogger().info("Starting loading listeners...");
        this.getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        this.getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        this.getServer().getPluginManager().registerEvents(new EntitySpawn(), this);
        this.getServer().getPluginManager().registerEvents(new FoodChangeLevel(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDropItem(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerSwapHandItems(), this);
        this.getLogger().info("Starting loading listeners... -> DONE");

        this.strawPlayers = new ArrayList<>();
        this.getLogger().info("Plugin enabled in "+(System.currentTimeMillis() - begin)+" ms.");
        this.getLogger().info("######################## [" + this.getDescription().getName() + " - " + this.getDescription().getVersion() + "] #################################");
    }

    @Override
    public void onDisable() {

    }

    /**
     * Permet d'avoir la liste des joueurs en jeu
     * @return la liste des joueurs en jeu
     */
    public Collection<StrawPlayer> getStrawPlayers() {
        return strawPlayers;
    }

    /**
     * Permet de récupérer l'instance d'un joueur a partir de son UUID
     * @param uuid pour la recherche
     * @return un strawplayer si présent dans la partie sinon null
     */
    public StrawPlayer getStrawPlayer(UUID uuid) {
        return this.strawPlayers.stream().filter(strawPlayer -> strawPlayer.getPlayer().getUniqueId().equals(uuid)).findFirst().orElse(null);
    }
}