package bzh.strawberry.strawbattle;

import bzh.strawberry.strawbattle.commands.ForcestartCommand;
import bzh.strawberry.strawbattle.commands.GithubCommand;
import bzh.strawberry.strawbattle.exception.StrawBattleException;
import bzh.strawberry.strawbattle.listeners.block.BlockBreak;
import bzh.strawberry.strawbattle.listeners.block.BlockPlace;
import bzh.strawberry.strawbattle.listeners.entity.EntityDamage;
import bzh.strawberry.strawbattle.listeners.entity.EntitySpawn;
import bzh.strawberry.strawbattle.listeners.player.*;
import bzh.strawberry.strawbattle.listeners.world.WeatherChange;
import bzh.strawberry.strawbattle.listeners.world.WorldInit;
import bzh.strawberry.strawbattle.listeners.world.WorldLoad;
import bzh.strawberry.strawbattle.managers.StrawMap;
import bzh.strawberry.strawbattle.managers.data.StrawPlayer;
import bzh.strawberry.strawbattle.tasks.EndingTask;
import bzh.strawberry.strawbattle.tasks.LaunchingTask;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.util.*;

import static bzh.strawberry.strawbattle.utils.Cuboid.load;
import static bzh.strawberry.strawbattle.utils.WorldUtil.copyWorld;
import static bzh.strawberry.strawbattle.utils.WorldUtil.deleteWorldFolder;

/*
 * This file StrawBattle is part of a project StrawBattle.StrawBattle.
 * It was created on 23/01/2021 17:48 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class StrawBattle extends JavaPlugin {

    public static StrawBattle STRAW_BATTLE;

    private int minPlayers;
    public boolean running, finish;
    private String prefix;

    private Collection<StrawPlayer> strawPlayers;
    private List<StrawMap> strawMaps;
    private LaunchingTask launchingTask;
    private EndingTask endingTask;
    private StrawMap strawMap;
    private Location spawnLocation;

    protected StrawBattle(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    public StrawBattle() {
        super();
    }
    @Override
    public void onLoad() {
        for (String name : Objects.requireNonNull(getConfig().getConfigurationSection("maps")).getKeys(false)) {
            if (deleteWorldFolder(new File(getServer().getWorldContainer(), name)))
                copyWorld(new File(getDataFolder(), name), new File(getServer().getWorldContainer(), name));
        }
    }

    @Override
    public void onEnable() {
        STRAW_BATTLE = this;
        long begin = System.currentTimeMillis();
        this.getLogger().info("######################## [" + this.getDescription().getName() + " - " + this.getDescription().getVersion() + " | Author(s) : " + this.getDescription().getAuthors().toString() + "] #################################");
        this.saveDefaultConfig();

        this.getLogger().info("Starting to load the commands...");
        Objects.requireNonNull(this.getCommand("github")).setExecutor(new GithubCommand());
        Objects.requireNonNull(this.getCommand("forcestart")).setExecutor(new ForcestartCommand());
        this.getLogger().info("Starting to load the commands... -> DONE");

        this.getLogger().info("Starting loading listeners...");
        this.getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        this.getServer().getPluginManager().registerEvents(new BlockPlace(), this);
        this.getServer().getPluginManager().registerEvents(new EntitySpawn(), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamage(), this);
        this.getServer().getPluginManager().registerEvents(new FoodChangeLevel(), this);
        this.getServer().getPluginManager().registerEvents(new AsyncPlayerChat(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDropItem(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerToggleSneak(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerSwapHandItems(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerGameModeChange(), this);
        this.getServer().getPluginManager().registerEvents(new WorldLoad(), this);
        this.getServer().getPluginManager().registerEvents(new WorldInit(), this);
        this.getServer().getPluginManager().registerEvents(new WeatherChange(), this);
        this.getLogger().info("Starting loading listeners... -> DONE");

        this.launchingTask = new LaunchingTask(this);
        this.endingTask = new EndingTask(this);

        this.minPlayers = this.getConfig().getInt("min-players");
        this.prefix = this.getConfig().getString("game-prefix");
        this.running = false;
        this.finish = false;

        this.getLogger().info("Starting loading maps...");
        this.strawMaps = new ArrayList<>();
        for (String name : Objects.requireNonNull(getConfig().getConfigurationSection("maps")).getKeys(false)) {
            StrawMap strawMap = new StrawMap(name);
            strawMap.setItem(new ItemStack(Material.valueOf(getConfig().getString("maps." + name + ".material"))));
            strawMaps.add(strawMap);
        }
        this.getLogger().info("Starting loading maps... -> DONE");

        this.spawnLocation = new Location(this.getServer().getWorld(Objects.requireNonNull(this.getConfig().getString("spawn.world"))), this.getConfig().getDouble("spawn.x"), this.getConfig().getDouble("spawn.y"), this.getConfig().getDouble("spawn.z"), this.getConfig().getInt("spawn.pitch"), this.getConfig().getInt("spawn.yaw"));

        this.strawPlayers = new ArrayList<>();
        this.getLogger().info("Plugin enabled in "+(System.currentTimeMillis() - begin)+" ms.");
        this.getLogger().info("######################## [" + this.getDescription().getName() + " - " + this.getDescription().getVersion() + " | Author(s) : " + this.getDescription().getAuthors().toString() + "] #################################");
    }

    @Override
    public void onDisable() {
        if (this.strawMap != null)
            this.getServer().unloadWorld(this.strawMap.getWorld(), false);
    }

    /**
     * Permet de charger la map de jeu avant le lancement de la partie et de calculer les points de spawn
     * @param strawMap qui a été séléctionné
     */
    public void loadMap(StrawMap strawMap) {
        String name = strawMap.getName();
        strawMap.setCuboid(load(strawMap.getWorld(), Objects.requireNonNull(getConfig().getString("maps." + name + ".cuboid"))));
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

    public StrawMap getStrawMap() {
        if (this.strawMap == null)
            this.strawMap = this.strawMaps.get((int) (Math.random() * this.strawMaps.size()));
        return this.strawMap;
    }

    /**
     * Permet de lancer la tache de lancement de la partie
     * @return l'instance de la task
     */
    public LaunchingTask getLaunchingTask() {
        return launchingTask;
    }

    /**
     * Permet de gerer la tache de fin de partie
     * @return l'instance de la task
     */
    public EndingTask getEndingTask() {
        return endingTask;
    }

    /**
     * Le nombre minimum de joueur pour lancer la partie de façon automatique
     * @return un entier du nombre de joueur requis
     */
    public int getMinPlayers() {
        return minPlayers;
    }

    /**
     * Le prefix qui doit être mis avant les messages d'informations
     * @return la chaine du prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * La position du point de spawn pour le début et la fin d'une partie
     * @return une location
     */
    public Location getSpawnLocation() {
        return spawnLocation;
    }
}