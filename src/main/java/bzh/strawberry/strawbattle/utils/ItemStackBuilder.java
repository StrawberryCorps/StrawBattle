package bzh.strawberry.strawbattle.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

/*
 * This file ItemStackBuilder is part of a project StrawBattle.StrawBattle.
 * It was created on 24/01/2021 01:17 by Uicias.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class ItemStackBuilder extends ItemStack {

    public ItemStackBuilder(Material material) {
        super(material);
    }

    public ItemStackBuilder(Material material, int amount) {
        super(material, amount);
    }

    public ItemStackBuilder(Material material, int amount, String name) {
        super(material, amount);
        ItemMeta tempItemMeta = this.getItemMeta();
        assert tempItemMeta != null;

        tempItemMeta.setDisplayName(name);
        this.setItemMeta(tempItemMeta);
    }

    public ItemStackBuilder(Material material, int amount, String name, List<String> lore) {
        super(material, amount);
        ItemMeta tempItemMeta = this.getItemMeta();
        assert tempItemMeta != null;

        tempItemMeta.setDisplayName(name);
        tempItemMeta.setLore(lore);
        this.setItemMeta(tempItemMeta);
    }

    public List<String> getLore() {
        return Objects.requireNonNull(this.getItemMeta()).getLore();
    }

    public ItemStackBuilder addEnchant(boolean hideFlags, EnchantmentBuilder... enchantments) {
        ItemMeta tempItemMeta = this.getItemMeta();
        assert tempItemMeta != null;

        for (EnchantmentBuilder enchantment : enchantments) {
            tempItemMeta.addEnchant(enchantment.getEnchantment(), enchantment.getLevel(), true);
        }

        if (hideFlags) {
            tempItemMeta.addItemFlags(ItemFlag.values());
        }

        this.setItemMeta(tempItemMeta);
        return this;
    }

    public static class EnchantmentBuilder {

        private final Enchantment enchantment;
        private final int level;

        public EnchantmentBuilder(Enchantment enchantment, int level) {
            if (enchantment == null) throw new AssertionError();
            if (level < 0) throw new AssertionError();

            this.enchantment = enchantment;
            this.level = level;
        }

        public Enchantment getEnchantment() {
            return enchantment;
        }

        public int getLevel() {
            return level;
        }
    }
}