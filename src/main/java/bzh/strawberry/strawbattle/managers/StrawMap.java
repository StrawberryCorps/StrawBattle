package bzh.strawberry.strawbattle.managers;

import bzh.strawberry.strawbattle.StrawBattle;
import bzh.strawberry.strawbattle.exception.StrawBattleException;
import bzh.strawberry.strawbattle.utils.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static bzh.strawberry.strawbattle.utils.Cuboid.load;

/*
 * This file StrawMap is part of a project StrawBattle.StrawBattle.
 * It was created on 24/01/2021 00:51 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class StrawMap {

    private String name;

    private ItemStack item;
    private List<Location> locations;
    private Cuboid cuboid;

    public StrawMap(String name) {
        this.name = name;
        this.locations = new ArrayList<>();
    }

    public World getWorld() { return Bukkit.getWorld(name); }

    public String getName() { return this.name; }

    public ItemStack getItemStack() { return this.item; }

    public void setItem(ItemStack item) { this.item = item; }

    public Cuboid getCuboid() { return this.cuboid; }

    public void setCuboid(Cuboid cuboid) { this.cuboid = cuboid; }

    public List<Location> getLocations() {
        return locations;
    }

    public void calculateSpawn() {
        this.setCuboid(load(this.getWorld(), Objects.requireNonNull(StrawBattle.STRAW_BATTLE.getConfig().getString("maps." + name + ".cuboid"))));
        for (Block block : this.getCuboid().blockList()) {
            if (block != null && block.getType().equals(Material.BEDROCK))
                this.locations.add(block.getLocation().add(0.5,2,0.5));
        }
        if (this.locations.isEmpty())
            throw new StrawBattleException("Aucune position de spawn est défini sur la carte de jeu ! Merci de vérifier la présence de Bedrock");
    }
}